package org.curryware.rumapplication.apimodels.organization

import com.google.gson.annotations.SerializedName


data class Subscription (

    @SerializedName("id"              ) var id            : Int?     = null,
    @SerializedName("billing_plan_id" ) var billingPlanId : Int?     = null,
    @SerializedName("started"         ) var started       : Int?     = null,
    @SerializedName("expires"         ) var expires       : String?  = null,
    @SerializedName("finished"        ) var finished      : String?  = null,
    @SerializedName("type"            ) var type          : String?  = null,
    @SerializedName("is_custom"       ) var isCustom      : Boolean? = null,
    @SerializedName("is_trial"        ) var isTrial       : Boolean? = null,
    @SerializedName("is_expired"      ) var isExpired     : Boolean? = null

)