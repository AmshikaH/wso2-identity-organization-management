/*
 * Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.organization.discovery.service;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.identity.organization.discovery.service.dao.OrganizationDiscoveryDAO;
import org.wso2.carbon.identity.organization.discovery.service.dao.OrganizationDiscoveryDAOImpl;
import org.wso2.carbon.identity.organization.discovery.service.internal.OrganizationDiscoveryServiceHolder;
import org.wso2.carbon.identity.organization.discovery.service.model.OrgDiscoveryAttribute;
import org.wso2.carbon.identity.organization.management.service.OrganizationManager;
import org.wso2.carbon.identity.organization.management.service.exception.OrganizationManagementClientException;
import org.wso2.carbon.identity.organization.management.service.exception.OrganizationManagementException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.wso2.carbon.identity.organization.management.service.constant.OrganizationManagementConstants.ErrorMessages.ERROR_CODE_DISCOVERY_ATTRIBUTE_ALREADY_ADDED_FOR_ORGANIZATION;
import static org.wso2.carbon.identity.organization.management.service.constant.OrganizationManagementConstants.ErrorMessages.ERROR_CODE_DISCOVERY_ATTRIBUTE_TAKEN;
import static org.wso2.carbon.identity.organization.management.service.constant.OrganizationManagementConstants.ErrorMessages.ERROR_CODE_DISCOVERY_CONFIG_DISABLED;
import static org.wso2.carbon.identity.organization.management.service.constant.OrganizationManagementConstants.ErrorMessages.ERROR_CODE_DUPLICATE_DISCOVERY_ATTRIBUTE_TYPES;
import static org.wso2.carbon.identity.organization.management.service.constant.OrganizationManagementConstants.ErrorMessages.ERROR_CODE_INVALID_ORGANIZATION;
import static org.wso2.carbon.identity.organization.management.service.constant.OrganizationManagementConstants.ErrorMessages.ERROR_CODE_UNAUTHORIZED_ORG_FOR_DISCOVERY_ATTRIBUTE_MANAGEMENT;
import static org.wso2.carbon.identity.organization.management.service.constant.OrganizationManagementConstants.ErrorMessages.ERROR_CODE_UNSUPPORTED_DISCOVERY_ATTRIBUTE;
import static org.wso2.carbon.identity.organization.management.service.util.Utils.getOrganizationId;
import static org.wso2.carbon.identity.organization.management.service.util.Utils.handleClientException;

/**
 * Implementation of Organization Discovery Manager Interface.
 */
public class OrganizationDiscoveryManagerImpl implements OrganizationDiscoveryManager {

    private static final OrganizationDiscoveryDAO organizationDiscoveryDAO = new OrganizationDiscoveryDAOImpl();

    @Override
    public List<OrgDiscoveryAttribute> addOrganizationDiscoveryAttributes(String organizationId,
                                                                          List<OrgDiscoveryAttribute>
                                                                                  discoveryAttributes)
            throws OrganizationManagementException {

        String rootOrganizationId = getOrganizationManager().getPrimaryOrganizationId(organizationId);
        validateRootOrganization(rootOrganizationId, organizationId);

        if (organizationDiscoveryDAO.isDiscoveryAttributeAddedToOrganization(organizationId)) {
            throw handleClientException(ERROR_CODE_DISCOVERY_ATTRIBUTE_ALREADY_ADDED_FOR_ORGANIZATION, organizationId);
        }

        validateOrganizationDiscoveryAttributes(false, rootOrganizationId, null,
                discoveryAttributes);

        organizationDiscoveryDAO.addOrganizationDiscoveryAttributes(organizationId, discoveryAttributes);
        return discoveryAttributes;
    }

    @Override
    public List<OrgDiscoveryAttribute> getOrganizationDiscoveryAttributes(String organizationId)
            throws OrganizationManagementException {

        String rootOrgId = getOrganizationManager().getPrimaryOrganizationId(organizationId);
        validateRootOrganization(rootOrgId, organizationId);
        return organizationDiscoveryDAO.getOrganizationDiscoveryAttributes(organizationId);
    }

    @Override
    public void deleteOrganizationDiscoveryAttributes(String organizationId) throws OrganizationManagementException {

        String rootOrgId = getOrganizationManager().getPrimaryOrganizationId(organizationId);
        validateRootOrganization(rootOrgId, organizationId);
        organizationDiscoveryDAO.deleteOrganizationDiscoveryAttributes(organizationId);
    }

