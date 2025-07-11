/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com).
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

package org.wso2.carbon.identity.organization.resource.sharing.policy.management.dao;

import org.wso2.carbon.identity.organization.resource.sharing.policy.management.constant.ResourceType;
import org.wso2.carbon.identity.organization.resource.sharing.policy.management.constant.SharedAttributeType;
import org.wso2.carbon.identity.organization.resource.sharing.policy.management.exception.NotImplementedException;
import org.wso2.carbon.identity.organization.resource.sharing.policy.management.exception.ResourceSharingPolicyMgtException;
import org.wso2.carbon.identity.organization.resource.sharing.policy.management.exception.ResourceSharingPolicyMgtServerException;
import org.wso2.carbon.identity.organization.resource.sharing.policy.management.model.ResourceSharingPolicy;
import org.wso2.carbon.identity.organization.resource.sharing.policy.management.model.SharedResourceAttribute;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * DAO interface for handling resource sharing policies across organizations.
 * <p>
 * This interface provides methods for adding, retrieving, and deleting resource sharing policies.
 * It also allows managing shared resource attributes, offering capabilities to add, retrieve, and delete
 * these attributes.
 * </p>
 */
public interface ResourceSharingPolicyHandlerDAO {

    /**
     * Adds a new resource sharing policy to the database and returns its unique identifier.
     *
     * @param resourceSharingPolicy The {@link ResourceSharingPolicy} containing details such as resource type,
     *                              initiating organization, policy holding organization, and sharing policy.
     *                              Must not be {@code null}.
     * @return The unique identifier of the newly created resource sharing policy.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while adding the resource sharing policy.
     */
    int addResourceSharingPolicy(ResourceSharingPolicy resourceSharingPolicy)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Retrieves a resource sharing policy by its unique identifier, if present.
     *
     * @param resourceSharingPolicyId The unique identifier of the resource sharing policy to be retrieved.
     *                                Must be a valid ID greater than zero.
     * @return An {@link Optional} containing the {@link ResourceSharingPolicy} if found,
     * or an empty {@link Optional} if no matching resource sharing policy exists.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while retrieving the resource sharing policy.
     */
    Optional<ResourceSharingPolicy> getResourceSharingPolicyById(int resourceSharingPolicyId)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Retrieves a list of resource sharing policies associated with the given policy holding organization IDs.
     *
     * @param policyHoldingOrganizationIds A list of organization IDs whose policies need to be retrieved.
     *                                     Must not be {@code null} or empty.
     * @return A list of {@link ResourceSharingPolicy} objects for the specified organization IDs.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while retrieving the resource sharing
     *                                                 policies.
     */
    List<ResourceSharingPolicy> getResourceSharingPolicies(List<String> policyHoldingOrganizationIds)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Retrieves a map of resource sharing policies grouped by resource type for the given policy holding
     * organization IDs.
     *
     * @param policyHoldingOrganizationIds A list of organization IDs whose policies need to be retrieved.
     *                                     Must not be {@code null} or empty.
     * @return A map where each key is a {@link ResourceType} and the corresponding value is a list of
     * {@link ResourceSharingPolicy}.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while retrieving the resource sharing
     *                                                 policies.
     */
    Map<ResourceType, List<ResourceSharingPolicy>> getResourceSharingPoliciesGroupedByResourceType(
            List<String> policyHoldingOrganizationIds) throws ResourceSharingPolicyMgtServerException;

    /**
     * Retrieves a map of resource sharing policies grouped by policy holding organization ID for the given policy
     * holding organization IDs.
     *
     * @param policyHoldingOrganizationIds A list of organization IDs whose policies need to be retrieved.
     *                                     Must not be {@code null} or empty.
     * @return A map where each key is an organization ID, and the value is a list of {@link ResourceSharingPolicy}.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while retrieving the resource sharing
     *                                                 policies.
     */
    Map<String, List<ResourceSharingPolicy>> getResourceSharingPoliciesGroupedByPolicyHoldingOrgId(
            List<String> policyHoldingOrganizationIds) throws ResourceSharingPolicyMgtServerException;

