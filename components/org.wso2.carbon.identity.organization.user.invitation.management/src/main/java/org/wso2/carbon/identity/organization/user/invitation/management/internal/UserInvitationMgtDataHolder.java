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

package org.wso2.carbon.identity.organization.user.invitation.management.internal;

import org.wso2.carbon.identity.event.services.IdentityEventService;
import org.wso2.carbon.identity.organization.management.organization.user.association.OrganizationUserAssociationService;
import org.wso2.carbon.identity.organization.management.service.OrganizationManager;
import org.wso2.carbon.user.core.service.RealmService;

/**
 * Data holder for organization user invitation management.
 */
public class UserInvitationMgtDataHolder {

    private static final UserInvitationMgtDataHolder USER_INVITATION_MGT_DATA_HOLDER =
            new UserInvitationMgtDataHolder();
    private RealmService realmService;
    private IdentityEventService identityEventService;
    private OrganizationManager organizationManager;
    private OrganizationUserAssociationService organizationUserAssociationService;

    public static UserInvitationMgtDataHolder getInstance() {

        return USER_INVITATION_MGT_DATA_HOLDER;
    }

    public RealmService getRealmService() {

        return realmService;
    }

    public void setRealmService(RealmService realmService) {

        this.realmService = realmService;
    }

    public IdentityEventService getIdentityEventService() {

        return identityEventService;
    }

    public void setIdentityEventService(IdentityEventService identityEventService) {

        this.identityEventService = identityEventService;
    }

    public OrganizationManager getOrganizationManagerService() {

        return organizationManager;
    }

    public void setOrganizationManagerService(OrganizationManager organizationManager) {

        this.organizationManager = organizationManager;
    }

    public OrganizationUserAssociationService getOrganizationUserAssociationService() {

        return organizationUserAssociationService;
    }

    public void setOrganizationUserAssociationService(
            OrganizationUserAssociationService organizationUserAssociationService) {

        this.organizationUserAssociationService = organizationUserAssociationService;
    }
}
