package com.example.practice.commons

import okhttp3.Call
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit


@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Retrofit::class)
@Import(OkHttpClientConfiguration::class, JacksonAutoConfiguration::class, RetrofitClientBeanRegistrar::class)
open class RetrofitClientConfiguration {

    @Bean
    open fun retrofitClientBuilder(converters: List<Converter.Factory>,
                                   adapters: List<CallAdapter.Factory>,
                                   okHttpClient: Call.Factory): RetrofitClientBuilderFactory {
        return RetrofitClientBuilderFactory(converters, adapters, okHttpClient)
    }


}
