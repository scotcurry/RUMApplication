package org.curryware.rumapplication.apimodels.organization

import com.google.gson.annotations.SerializedName


data class Settings (

    @SerializedName("saml"                          ) var saml                       : Saml?                       = Saml(),
    @SerializedName("saml_can_be_enabled"           ) var samlCanBeEnabled           : Boolean?                    = null,
    @SerializedName("saml_login_url"                ) var samlLoginUrl               : String?                     = null,
    @SerializedName("saml_idp_metadata_uploaded"    ) var samlIdpMetadataUploaded    : Boolean?                    = null,
    @SerializedName("saml_idp_endpoint"             ) var samlIdpEndpoint            : String?                     = null,
    @SerializedName("saml_idp_metadata_valid_until" ) var samlIdpMetadataValidUntil  : String?                     = null,
    @SerializedName("saml_idp_initiated_login"      ) var samlIdpInitiatedLogin      : SamlIdpInitiatedLogin?      = SamlIdpInitiatedLogin(),
    @SerializedName("saml_strict_mode"              ) var samlStrictMode             : SamlStrictMode?             = SamlStrictMode(),
    @SerializedName("saml_autocreate_access_role"   ) var samlAutocreateAccessRole   : String?                     = null,
    @SerializedName("saml_autocreate_users_domains" ) var samlAutocreateUsersDomains : SamlAutocreateUsersDomains? = SamlAutocreateUsersDomains(),
    @SerializedName("private_widget_share"          ) var privateWidgetShare         : Boolean?                    = null,
    @SerializedName("custom_landing_page"           ) var customLandingPage          : String?                     = null,
    @SerializedName("default_landing_page"          ) var defaultLandingPage         : String?                     = null,
    @SerializedName("manage_reports"                ) var manageReports              : String?                     = null

)
