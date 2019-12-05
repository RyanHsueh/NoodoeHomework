package com.ryanhsueh.noodoehomeword.api

import com.ryanhsueh.noodoehomeword.constants.Constants
import com.ryanhsueh.noodoehomeword.bean.Model
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by ryanhsueh on 2019-12-05
 */
interface HomeworkService {

    //++ Restfule api

    @GET("login")
    fun login(@Query("username") username: String,
              @Query("password") password: String): Observable<Model.UserInfo>

    @PUT("users/{objectId}")
    fun updateUser(@Path("objectId") objectId: String,
                   @Body timezone: Model.Timezone
    ): Observable<Model.ResponseUpdate<Model.Role>>

    //-- Restfule api



    companion object {
        private var sessionToken: String? = null
        fun setToken(token: String) {
            sessionToken = token
        }

        fun create(): HomeworkService {

            val httpClient = OkHttpClient.Builder()
            httpClient.addNetworkInterceptor(AddHeaderInterceptor())

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .client(httpClient.build())
                .build()

            return retrofit.create(HomeworkService::class.java)
        }

        private class AddHeaderInterceptor : Interceptor {

            override fun intercept(chain: Interceptor.Chain): Response {
                val builder = chain.request().newBuilder()
                builder.header(
                    "X-Parse-Application-Id",
                    Constants.APPLICATION_ID
                )
                builder.header(
                    "X-Parse-REST-API-Key",
                    Constants.REST_API_KEY
                )
                if (sessionToken != null) {
                    builder.header(
                        "X-Parse-Session-Token",
                        sessionToken!!
                    )
                }

                return chain.proceed(builder.build())
            }

        }
    }

}