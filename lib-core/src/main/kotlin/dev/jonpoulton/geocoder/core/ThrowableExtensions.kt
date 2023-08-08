package dev.jonpoulton.geocoder.core

fun Throwable.requireMessage(): String = this.message ?: this.javaClass.simpleName
