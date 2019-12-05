package com.ryanhsueh.noodoehomeword

import com.google.gson.annotations.SerializedName

/**
 * Created by ryanhsueh on 2019-12-05
 */
object Model {
    data class UserInfo(@SerializedName("username") val username: String,
                        @SerializedName("objectId") val objectId: String,
                        @SerializedName("code") val code: String,
                        @SerializedName("isVerifiedReportEmail") val isVerifiedReportEmail: Boolean,
                        @SerializedName("reportEmail") val reportEmail: String,
                        @SerializedName("createdAt") val createdAt: String,
                        @SerializedName("updatedAt") val updatedAt: String,
                        @SerializedName("timezone") val timezone: Int,
                        @SerializedName("parameter") val parameter: Int,
                        @SerializedName("sessionToken") val sessionToken: String)

    data class Timezone(@SerializedName("timezone") val timezone: Int)

    data class ResponseUpdate(@SerializedName("updatedAt") val updatedAt: String,
                              @SerializedName("role") val role: Role)

    data class Role(@SerializedName("__op") val __op: String)

}