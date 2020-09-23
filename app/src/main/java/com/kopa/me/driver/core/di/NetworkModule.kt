package com.kopa.me.driver.core.di

import android.content.Context
import android.content.Intent
import com.ironz.binaryprefs.Preferences
import com.kopa.me.driver.core.MedriverApp
import com.kopa.me.driver.core.utils.AppConstants
import com.kopa.me.driver.core.utils.commit
import com.kopa.me.driver.core.utils.serialization.asConverterFactory
import com.kopa.me.driver.data.datasource.remote.login.api.DriverProfileApi
import com.kopa.me.driver.data.datasource.remote.login.api.FAQApi
import com.kopa.me.driver.data.datasource.remote.login.api.LoginApi
import com.kopa.me.driver.presentation.ui.login.LoginActivity
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit


private const val BASE_URL = "http://driver.kopait.com"

private val contentType = "application/json".toMediaType()

val NetworkModule = module {


    single { provideRetrofit(androidContext = get(), preferences = get()) }
    single { provideLoginApi(retrofit = get()) }
    single { provideDriverProfileApi(retrofit = get()) }
    single { provideFAQApi(retrofit = get()) }

}

fun provideRetrofit(androidContext: Context, preferences: Preferences): Retrofit =
    Retrofit.Builder()
        .apply {
            addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            baseUrl(BASE_URL)
            if (MedriverApp.debug.enabled) {
                val okHttpClient = OkHttpClient.Builder()
                okHttpClient.addInterceptor(HttpLoggingInterceptor().apply {
					level = HttpLoggingInterceptor.Level.BODY
				})
                okHttpClient.addInterceptor(object : Interceptor {
					override fun intercept(chain: Interceptor.Chain): Response {
						val newUrl = chain
							.request()
							.url
							.newBuilder()
							.build()

						val request = chain
							.request()
							.newBuilder()
							.url(newUrl)
							.build()

						val response = chain.proceed(request)
						if (response.code == AppConstants.RESPONSE_CODE_UNAUTH) {
							navigateToLoginScreen(androidContext, preferences)
						}
						return response
					}
				})
                client(okHttpClient.build())
            }
        }
        .build()


fun navigateToLoginScreen(androidContext: Context, preferences: Preferences) {
    preferences.commit {
        putString(AppConstants.PREF_KEY_APP_TOKEN, "")
        putLong(AppConstants.PREF_KEY_DRIVER_ID, -1L)
    }

    androidContext.startActivity(
		Intent(androidContext, LoginActivity::class.java).setFlags(
			Intent.FLAG_ACTIVITY_NEW_TASK
					or Intent.FLAG_ACTIVITY_CLEAR_TOP
					or Intent.FLAG_ACTIVITY_CLEAR_TASK
		)
	)
}


fun provideLoginApi(retrofit: Retrofit): LoginApi = retrofit.create(LoginApi::class.java)

fun provideDriverProfileApi(retrofit: Retrofit): DriverProfileApi =
    retrofit.create(DriverProfileApi::class.java)

fun provideFAQApi(retrofit: Retrofit): FAQApi =
    retrofit.create(FAQApi::class.java)