    /**
     * Deletes a resource sharing policy by its unique identifier if the specified organization has permission.
     *
     * @param resourceSharingPolicyId     The unique identifier of the resource sharing policy to be deleted.
     * @param sharingPolicyInitiatedOrgId The ID of the organization initiating the share request.
     *                                    The deletion will only be successful if the initiating organization
     *                                    has permission to delete sharing policies.
     *                                    Must not be {@code null}.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while deleting the resource sharing policy.
     */
    void deleteResourceSharingPolicyRecordById(int resourceSharingPolicyId, String sharingPolicyInitiatedOrgId)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Deletes a resource sharing policy based on its resource type and resource ID if permitted.
     *
     * @param resourceType                The {@link ResourceType} of the resource.
     * @param resourceId                  The unique identifier of the resource whose sharing policy is to be deleted.
     * @param sharingPolicyInitiatedOrgId The ID of the organization initiating the share request.
     *                                    The deletion will only be successful if the initiating organization
     *                                    has permission to delete sharing policies.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while deleting the resource sharing policy.
     */
    void deleteResourceSharingPolicyByResourceTypeAndId(ResourceType resourceType, String resourceId,
                                                        String sharingPolicyInitiatedOrgId)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Deletes a resource sharing policy in the specified policy holding organization, based on its resource type
     * and resource ID, if permitted.
     *
     * @param policyHoldingOrgId          The ID of the organization holding the policy for the resource.
     * @param resourceType                The {@link ResourceType} of the resource.
     * @param resourceId                  The unique identifier of the resource whose sharing policy is to be deleted.
     * @param sharingPolicyInitiatedOrgId The ID of the organization initiating the share request.
     *                                    The deletion will only be successful if the initiating organization
     *                                    has permission to delete sharing policies.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while deleting the resource sharing policy.
     */
    void deleteResourceSharingPolicyInOrgByResourceTypeAndId(String policyHoldingOrgId, ResourceType resourceType,
                                                             String resourceId, String sharingPolicyInitiatedOrgId)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Adds shared resource attributes to an existing resource sharing policy.
     *
     * @param sharedResourceAttributes A list of {@link SharedResourceAttribute} objects to be added.
     *                                 Must not be {@code null} or empty.
     * @return {@code true} if the shared resource attributes were added successfully.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while adding the shared resource attributes.
     */
    boolean addSharedResourceAttributes(List<SharedResourceAttribute> sharedResourceAttributes)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Retrieves shared resource attributes for a given resource sharing policy.
     *
     * @param resourceSharingPolicyId The unique identifier of the resource sharing policy.
     * @return A list of {@link SharedResourceAttribute} associated with the given policy.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while retrieving the shared resource
     *                                                 attributes.
     */
    List<SharedResourceAttribute> getSharedResourceAttributesBySharingPolicyId(int resourceSharingPolicyId)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Retrieves shared resource attributes based on attribute type.
     *
     * @param attributeType The {@link SharedAttributeType} of the resource attribute to be retrieved.
     * @return A list of {@link SharedResourceAttribute} objects for the specified type.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while retrieving the shared resource
     *                                                 attributes.
     */
    List<SharedResourceAttribute> getSharedResourceAttributesByType(SharedAttributeType attributeType)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Retrieves shared resource attributes based on attribute ID.
     *
     * @param attributeId The unique identifier of the resource attribute to be retrieved.
     * @return A list of {@link SharedResourceAttribute} objects for the specified attribute ID.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while retrieving the shared resource
     *                                                 attributes.
     */
    List<SharedResourceAttribute> getSharedResourceAttributesById(String attributeId)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Retrieves shared resource attributes based on attribute type and ID.
     *
     * @param attributeType The {@link SharedAttributeType} of the resource attribute to be retrieved.
     * @param attributeId   The unique identifier of the attribute to be retrieved.
     * @return A list of {@link SharedResourceAttribute} objects for the specified type and ID.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while retrieving the shared resource
     *                                                 attributes.
     */
    List<SharedResourceAttribute> getSharedResourceAttributesByTypeAndId(SharedAttributeType attributeType,
                                                                         String attributeId)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Deletes shared resource attributes for a given resource sharing policy and attribute type if the specified
     * organization has permission.
     *
     * @param resourceSharingPolicyId     The unique identifier of the resource sharing policy.
     * @param sharedAttributeType         The {@link SharedAttributeType} to be deleted.
     * @param sharingPolicyInitiatedOrgId The ID of the organization initiating the share request.
     *                                    The deletion will only be successful if the initiating organization
     *                                    has permission to delete shared attributes.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while deleting the shared resource attributes.
     */
    void deleteSharedResourceAttributesByResourceSharingPolicyId(int resourceSharingPolicyId,
                                                                 SharedAttributeType sharedAttributeType,
                                                                 String sharingPolicyInitiatedOrgId)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Deletes a shared resource attribute based on its attribute type and unique identifier if the specified
     * organization has permission.
     *
     * @param attributeType               The {@link SharedAttributeType} of the attribute to be deleted.
     * @param attributeId                 The unique identifier of the attribute to be deleted.
     * @param sharingPolicyInitiatedOrgId The ID of the organization initiating the share request.
     *                                    The deletion will only be successful if the initiating organization
     *                                    has permission to delete shared attributes.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while deleting the shared resource attribute.
     */
    void deleteSharedResourceAttributeByAttributeTypeAndId(SharedAttributeType attributeType, String attributeId,
                                                           String sharingPolicyInitiatedOrgId)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Adds a resource sharing policy along with its associated shared resource attributes in a single transaction.
     *
     * @param resourceSharingPolicy    The {@link ResourceSharingPolicy} containing details such as resource type,
     *                                 initiating organization, policy holding organization, and sharing policy.
     *                                 Must not be {@code null}.
     * @param sharedResourceAttributes A list of {@link SharedResourceAttribute} objects associated with the resource
     *                                 sharing policy. Must not be {@code null} or empty.
     * @return {@code true} if both the resource sharing policy and the shared resource attributes were added
     * successfully.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while adding the resource sharing policy or
     *                                                 the shared resource attributes.
     */
    boolean addResourceSharingPolicyWithAttributes(ResourceSharingPolicy resourceSharingPolicy,
                                                   List<SharedResourceAttribute> sharedResourceAttributes)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Retrieves a map of resource sharing policies along with their associated shared resource attributes
     * for the given list of policy holding organization IDs.
     *
     * @param policyHoldingOrganizationIds A list of organization IDs whose resource sharing policies along with
     *                                     shared resource attributes need to be retrieved.
     *                                     Must not be {@code null} or empty.
     * @return A nested map where:
     * - The first key is the organization ID.
     * - The second key is the {@link ResourceSharingPolicy}.
     * - The value is a list of {@link SharedResourceAttribute} associated with the policy.
     * If no matching policies or attributes are found, an empty map will be returned.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while retrieving the resource sharing
     *                                                 policies or shared attributes.
     */
    Map<String, Map<ResourceSharingPolicy, List<SharedResourceAttribute>>>
    getResourceSharingPoliciesWithSharedAttributes(List<String> policyHoldingOrganizationIds)
            throws ResourceSharingPolicyMgtServerException;

