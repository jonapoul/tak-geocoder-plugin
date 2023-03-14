package dev.jonpoulton.geocoder.w3w.model

@JvmInline
value class WhatThreeWordsApiKey(private val apiKey: String) {
  override fun toString(): String = apiKey
}
