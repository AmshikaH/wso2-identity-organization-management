/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.com).
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
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

package org.wso2.carbon.identity.organization.management.role.management.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.service.RoleManagementApiService;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.model.RolePatchRequestObject;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.model.RolePostRequestObject;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.model.RolePutRequestObject;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.RolesApiService;

import javax.ws.rs.core.Response;

public class RolesApiServiceImpl implements RolesApiService {

    @Autowired
    private RoleManagementApiService roleManagementApiService;
    //default organization for API testing
    private String organizationId = "xxx";

    @Override
    public Response createRole(RolePostRequestObject rolePostRequestObject) {

        return roleManagementApiService.createRole(organizationId, rolePostRequestObject);
    }

    @Override
    public Response rolesGet(String filter, Integer limit, String after, String before) {

        return roleManagementApiService.getRolesOfOrganization(organizationId, filter, limit, after, before);
    }

    @Override
    public Response rolesRoleIdDelete(String roleId) {

        return roleManagementApiService.deleteRole(organizationId, roleId);
    }

    @Override
    public Response rolesRoleIdGet(String roleId) {

        return roleManagementApiService.getRoleUsingOrganizationIdAndRoleId(organizationId, roleId);
    }

    @Override
    public Response rolesRoleIdPatch(String roleId, RolePatchRequestObject rolePatchRequestObject) {

        return roleManagementApiService.patchRole(organizationId, roleId, rolePatchRequestObject);
    }

    @Override
    public Response rolesRoleIdPut(String roleId, RolePutRequestObject rolePutRequestObject) {

        return roleManagementApiService.putRole(organizationId, roleId, rolePutRequestObject);
    }
}
