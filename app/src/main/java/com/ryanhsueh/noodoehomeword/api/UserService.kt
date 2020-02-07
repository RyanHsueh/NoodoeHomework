package com.ryanhsueh.noodoehomeword.api

import com.ryanhsueh.noodoehomeword.bean.Model
import io.reactivex.Observable
import retrofit2.http.*

interface UserService {

    @GET("login")
    fun login(@Query("username") username: String,
              @Query("password") password: String): Observable<Model.UserInfo>

    @PUT("users/{objectId}")
    fun updateUser(@Path("objectId") objectId: String,
                   @Body timezone: Model.Timezone
    ): Observable<Model.ResponseUpdate<Model.Role>>

}