    /**
     * Deletes a resource sharing policy based on its resource type and resource ID.
     * This method is intended to be used when a resource (e.g. user) is being deleted independently of the policies.
     * It ensures that all related resource sharing policies associated with the specified resource are deleted
     * as part of the resource deletion process.
     *
     * @param resourceType The {@link ResourceType} of the resource whose policy needs to be deleted.
     *                     Must not be {@code null}.
     * @param resourceId   The unique identifier of the resource whose sharing policy is to be deleted.
     *                     Must not be {@code null} or empty.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while deleting the resource sharing policy.
     */
    default void deleteResourceSharingPolicyByResourceTypeAndId(ResourceType resourceType, String resourceId)
            throws ResourceSharingPolicyMgtServerException {

        throw new NotImplementedException(
                "deleteResourceSharingPolicyByResourceTypeAndId method is not implemented in " + this.getClass());
    }

    /**
     * Deletes a shared resource attribute based on its attribute type and unique identifier.
     * This method is intended to be used when a resource (e.g. roles) is being deleted independently of the policies.
     * It ensures that all corresponding shared resource attributes associated with the specified attribute are deleted
     * as part of the attribute deletion process.
     *
     * @param attributeType The {@link SharedAttributeType} of the attribute to be deleted.
     *                      Must not be {@code null}.
     * @param attributeId   The unique identifier of the attribute to be deleted.
     *                      Must not be {@code null} or empty.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while deleting the shared resource attribute.
     */
    default void deleteSharedResourceAttributeByAttributeTypeAndId(SharedAttributeType attributeType,
                                                                   String attributeId)
            throws ResourceSharingPolicyMgtServerException {

        throw new NotImplementedException(
                "deleteSharedResourceAttributeByAttributeTypeAndId method is not implemented in " + this.getClass());
    }

