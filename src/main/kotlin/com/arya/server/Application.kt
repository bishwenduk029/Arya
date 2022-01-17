package com.arya.server

import io.micronaut.runtime.Micronaut

fun main(args: Array<String>) {
	Micronaut.build()
	    .args(*args)
		.packages("server", "com.arya.intent.handlers")
		.eagerInitSingletons(true)
		.start()
}

