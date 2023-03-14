package dev.jonpoulton.geocoder.mapquest.api

import dev.jonpoulton.geocoder.mapquest.model.ForwardGeocodingRequest
import dev.jonpoulton.geocoder.mapquest.model.ForwardGeocodingResponse
import dev.jonpoulton.geocoder.mapquest.model.MapQuestApiKey
import dev.jonpoulton.geocoder.mapquest.model.ReverseGeocodingRequest
import dev.jonpoulton.geocoder.mapquest.model.ReverseGeocodingResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Comments pulled from [the MapQuest public API docs](https://developer.mapquest.com/documentation/geocoding-api) on
 * 11/06/2023.
 */
internal interface MapQuestApi {
  /**
   * Forward geocoding (also called address geocoding) is the process of finding an associated latitude and longitude
   * for a given address.
   *
   * Example: 1060 W. Addison St., Chicago IL, 60613 returns 41.947239,-87.655636.
   */
  @POST("/geocoding/v1/address")
  suspend fun forwardGeocoding(
    @Query("key") apiKey: MapQuestApiKey,
    @Body body: ForwardGeocodingRequest,
  ): ForwardGeocodingResponse

  /**
   * The reverse geocoding service allows a latitude and longitude to be converted to a location.
   *
   * Note: For Reverse Geocode native language support to work, the client should pass a header parameter
   * accept-language which takes ISO-2 country codes as its value with the request.
   */
  @POST("/geocoding/v1/reverse")
  suspend fun reverseGeocoding(
    @Query("key") apiKey: MapQuestApiKey,
    @Body body: ReverseGeocodingRequest,
  ): ReverseGeocodingResponse
}
