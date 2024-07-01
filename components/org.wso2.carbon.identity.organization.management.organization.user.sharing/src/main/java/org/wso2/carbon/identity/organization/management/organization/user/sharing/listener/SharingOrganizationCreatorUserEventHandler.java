/*
 * Copyright (c) 2023-2024, WSO2 LLC. (http://www.wso2.com).
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

package org.wso2.carbon.identity.organization.management.organization.user.sharing.listener;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.application.authentication.framework.util.FrameworkConstants;
import org.wso2.carbon.identity.application.common.IdentityApplicationManagementException;
import org.wso2.carbon.identity.application.common.model.ServiceProvider;
import org.wso2.carbon.identity.application.mgt.ApplicationConstants;
import org.wso2.carbon.identity.core.util.IdentityTenantUtil;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.identity.event.IdentityEventException;
import org.wso2.carbon.identity.event.event.Event;
import org.wso2.carbon.identity.event.handler.AbstractEventHandler;
import org.wso2.carbon.identity.organization.management.organization.user.sharing.OrganizationUserSharingService;
import org.wso2.carbon.identity.organization.management.organization.user.sharing.OrganizationUserSharingServiceImpl;
import org.wso2.carbon.identity.organization.management.organization.user.sharing.constant.UserSharingConstants;
import org.wso2.carbon.identity.organization.management.organization.user.sharing.internal.OrganizationUserSharingDataHolder;
import org.wso2.carbon.identity.organization.management.service.OrganizationManager;
import org.wso2.carbon.identity.organization.management.service.constant.OrganizationManagementConstants;
import org.wso2.carbon.identity.organization.management.service.exception.OrganizationManagementException;
import org.wso2.carbon.identity.organization.management.service.model.Organization;
import org.wso2.carbon.identity.organization.management.service.util.OrganizationManagementUtil;
import org.wso2.carbon.identity.organization.management.service.util.Utils;
import org.wso2.carbon.identity.role.v2.mgt.core.RoleConstants;
import org.wso2.carbon.identity.role.v2.mgt.core.exception.IdentityRoleManagementException;
import org.wso2.carbon.user.api.RealmConfiguration;
import org.wso2.carbon.user.api.UserStoreException;

import java.util.Collections;
import java.util.Map;

import static org.wso2.carbon.identity.organization.management.ext.Constants.EVENT_PROP_ORGANIZATION_ID;

/**
 * The event handler for sharing the organization creator to the child organization.
 */
public class SharingOrganizationCreatorUserEventHandler extends AbstractEventHandler {

    private final OrganizationUserSharingService userSharingService = new OrganizationUserSharingServiceImpl();

