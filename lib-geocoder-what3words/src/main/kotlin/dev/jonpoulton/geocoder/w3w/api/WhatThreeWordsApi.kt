package dev.jonpoulton.geocoder.w3w.api

import dev.jonpoulton.geocoder.w3w.model.CoordinatesRequest
import dev.jonpoulton.geocoder.w3w.model.ResponseFormat
import dev.jonpoulton.geocoder.w3w.model.WhatThreeWordsApiKey
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Comments pulled from [the what3words public API docs](https://developer.what3words.com/public-api/docs) on
 * 21/03/2023.
 */
internal interface WhatThreeWordsApi {
  /**
   * This function will convert a latitude and longitude to a 3 word address, in the language of your choice. It
   * also returns country, the bounds of the grid square, a nearby place (such as a local town) and a link to our map
   * site.
   */
  @GET("/v3/convert-to-3wa")
  suspend fun convertToThreeWordAddress(
    @Query("key") apiKey: WhatThreeWordsApiKey,
    @Query("coordinates") coordinates: CoordinatesRequest,
    @Query("language") language: String = "en",
    @Query("format") format: ResponseFormat = ResponseFormat.Json,
  ): String
}
