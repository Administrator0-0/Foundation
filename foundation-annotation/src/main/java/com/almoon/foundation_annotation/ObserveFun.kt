package com.almoon.foundation_annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ObserveFun (val value: String)