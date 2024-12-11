package com.example.practice.commons


@Retention
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class RetrofitClient(val value: String, val url: String = "")
