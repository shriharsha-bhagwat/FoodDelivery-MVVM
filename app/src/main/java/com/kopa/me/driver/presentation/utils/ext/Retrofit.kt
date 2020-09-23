package com.kopa.me.driver.presentation.utils.ext

import okhttp3.Call
import okhttp3.Request
import retrofit2.Retrofit

@PublishedApi
internal inline fun Retrofit.Builder.callFactory(
        crossinline body: (Request) -> Call
) = callFactory(object : Call.Factory {
    override fun newCall(request: Request): Call = body(request)
})

/*@Suppress("NOTHING_TO_INLINE")
inline fun Retrofit.Builder.delegatingCallFactory(
        delegate: Lazy<OkHttpClient>
): Retrofit.Builder = callFactory {
    delegate.get().newCall(it)
}*/
