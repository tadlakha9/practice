package commons

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.config.BeanDefinitionHolder
import org.springframework.beans.factory.support.AbstractBeanDefinition
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.env.Environment
import org.springframework.core.type.AnnotationMetadata

class RetrofitClientBeanRegistrar : ImportBeanDefinitionRegistrar, EnvironmentAware {

    private lateinit var environment: Environment
    private val kafkaInClassPath: Boolean by lazy {
        try {
            Class.forName("reactor.kafka.sender.KafkaSender")
            true
        } catch (exception: ClassNotFoundException) {
            false
        }
    }

    override fun setEnvironment(environment: Environment) {
        this.environment = environment
    }

    override fun registerBeanDefinitions(importingClassMetadata: AnnotationMetadata, registry: BeanDefinitionRegistry) {
        println("---------------------------------------------inside RetrofitClientBeanRegistrar---------------------------------------------")
        RetrofitClientProvider(registry).findCandidateComponents("com.example.practice")
                .map { it to (it as AnnotatedBeanDefinition).metadata }
                .forEach { (component, metadata) -> register(component, metadata, registry) }
    }

    private fun register(component: BeanDefinition, annotationMetadata: AnnotationMetadata, registry: BeanDefinitionRegistry) {
        val beanName = component.beanClassName
        val beanDefinition = beanName?.let { beanDefinition(annotationMetadata, it) }
        val beanDefinitionHolder = beanDefinition?.let { BeanDefinitionHolder(it, beanName, arrayOf(alias(beanName))) }
        if (beanDefinitionHolder != null) {
            BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry)
        }
    }


    private fun beanDefinition(annotationMetadata: AnnotationMetadata, beanName: String): AbstractBeanDefinition {
        val attributes = getAnnotationAttributes(annotationMetadata)
        return with(BeanDefinitionBuilder.rootBeanDefinition(RetrofitFactoryBean::class.java)) {
            addConstructorArgValue(beanName)
            val serviceName = attributes?.get(RetrofitClient::value.name)?.let { resolveProperty(it.toString()) }
            val serviceUrl = attributes?.get(RetrofitClient::url.name)?.let { resolveProperty(it.toString()) }
            addConstructorArgValue(serviceName)
            addConstructorArgValue(serviceUrl)
            setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
            this.beanDefinition
        }
    }

    private fun alias(beanName: String): String {
        val className = beanName.substringAfterLast(".").replaceFirstChar { it.lowercase() }
        val suffix = RetrofitClient::class.simpleName
        return "$className$suffix"
    }

    private fun getAnnotationAttributes(metadata: AnnotationMetadata): MutableMap<String, Any>? {
        val annotation = RetrofitClient::class
        val name = annotation.qualifiedName
        return name?.let { metadata.getAnnotationAttributes(it) }
    }

    private fun resolveProperty(propertyValue: String) = environment.resolvePlaceholders(propertyValue)


}
