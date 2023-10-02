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

package org.wso2.carbon.identity.organization.discovery.service.internal;

import org.wso2.carbon.identity.organization.config.service.OrganizationConfigManager;
import org.wso2.carbon.identity.organization.discovery.service.OrganizationDiscoveryTypeFactory;
import org.wso2.carbon.identity.organization.management.service.OrganizationManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Organization discovery data holder.
 */
public class OrganizationDiscoveryServiceHolder {

    private static final OrganizationDiscoveryServiceHolder instance = new OrganizationDiscoveryServiceHolder();
    private OrganizationManager organizationManager = null;
    private OrganizationConfigManager organizationConfigManager = null;
    private Map<String, OrganizationDiscoveryTypeFactory> discoveryTypeFactoryMap;

    public static OrganizationDiscoveryServiceHolder getInstance() {

        return instance;
    }

    public OrganizationManager getOrganizationManager() {

        return organizationManager;
    }

    public void setOrganizationManager(OrganizationManager organizationManager) {

        this.organizationManager = organizationManager;
    }

    public OrganizationConfigManager getOrganizationConfigManager() {

        return organizationConfigManager;
    }

    public void setOrganizationConfigManager(OrganizationConfigManager organizationConfigManager) {

        this.organizationConfigManager = organizationConfigManager;
    }

    public OrganizationDiscoveryTypeFactory getDiscoveryTypeFactory(String type) {

        if (discoveryTypeFactoryMap == null) {
            return null;
        }
        return discoveryTypeFactoryMap.get(type);
    }

    public Set<String> getDiscoveryTypes() {

        return discoveryTypeFactoryMap.keySet();
    }

    public void setDiscoveryTypeFactory(OrganizationDiscoveryTypeFactory discoveryTypeFactory) {

        if (discoveryTypeFactoryMap == null) {
            discoveryTypeFactoryMap = new HashMap<>();
        }
        discoveryTypeFactoryMap.put(discoveryTypeFactory.getType(), discoveryTypeFactory);
    }

    public void unbindDiscoveryTypeFactory(OrganizationDiscoveryTypeFactory discoveryTypeFactory) {

        discoveryTypeFactoryMap.remove(discoveryTypeFactory.getType());
    }
}
