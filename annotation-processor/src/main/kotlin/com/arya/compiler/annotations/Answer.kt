package com.arya.compiler.annotations

@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Answer(val response: String)