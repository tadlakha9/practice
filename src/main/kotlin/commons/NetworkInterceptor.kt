package commons

import okhttp3.Interceptor
import org.springframework.core.Ordered


interface NetworkInterceptor : Interceptor, Ordered {

    override fun getOrder() = 0
}