    @Override
    public void handleEvent(Event event) throws IdentityEventException {

        String eventName = event.getEventName();
        String orgId = null;

        if (!"POST_SHARED_CONSOLE_APP".equals(eventName)) {
            return;
        }

        try {
            Map<String, Object> eventProperties = event.getEventProperties();
            orgId = (String) eventProperties.get(EVENT_PROP_ORGANIZATION_ID);
            Organization organization = OrganizationUserSharingDataHolder.getInstance()
                    .getOrganizationManager().getOrganization(orgId, false, false);
            boolean isOrgOwnerSetInAttributes = checkOrgCreatorSetInOrgAttributes(organization);
            String authenticationType = (String) IdentityUtil.threadLocalProperties.get()
                    .get(UserSharingConstants.AUTHENTICATION_TYPE);
            if (!isOrgOwnerSetInAttributes && StringUtils.isNotEmpty(authenticationType) &&
                    UserSharingConstants.APPLICATION_AUTHENTICATION_TYPE.equals(authenticationType)) {
                return;
            }

            String tenantDomain = OrganizationUserSharingDataHolder.getInstance().getOrganizationManager()
                    .resolveTenantDomain(orgId);
            if (!OrganizationManagementUtil.isOrganization(tenantDomain)) {
                return;
            }

            RealmConfiguration realmConfiguration = OrganizationUserSharingDataHolder.getInstance()
                    .getRealmService().getTenantUserRealm(IdentityTenantUtil.getTenantId(tenantDomain))
                    .getRealmConfiguration();
            String associatedUserName = realmConfiguration.getAdminUserName();
            String associatedUserId = realmConfiguration.getAdminUserId();
            String associatedOrgId = PrivilegedCarbonContext.getThreadLocalCarbonContext()
                    .getUserResidentOrganizationId();
            if (StringUtils.isEmpty(associatedOrgId)) {
                associatedOrgId = getOrganizationManager().resolveOrganizationId(Utils.getTenantDomain());
            }
            try {
                PrivilegedCarbonContext.startTenantFlow();
                PrivilegedCarbonContext.getThreadLocalCarbonContext().setTenantDomain(tenantDomain, true);
                PrivilegedCarbonContext.getThreadLocalCarbonContext().setUsername(associatedUserName);
                userSharingService.shareOrganizationUser(orgId, associatedUserId, associatedOrgId);
                String userId = userSharingService
                        .getUserAssociationOfAssociatedUserByOrgId(associatedUserId, orgId)
                        .getUserId();
                if (allowAssignConsoleAdministratorRole()) {
                    assignUserToConsoleAppAdminRole(userId, tenantDomain);
                }
            } finally {
                PrivilegedCarbonContext.endTenantFlow();
            }
        } catch (OrganizationManagementException | UserStoreException e) {
            throw new IdentityEventException("An error occurred while sharing the organization creator to the " +
                    "organization : " + orgId, e);
        }
    }

    /**
     * The users authenticated via either console application or API invoked with basic auth option is allowed to be
     * assigned the shared console Administrator role.
     *
     * @return Whether console role is allowed to be assigned.
     */
    private boolean allowAssignConsoleAdministratorRole() {

        Object authenticatedAppFromThreadLocal = IdentityUtil.threadLocalProperties.get()
                .get(FrameworkConstants.SERVICE_PROVIDER);
        if (!(authenticatedAppFromThreadLocal instanceof String)) {
            // When organization creation is initiated via basic auth, SP is not set in the thread local.
            return true;
        }
        String authenticatedApp = (String) authenticatedAppFromThreadLocal;
        return FrameworkConstants.Application.CONSOLE_APP.equals(authenticatedApp);
    }

    private void assignUserToConsoleAppAdminRole(String userId, String tenantDomain)
            throws IdentityEventException {

        try {
            ServiceProvider serviceProvider = OrganizationUserSharingDataHolder.getInstance()
                    .getApplicationManagementService().getApplicationExcludingFileBasedSPs(
                            ApplicationConstants.CONSOLE_APPLICATION_NAME, tenantDomain);
            String adminRoleId = OrganizationUserSharingDataHolder.getInstance().getRoleManagementService()
                    .getRoleIdByName(RoleConstants.ADMINISTRATOR, RoleConstants.APPLICATION,
                            serviceProvider.getApplicationResourceId(), tenantDomain);
            OrganizationUserSharingDataHolder.getInstance().getRoleManagementService()
                    .updateUserListOfRole(adminRoleId, Collections.singletonList(userId), Collections.emptyList(),
                            tenantDomain);
        } catch (IdentityRoleManagementException e) {
            throw new IdentityEventException("An error occurred while assigning the user to the administrator role", e);
        } catch (IdentityApplicationManagementException e) {
            throw new IdentityEventException("Failed to retrieve application id of Console application.", e);
        }
    }

    private OrganizationManager getOrganizationManager() {

        return OrganizationUserSharingDataHolder.getInstance().getOrganizationManager();
    }

    private boolean checkOrgCreatorSetInOrgAttributes(Organization organization) {

        if (CollectionUtils.isEmpty(organization.getAttributes())) {
            return false;
        }
        return organization.getAttributes().stream()
                .anyMatch(x -> x.getKey().equals(OrganizationManagementConstants.CREATOR_ID));
    }
}
