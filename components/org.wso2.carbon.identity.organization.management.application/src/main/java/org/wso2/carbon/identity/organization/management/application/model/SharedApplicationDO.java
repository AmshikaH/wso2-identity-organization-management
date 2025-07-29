/*
 * Copyright (c) 2022, WSO2 LLC. (http://www.wso2.com).
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

package org.wso2.carbon.identity.organization.management.application.model;


/**
 * This class contains the fragment application id and its organization id.
 * Use this to access the shared application info with DAO layer.
 * Always prioritize {@link SharedApplicationOrganizationNode} for accessing shared application info with service level.
 */
public class SharedApplicationDO {

    String organizationId;

    String fragmentApplicationId;

    boolean shareWithAllChildren;

    Integer appId;

    public SharedApplicationDO(String organizationId, String fragmentApplicationId) {

        this.organizationId = organizationId;
        this.fragmentApplicationId = fragmentApplicationId;
    }

    public SharedApplicationDO(String organizationId, String fragmentApplicationId, Integer appId) {

        this.organizationId = organizationId;
        this.fragmentApplicationId = fragmentApplicationId;
        this.appId = appId;
    }


    public SharedApplicationDO(String organizationId, String fragmentApplicationId, boolean shareWithAllChildren) {

        this.organizationId = organizationId;
        this.fragmentApplicationId = fragmentApplicationId;
        this.shareWithAllChildren = shareWithAllChildren;
    }

    public SharedApplicationDO(String organizationId, String fragmentApplicationId, boolean shareWithAllChildren,
                               Integer appId) {

        this.organizationId = organizationId;
        this.fragmentApplicationId = fragmentApplicationId;
        this.shareWithAllChildren = shareWithAllChildren;
        this.appId = appId;
    }

    public String getOrganizationId() {

        return organizationId;
    }

    public String getFragmentApplicationId() {

        return fragmentApplicationId;
    }

    public boolean shareWithAllChildren() {

        return this.shareWithAllChildren;
    }

    public Integer getAppId() {

        return appId;
    }
}
