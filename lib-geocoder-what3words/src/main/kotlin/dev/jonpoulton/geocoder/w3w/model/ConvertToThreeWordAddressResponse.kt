package dev.jonpoulton.geocoder.w3w.model

import kotlinx.serialization.Serializable

@Serializable
internal data class ConvertToThreeWordAddressResponse(
  val square: SquareModel,
  val nearestPlace: String,
  val coordinates: CoordinatesModel,
  val words: WhatThreeWords,
  val language: String,
  val map: String,
)