    @Override
    public List<OrgDiscoveryAttribute> updateOrganizationDiscoveryAttributes(String organizationId,
                                                                             List<OrgDiscoveryAttribute>
                                                                                     discoveryAttributes)
            throws OrganizationManagementException {

        String rootOrgId = getOrganizationManager().getPrimaryOrganizationId(organizationId);
        validateRootOrganization(rootOrgId, organizationId);
        validateOrganizationDiscoveryAttributes(true, rootOrgId, organizationId, discoveryAttributes);
        organizationDiscoveryDAO.updateOrganizationDiscoveryAttributes(organizationId, discoveryAttributes);
        return discoveryAttributes;
    }

    @Override
    public boolean isDiscoveryAttributeAvailable(String type, String value) throws OrganizationManagementException {

        return !organizationDiscoveryDAO.isDiscoveryAttributeExistInHierarchy(false, getOrganizationId(),
                null, type, Collections.singletonList(value));
    }

    @Override
    public Map<String, List<OrgDiscoveryAttribute>> getOrganizationsDiscoveryAttributes()
            throws OrganizationManagementException {

        return organizationDiscoveryDAO.getOrganizationsDiscoveryAttributes(getOrganizationId());
    }

    private boolean isDiscoveryConfigurationEnabled(String rootOrgId, String type) throws
            OrganizationManagementException {

        OrganizationDiscoveryTypeFactory organizationDiscoveryTypeFactory = OrganizationDiscoveryServiceHolder
                .getInstance().getDiscoveryTypeFactory(type);
        return organizationDiscoveryTypeFactory.isDiscoveryConfigurationEnabled(rootOrgId);
    }

    private void validateRootOrganization(String rootOrgId, String organizationId)
            throws OrganizationManagementClientException {

        // Not having a root organization implies that the organization is not a valid organization.
        if (StringUtils.isBlank(rootOrgId)) {
            throw handleClientException(ERROR_CODE_INVALID_ORGANIZATION, organizationId);
        }
        if (!StringUtils.equals(getOrganizationId(), rootOrgId)) {
            throw handleClientException(ERROR_CODE_UNAUTHORIZED_ORG_FOR_DISCOVERY_ATTRIBUTE_MANAGEMENT, organizationId);
        }
    }

    private void validateOrganizationDiscoveryAttributes(boolean excludeCurrentOrganization, String rootOrganizationId,
                                                         String organizationId,
                                                         List<OrgDiscoveryAttribute> discoveryAttributes)
            throws OrganizationManagementException {

        Set<String> uniqueDiscoveryAttributeTypes = new HashSet<>();
        for (OrgDiscoveryAttribute attribute : discoveryAttributes) {
            String attributeType = attribute.getType();
            if (!uniqueDiscoveryAttributeTypes.add(attributeType)) {
                throw handleClientException(ERROR_CODE_DUPLICATE_DISCOVERY_ATTRIBUTE_TYPES, attributeType);
            }

            if (!OrganizationDiscoveryServiceHolder.getInstance().getDiscoveryTypes().contains(attributeType)) {
                throw handleClientException(ERROR_CODE_UNSUPPORTED_DISCOVERY_ATTRIBUTE, attributeType);
            }

            if (!isDiscoveryConfigurationEnabled(rootOrganizationId, attributeType)) {
                throw handleClientException(ERROR_CODE_DISCOVERY_CONFIG_DISABLED, getOrganizationId());
            }

            attribute.setValues(attribute.getValues().stream().distinct().collect(Collectors.toList()));
            boolean discoveryAttributeTaken = organizationDiscoveryDAO.isDiscoveryAttributeExistInHierarchy
                    (excludeCurrentOrganization, rootOrganizationId, organizationId, attributeType,
                            attribute.getValues());
            if (discoveryAttributeTaken) {
                throw handleClientException(ERROR_CODE_DISCOVERY_ATTRIBUTE_TAKEN, attributeType);
            }
        }
    }

    private OrganizationManager getOrganizationManager() {

        return OrganizationDiscoveryServiceHolder.getInstance().getOrganizationManager();
    }
}
