package commons

import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

class RetrofitFactoryBean(private var type: Class<*>, private var name: String, private var url: String) : FactoryBean<Any>,
                                                                                                           ApplicationContextAware {

    private lateinit var context: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

    override fun getObject(): Any {
        return retrofitClientBuilder().createInstance()
                .baseUrl(RetrofitUrlFactory.createUrl(name, url, serviceUrlStub()))
                .build()
                .create(objectType)
    }

    override fun getObjectType() = type

    override fun isSingleton() = true

    private fun retrofitClientBuilder() = context.getBean<RetrofitClientBuilderFactory>()

    private fun serviceUrlStub() = context.getBeansOfType(ServiceUrlStub::class.java).values.firstOrNull() ?: ServiceUrlStub(emptyMap())
}
