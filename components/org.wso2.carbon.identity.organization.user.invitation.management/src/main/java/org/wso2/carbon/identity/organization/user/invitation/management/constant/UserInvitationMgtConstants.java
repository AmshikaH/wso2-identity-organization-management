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

package org.wso2.carbon.identity.organization.user.invitation.management.constant;

/**
 * Constants for organization user invitation management.
 */
public class UserInvitationMgtConstants {

    public static final String CLAIM_EMAIL_ADDRESS = "http://wso2.org/claims/emailaddress";
    public static final String INVITATION_ERROR_PREFIX = "OUI-";

    // Filter Constants
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_EXPIRED = "EXPIRED";
    public static final String FILTER_STATUS_EQ = "status eq ";
    public static final String FILTER_STATUS = "status";
    public static final String OPERATION_EQ = "eq";

    // Event Properties Constants
    public static final String EVENT_PROP_USER_NAME = "user-name";
    public static final String EVENT_PROP_EMAIL_ADDRESS = "email-address";
    public static final String EVENT_PROP_CONFIRMATION_CODE = "confirmation-code";
    public static final String EVENT_PROP_TENANT_DOMAIN = "tenant-domain";
    public static final String EVENT_PROP_REDIRECT_URL = "redirect-url";
    public static final String EVENT_PROP_SEND_TO = "send-to";
    public static final String EVENT_PROP_TEMPLATE_TYPE = "TEMPLATE_TYPE";
    public static final String ORGANIZATION_USER_INVITATION_EMAIL_TEMPLATE_TYPE = "OrganizationUserInvitation";
    public static final String EVENT_NAME_POST_ADD_INVITATION = "POST_ADD_ORGANIZATION_USER_INVITATION";

    /**
     * Error messages for organization user invitation management related errors.
     */
    public enum ErrorMessage {

        // Service layer errors
        ERROR_CODE_USER_NOT_FOUND("10011",
                "Invalid user identification provided.",
                "Could not find an user with given username %s."),
        ERROR_CODE_CREATE_INVITATION("10012",
                "Unable to create the invitation.",
                "Could not create the invitation to the user %s."),
        ERROR_CODE_INVALID_CONFIRMATION_CODE("10013",
                "Invalid confirmation code.",
                "Could not validate the confirmation code %s."),
        ERROR_CODE_INVALID_INVITATION_ID("10014",
                "Invalid invitation id.",
                "Could not delete an invitation with the id %s."),
        ERROR_CODE_EVENT_HANDLE("10015",
                "Unable to handle the invitation create event.",
                "Could not handle the event triggered to create invitation for username %s."),
        ERROR_CODE_UNSUPPORTED_FILTER_ATTRIBUTE_VALUE("10016",
                "Unsupported filter attribute value.",
                "The filter attribute value '%s' is not supported."),
        ERROR_CODE_UNSUPPORTED_FILTER_ATTRIBUTE("10017",
                "Unsupported filter attribute.",
                "The filter attribute '%s' is not supported."),
        ERROR_CODE_ACTIVE_INVITATION_EXISTS("10018",
                "Invitation already exists.",
                "An active invitation already exists for the user %s."),
        ERROR_CODE_INVITATION_EXPIRED("10019",
                "Invitation expired.",
                "The invitation for the user %s has expired."),
        ERROR_CODE_NO_INVITATION_FOR_USER("10020",
                "No invitation available.",
                "No invitation is available for the user %s."),
        ERROR_CODE_UNABLE_TO_RESEND_INVITATION("10021",
                "Unable to resend.",
                "Could not resend the invitation to user %s."),
        ERROR_CODE_INVALID_FILTER("10022",
                "Invalid filter.",
                "The filter '%s' is invalid."),

        // DAO layer errors
        ERROR_CODE_STORE_INVITATION("10001",
                "Unable to store the invitation.",
                "Could not store the invitation details for user %s."),
        ERROR_CODE_STORE_ROLE_ASSIGNMENTS("10002",
                "Unable to store the role assignments of the invitation.",
                "Could not store the role assignment details for user %s."),
        ERROR_CODE_COMMIT_INVITATION("10003",
                "Unable to store the invitation.",
                "Could not store the invitation details."),
        ERROR_CODE_RETRIEVE_INVITATION_DETAILS("10004",
                "Unable to retrieve the invitation.",
                "Could not retrieve the invitation details for invitation id %s."),
        ERROR_CODE_RETRIEVE_ROLE_ASSIGNMENTS("10005",
                "Unable to retrieve the role assignments of the invitation.",
                "Could not retrieve the role assignments of the invitation for invitation id %s."),
        ERROR_CODE_RETRIEVE_INVITATION_BY_CONFIRMATION_ID("10006",
                "Unable to retrieve the invitation from confirmation code.",
                "Could not retrieve the invitation details for confirmation code %s."),
        ERROR_CODE_RETRIEVE_INVITATION_BY_ORG_ID("10007",
                "Unable to retrieve invitations.",
                "Could not retrieve the invitations details for organization id %s."),
        ERROR_CODE_RETRIEVE_ROLE_ASSIGNMENTS_FOR_INVITATION_BY_ORG_ID("10008",
                "Unable to retrieve role assignments.",
                "Could not retrieve the role assignments details for invitation belongs to organization id %s."),
        ERROR_CODE_RETRIEVE_INVITATIONS_FOR_ORG_ID("10009",
                "Unable to retrieve invitations for organization.",
                "Could not retrieve the invitations for organization id %s."),
        ERROR_CODE_DELETE_ROLE_ASSIGNMENTS_BY_INVITATION("10010",
                "Unable to delete role assignments.",
                "Could not delete role assignments for invitation id %s."),
        ERROR_CODE_DELETE_INVITATION_DETAILS("10011",
                "Unable to delete invitation details.",
                "Could not delete invitation details for invitation id %s."),
        ERROR_CODE_DELETE_INVITATION_BY_ID("10012",
                "Unable to delete invitation.",
                "Could not delete invitation for invitation id %s."),
        ERROR_CODE_GET_INVITATION_BY_USER("10013",
                "Unable to retrieve invitation.",
                "Could not retrieve invitation for username %s."),
        ERROR_CODE_MULTIPLE_INVITATIONS_FOR_USER("10014",
                "Multiple invitations found.",
                "Multiple invitations found for username %s."),
        ERROR_CODE_GET_INVITATION("10015",
                "Unable to get the invitation.",
                "Could not get the invitation for invitation id %s.");

        private final String code;
        private final String message;
        private final String description;

        ErrorMessage(String code, String message, String description) {

            this.code = code;
            this.message = message;
            this.description = description;
        }

        public String getCode() {

            return INVITATION_ERROR_PREFIX + code;
        }

        public String getMessage() {

            return message;
        }

        public String getDescription() {

            return description;
        }
    }

}
