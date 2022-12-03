package org.curryware.rumapplication.apimodels.organization

import com.google.gson.annotations.SerializedName


data class Orgs (

    @SerializedName("public_id"    ) var publicId     : String?       = null,
    @SerializedName("name"         ) var name         : String?       = null,
    @SerializedName("description"  ) var description  : String?       = null,
    @SerializedName("created"      ) var created      : String?       = null,
    @SerializedName("subscription" ) var subscription : Subscription? = Subscription(),
    @SerializedName("billing"      ) var billing      : Billing?      = Billing(),
    @SerializedName("settings"     ) var settings     : Settings?     = Settings()

)
