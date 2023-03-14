package dev.jonpoulton.geocoder.w3w.model

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class WhatThreeWords(private val words: String) {
  constructor(word1: String, word2: String, word3: String) : this(words = "$word1.$word2.$word3")
  constructor(words: List<String>) : this(words = words.joinToString(DOT))

  override fun toString(): String = words

  fun toList(): List<String> = words.split(DOT)

  private companion object {
    const val DOT = "."
  }
}
