package dev.jonpoulton.geocoder.positionstack.api

import dev.jonpoulton.geocoder.geocoding.Coordinates
import dev.jonpoulton.geocoder.positionstack.model.ForwardGeocodingResponse
import dev.jonpoulton.geocoder.positionstack.model.PositionStackApiKey
import dev.jonpoulton.geocoder.positionstack.model.ReverseGeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Comments pulled from [the PositionStack public API docs](https://positionstack.com/documentation) on 11/06/2023.
 */
internal interface PositionStackApi {
  /**
   * Forward geocoding is the process of converting a free-text address or place to location data. To make a forward
   * geocoding request, use the API's forward endpoint and specify your query using the query parameter. Other optional
   * parameters are available.
   */
  @GET("/v1/forward")
  suspend fun forwardGeocoding(
    @Query("access_key") apiKey: PositionStackApiKey,
    @Query("query") address: String,
    @Query("limit") limit: Int = 1,
    @Query("output") output: String = "json",
  ): ForwardGeocodingResponse

  /**
   * Reverse geocoding is the process of converting coordinates (latitude & longitude) or an IP address to location
   * data. To make a reverse geocoding request, use the API's reverse endpoint and specify your query using the query
   * parameter. Other optional parameters are available.
   */
  @GET("/v1/reverse")
  suspend fun reverseGeocoding(
    @Query("access_key") apiKey: PositionStackApiKey,
    @Query("query") coordinates: Coordinates,
    @Query("limit") limit: Int = 1,
    @Query("output") output: String = "json",
  ): ReverseGeocodingResponse
}