    /**
     * Deletes all resource sharing policies and shared resource attributes associated with a given organization.
     * This method is intended to be used when an organization is being deleted. It ensures that all resource sharing
     * policies and corresponding shared resource attributes related to the specified organization are also deleted
     * as part of the organization's deletion process.
     *
     * @param organizationId The unique identifier of the organization whose policies and attributes need to be deleted.
     *                       Must not be {@code null} or empty.
     * @throws ResourceSharingPolicyMgtServerException If an error occurs while deleting the resource sharing policies
     *                                                 or attributes.
     */
    default void deleteResourceSharingPoliciesAndAttributesByOrganizationId(String organizationId)
            throws ResourceSharingPolicyMgtServerException {

        throw new NotImplementedException(
                "deleteResourceSharingPoliciesAndAttributesByOrganizationId method is not implemented in " +
                        this.getClass());
    }

    /**
     * Retrieves a map of resource sharing policies and their associated shared resource attributes
     * for a given initiating organization ID, resource type, and resource ID.
     *
     * @param initiatingOrganizationId The ID of the organization initiating the resource sharing. Must be a valid ID.
     * @param resourceType             The type of the resource being shared. Must not be {@code null}.
     * @param resourceId               The unique identifier of the resource being shared. Must not be {@code null}.
     * @return A map where:
     * - The key is the {@link ResourceSharingPolicy} applicable to the initiating organization for the given resource.
     * - The value is a list of {@link SharedResourceAttribute} associated with the policy.
     * If no matching policies or attributes are found, an empty map will be returned.
     * @throws ResourceSharingPolicyMgtException If an error occurs while retrieving the resource sharing
     *                                           policies or shared attributes.
     */
    default Map<ResourceSharingPolicy, List<SharedResourceAttribute>>
    getResourceSharingPolicyByInitiatingOrgId(String initiatingOrganizationId, String resourceType, String resourceId)
            throws ResourceSharingPolicyMgtException {

        throw new NotImplementedException(
                "getResourceSharingPolicyByInitiatingOrgId method is not implemented in " + this.getClass());
    }

    /**
     * Retrieves a list of resource sharing policies associated with the given policy holding organization IDs
     * for a given resource type.
     *
     * @param policyHoldingOrganizationIds  A list of organization IDs whose policies need to be retrieved.
     *                                      Must not be {@code null} or empty.
     * @param resourceType                  The type of the resource being shared. Must not be {@code null}.
     * @return A list of {@link ResourceSharingPolicy} objects for the specified organization IDs.
     * @throws ResourceSharingPolicyMgtException If an error occurs while retrieving the resource sharing policies.
     */
    default List<ResourceSharingPolicy> getResourceSharingPoliciesByResourceType(
            List<String> policyHoldingOrganizationIds, String resourceType)
            throws ResourceSharingPolicyMgtServerException {

        throw new NotImplementedException(
                "getResourceSharingPoliciesByResourceType method is not implemented in " + this.getClass());
    }
}
