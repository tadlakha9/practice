package com.example.practice.commons

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter


class RetrofitClientProvider(private var beanRegistry: BeanDefinitionRegistry) : ClassPathScanningCandidateComponentProvider(false) {

    private val log = LoggerFactory.getLogger(RetrofitClientProvider::class.java)

    init {
        addIncludeFilter(AnnotationTypeFilter(RetrofitClient::class.java, true, true))
    }

    override fun isCandidateComponent(beanDefinition: AnnotatedBeanDefinition): Boolean {
        val isInterface = beanDefinition.metadata.isInterface
        if (!isInterface) {
            log.warn("${RetrofitClient::class} can only be specified on interfaces. Remove it from ${beanDefinition.beanClassName}")
        }
        return isInterface
    }

    override fun getRegistry(): BeanDefinitionRegistry = beanRegistry
}
