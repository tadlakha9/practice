package com.example.practice.commons

import okhttp3.Call
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager


@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(OkHttpClient::class)
@EnableConfigurationProperties(OkHttpClientProperties::class)
class OkHttpClientConfiguration(
    private val okHttpClientProperties: OkHttpClientProperties,
    private val networkInterceptorProvider: ObjectProvider<NetworkInterceptor>,
    private val applicationInterceptorProvider: ObjectProvider<ApplicationInterceptor>,
) {

    @Bean
    @ConditionalOnMissingBean
    fun okHttpClient(): Call.Factory {
        val connection = okHttpClientProperties.connection
        return OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .dispatcher(dispatcher())
                .readTimeout(connection.readTimeout, MILLISECONDS)
                .writeTimeout(connection.writeTimeout, MILLISECONDS)
                .connectTimeout(connection.connectTimeout, MILLISECONDS)
                .connectionPool(ConnectionPool(connection.maxIdleConnections, connection.keepAliveDurationInSeconds, SECONDS))
                .addAllInterceptors()
                .configureSsl()
                .build()
    }


    private fun OkHttpClient.Builder.configureSsl(): OkHttpClient.Builder = if (!okHttpClientProperties.ssl.enabled) disableSslValidation() else this

    private fun OkHttpClient.Builder.disableSslValidation(): OkHttpClient.Builder {
        val noopTrustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                //we need to have that as it is. Because in case of disbaleSSL for client call, we need a noopTrustManager which does nothing

            }

            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                //we need to have that as it is. Because in case of disbaleSSL for client call, we need a noopTrustManager which does nothing
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
        val socketFactory = SSLContext.getInstance(okHttpClientProperties.cryptographicProtocol).apply { init(null, arrayOf(noopTrustManager), SecureRandom()) }.socketFactory
        return apply {
            sslSocketFactory(socketFactory, noopTrustManager)
            hostnameVerifier { _, _ -> true }
        }
    }

    private fun dispatcher(): Dispatcher {
        return Dispatcher(Dispatcher().executorService).apply {
            maxRequests = okHttpClientProperties.connection.maxRequest
            maxRequestsPerHost = okHttpClientProperties.connection.maxRequestsPerHost
        }
    }

    private fun OkHttpClient.Builder.addAllInterceptors(): OkHttpClient.Builder = apply {
        networkInterceptorProvider.orderedStream().forEach { addNetworkInterceptor(it) }
        applicationInterceptorProvider.orderedStream().forEach { addInterceptor(it) }
    }
}
