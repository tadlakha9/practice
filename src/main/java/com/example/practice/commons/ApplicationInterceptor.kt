package com.example.practice.commons

import okhttp3.Interceptor
import org.springframework.core.Ordered

/**
 * Interface making an OkHttp interceptor as an application interceptor
 */
interface ApplicationInterceptor : Interceptor, Ordered {

    override fun getOrder() = 0
}
