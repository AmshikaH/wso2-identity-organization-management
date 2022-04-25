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

package org.wso2.carbon.identity.organization.management.role.management.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import java.io.InputStream;
import java.util.List;

import org.wso2.carbon.identity.organization.management.role.management.endpoint.model.Error;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.model.RoleGetResponseObject;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.model.RolePatchRequestObject;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.model.RolePatchResponseObject;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.model.RolePostRequestObject;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.model.RolePostResponseObject;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.model.RolePutRequestObject;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.model.RolePutResponseObject;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.model.RolesListResponseObject;
import org.wso2.carbon.identity.organization.management.role.management.endpoint.RolesApiService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import io.swagger.annotations.*;

import javax.validation.constraints.*;

@Path("/roles")
@Api(description = "The roles API")

public class RolesApi  {

    @Autowired
    private RolesApiService delegate;

    @Valid
    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Create a role inside an organization.", notes = "This API creates a role inside an organization and returns the details of the created role including its unique id.", response = RolePostResponseObject.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Organization Role Management", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Valid role is created.", response = RolePostResponseObject.class),
        @ApiResponse(code = 400, message = "Invalid input in the request.", response = Error.class),
        @ApiResponse(code = 401, message = "Authentication information is missing or invalid.", response = Void.class),
        @ApiResponse(code = 403, message = "Access forbidden.", response = Void.class),
        @ApiResponse(code = 404, message = "Requested resource is not found.", response = Error.class),
        @ApiResponse(code = 409, message = "Conflict response.", response = Void.class),
        @ApiResponse(code = 500, message = "Internal server error.", response = Error.class)
    })
    public Response createRole(@ApiParam(value = "This represents a set of permissions going to be assigned to the role." ,required=true) @Valid RolePostRequestObject rolePostRequestObject) {

        return delegate.createRole(rolePostRequestObject );
    }

    @Valid
    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Get roles inside an organization.", notes = "This API returs roles according to the specified filter, sort and pagination parameters.", response = RolesListResponseObject.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Organization Role Management", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Valid roles are found.", response = RolesListResponseObject.class),
        @ApiResponse(code = 401, message = "Authentication information is missing or invalid.", response = Void.class),
        @ApiResponse(code = 403, message = "Access forbidden.", response = Void.class),
        @ApiResponse(code = 404, message = "Requested resource is not found.", response = Error.class)
    })
    public Response rolesGet(    @Valid@ApiParam(value = "Condition to filter the retrieval of records.")  @QueryParam("filter") String filter,     @Valid @Min(0)@ApiParam(value = "Maximum number of records to be returned. (Should be greater than 0)")  @QueryParam("limit") Integer limit,     @Valid@ApiParam(value = "Points to the next range of data to be returned.")  @QueryParam("after") String after,     @Valid@ApiParam(value = "Points to the previous range of data that can be retrieved.")  @QueryParam("before") String before) {

        return delegate.rolesGet(filter,  limit,  after,  before );
    }

    @Valid
    @DELETE
    @Path("/{role-id}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Delete a role inside an organization.", notes = "This API deletes a particular role inside an organization using its unique ID.", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Organization Role Management", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Role is deleted.", response = Void.class),
        @ApiResponse(code = 400, message = "Invalid input in the request.", response = Error.class),
        @ApiResponse(code = 401, message = "Authentication information is missing or invalid.", response = Void.class),
        @ApiResponse(code = 403, message = "Access forbidden.", response = Void.class),
        @ApiResponse(code = 404, message = "Requested resource is not found.", response = Error.class),
        @ApiResponse(code = 500, message = "Internal server error.", response = Error.class)
    })
    public Response rolesRoleIdDelete(@ApiParam(value = "ID of the role.",required=true) @PathParam("role-id") String roleId) {

        return delegate.rolesRoleIdDelete(roleId );
    }

    @Valid
    @GET
    @Path("/{role-id}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Get Role by ID", notes = "This API returns the role details of a particular role using its unique id.", response = RoleGetResponseObject.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Organization Role Management", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Valid role is found.", response = RoleGetResponseObject.class),
        @ApiResponse(code = 400, message = "Invalid input in the request.", response = Error.class),
        @ApiResponse(code = 401, message = "Authentication information is missing or invalid.", response = Void.class),
        @ApiResponse(code = 403, message = "Access forbidden.", response = Void.class),
        @ApiResponse(code = 404, message = "Requested resource is not found.", response = Error.class),
        @ApiResponse(code = 500, message = "Internal server error.", response = Error.class)
    })
    public Response rolesRoleIdGet(@ApiParam(value = "ID of the role.",required=true) @PathParam("role-id") String roleId) {

        return delegate.rolesRoleIdGet(roleId );
    }

    @Valid
    @PATCH
    @Path("/{role-id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Update Role - PATCH", notes = "This API updates the role details and returns the updated role details inside a PATCH operation.", response = RolePatchResponseObject.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Organization Role Management", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Role is Updated.", response = RolePatchResponseObject.class),
        @ApiResponse(code = 400, message = "Invalid input in the request.", response = Error.class),
        @ApiResponse(code = 401, message = "Authentication information is missing or invalid.", response = Void.class),
        @ApiResponse(code = 403, message = "Access forbidden.", response = Void.class),
        @ApiResponse(code = 404, message = "Requested resource is not found.", response = Error.class),
        @ApiResponse(code = 406, message = "Not acceptable.", response = Void.class),
        @ApiResponse(code = 500, message = "Internal server error.", response = Error.class)
    })
    public Response rolesRoleIdPatch(@ApiParam(value = "ID of the role.",required=true) @PathParam("role-id") String roleId, @ApiParam(value = "This represents a set of values that need to be changed in the role." ) @Valid RolePatchRequestObject rolePatchRequestObject) {

        return delegate.rolesRoleIdPatch(roleId,  rolePatchRequestObject );
    }

    @Valid
    @PUT
    @Path("/{role-id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Update Role - PUT", notes = "This API updates the role details and returns the updated role details using a PUT operation.", response = RolePutResponseObject.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Organization Role Management" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Role is Updated.", response = RolePutResponseObject.class),
        @ApiResponse(code = 400, message = "Invalid input in the request.", response = Error.class),
        @ApiResponse(code = 401, message = "Authentication information is missing or invalid.", response = Void.class),
        @ApiResponse(code = 403, message = "Access forbidden.", response = Void.class),
        @ApiResponse(code = 404, message = "Requested resource is not found.", response = Error.class),
        @ApiResponse(code = 406, message = "Not acceptable.", response = Void.class),
        @ApiResponse(code = 500, message = "Internal server error.", response = Error.class)
    })
    public Response rolesRoleIdPut(@ApiParam(value = "ID of the role.",required=true) @PathParam("role-id") String roleId, @ApiParam(value = "This represents a set of values that need to be changed in the role." ) @Valid RolePutRequestObject rolePutRequestObject) {

        return delegate.rolesRoleIdPut(roleId,  rolePutRequestObject );
    }

}
