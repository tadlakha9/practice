package commons

import org.slf4j.event.Level
import org.springframework.boot.context.properties.ConfigurationProperties



@ConfigurationProperties("okhttp")
class OkHttpClientProperties(
        /** Configures HTTP client TCP connections */
        val connection: Connection = Connection(),
        /** Configures HTTP client logger */
        val log: Log = Log(),
        /** Configures HTTP client TLS initiation */
        val ssl: Ssl = Ssl(),
        /** Automatically injects a token in HTTP client calls */
        val autoToken: AutoToken = AutoToken(),
        /** Configures HTTP client filters */
        val filters: Filters = Filters(),
        /** cryptographic protocol used*/
        val cryptographicProtocol: String="TLSv1.2"
) {

    data class Connection(
            /** Default TCP socket read timeout */
            val readTimeout: Long = 15_000L,
            /** Default TCP socket write timeout */
            val writeTimeout: Long = 15_000L,
            /** Default TCP socket connect timeout */
            val connectTimeout: Long = 5_000L,
            /** Maximum number of idle TCP connections in the pool. If you are targeting Wiremock, set to 0. */
            val maxIdleConnections: Int = 100,
            /** Maximum number of concurrent requests */
            val maxRequest: Int = 1000,
            /** Maximum number of concurrent requests per host */
            val maxRequestsPerHost: Int = 1000,
            /** Time to live of idle connections before being remove from connection pool */
            val keepAliveDurationInSeconds: Long = 300L,
    )

    data class Log(
            /** Enable Client requests logging */
            val enabled: Boolean = false,
            /** Log content, can be one of NONE, BASIC, HEADERS, BODY */
            /** Log level threshold */
            val level: Level = Level.DEBUG
    )

    data class Ssl(
            /** Enable TLS for all HTTP client requests. */
            val enabled: Boolean = true
    )

    data class AutoToken(
            /** Automatically injects a token in HTTP client calls */
            val enabled: Boolean = true
    )

    data class Filters(
            /** If true, replace all HTTPS requests by HTTP */
            val enforceHttp: Boolean = false,
            /** If true, remove port from request */
            val enforceHttpPort: Boolean = false,
            /** If true, replaces HTTPS redirect URIs by HTTP */
            val enforceHttpOnRedirects: Boolean = false
    )

    data class ExternalStats(
            /** Automatically sends an event for each call to an External endpoint for stats analysis */
            val enabled: Boolean = true
    )
}
