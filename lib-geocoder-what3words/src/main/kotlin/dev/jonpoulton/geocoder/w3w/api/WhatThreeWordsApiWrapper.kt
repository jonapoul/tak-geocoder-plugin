package dev.jonpoulton.geocoder.w3w.api

import dev.jonpoulton.geocoder.core.runBlockingOrNull
import dev.jonpoulton.geocoder.di.LenientJson
import dev.jonpoulton.geocoder.w3w.model.ConvertToThreeWordAddressResponse
import dev.jonpoulton.geocoder.w3w.model.CoordinatesRequest
import dev.jonpoulton.geocoder.w3w.model.WhatThreeWordsApiKey

internal class WhatThreeWordsApiWrapper(private val api: WhatThreeWordsApi) {
  fun reverseGeocoding(
    apiKey: WhatThreeWordsApiKey,
    coordinates: CoordinatesRequest,
  ): ConvertToThreeWordAddressResponse? {
    return runBlockingOrNull {
      val responseJson = api.convertToThreeWordAddress(apiKey, coordinates)
      LenientJson.decodeFromString(ConvertToThreeWordAddressResponse.serializer(), responseJson)
    }
  }
}
