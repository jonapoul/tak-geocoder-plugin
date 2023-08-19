package dev.jonpoulton.geocoder.positionstack.api

import dev.jonpoulton.alakazam.test.getResourceAsStream
import dev.jonpoulton.geocoder.geocoding.Coordinates
import dev.jonpoulton.geocoder.positionstack.model.ErrorContext
import dev.jonpoulton.geocoder.positionstack.model.ErrorQuery
import dev.jonpoulton.geocoder.positionstack.model.ForwardGeocodingResponse
import dev.jonpoulton.geocoder.positionstack.model.GeocodingError
import dev.jonpoulton.geocoder.positionstack.model.PositionStackApiKey
import dev.jonpoulton.geocoder.positionstack.model.ReverseGeocodingResponse
import dev.jonpoulton.geocoder.positionstack.model.ReverseSuccessData
import dev.jonpoulton.geocoder.test.MockWebServerRule
import dev.jonpoulton.geocoder.test.buildApi
import dev.jonpoulton.geocoder.test.enqueue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class PositionStackApiTest {
  @get:Rule
  val webServerRule = MockWebServerRule()

  private lateinit var api: PositionStackApiWrapper

  @Before
  fun before() {
    api = PositionStackApiWrapper(buildApi(webServerRule))
  }

  @Test
  fun `Forward query parameters encoded properly`() = runTest {
    /* Given some unused response is loaded to be returned as a response */
    val json = getResourceJson(file = "forward-error-response-no-query.json")
    webServerRule.enqueue(body = json)

    /* When */
    val address = "hello world"
    api.forwardGeocoding(API_KEY, address = address)

    /* Then */
    val request = webServerRule.server.takeRequest()
    val requestUrl = request.requestUrl ?: error("Null request URL")
    val queries = requestUrl.queryParameterNames.associateWith { name -> requestUrl.queryParameter(name) }
    assertEquals(
      expected = mapOf(
        "access_key" to API_KEY.toString(),
        "query" to address,
        "limit" to "1",
        "output" to "json",
      ),
      actual = queries,
    )
  }

  @Test
  fun `Forward no query failure decoded properly`() = runTest {
    /* Given */
    val json = getResourceJson(file = "forward-error-response-no-query.json")
    webServerRule.enqueue(body = json)

    /* When */
    val response = api.forwardGeocoding(API_KEY, address = "")

    /* Then */
    val expected = ForwardGeocodingResponse.Error(
      error = GeocodingError(
        code = "validation_error",
        message = "Request failed with validation error",
        context = ErrorContext(
          query = ErrorQuery(
            type = "invalid_query",
            message = "query must have at 3 character's",
          ),
        ),
      ),
    )
    assertEquals(
      expected = expected,
      actual = response,
    )
  }

  @Test
  fun `Forward wrong protocol failure decoded properly`() = runTest {
    /* Given */
    val json = getResourceJson(file = "forward-error-response-wrong-protocol.json")
    webServerRule.enqueue(body = json)

    /* When */
    val response = api.forwardGeocoding(API_KEY, address = "")

    /* Then */
    val expected = ForwardGeocodingResponse.Error(
      error = GeocodingError(
        code = "https_access_restricted",
        message = "Access Restricted - Your current Subscription Plan does not support HTTPS Encryption.",
        context = null,
      ),
    )
    assertEquals(
      expected = expected,
      actual = response,
    )
  }

  @Test
  fun `Forward success decoded properly`() = runTest {
    /* Given */
    val json = getResourceJson(file = "forward-success-response.json")
    webServerRule.enqueue(body = json)

    /* When */
    val response = api.forwardGeocoding(API_KEY, address = "")

    /* Then */
    val expected = ForwardGeocodingResponse.Success(
      data = listOf(
        Coordinates(latitude = 38.897675, longitude = -77.036547),
        Coordinates(latitude = 38.897473, longitude = -77.036548),
      ),
    )
    assertEquals(
      expected = expected,
      actual = response,
    )
  }

  @Test
  fun `Reverse success decoded properly`() = runTest {
    /* Given */
    val json = getResourceJson(file = "reverse-success-response.json")
    webServerRule.enqueue(body = json)

    /* When */
    val response = api.reverseGeocoding(
      apiKey = API_KEY,
      coordinates = Coordinates(latitude = 40.763841, longitude = -73.972972),
    )

    /* Then */
    val expected = ReverseGeocodingResponse.Success(
      data = listOf(
        ReverseSuccessData(
          name = "Apple Store",
          number = "767",
          postalCode = "10153",
          street = "5th Avenue",
          region = "New York",
          regionCode = "NY",
          county = "New York County",
          locality = "New York",
          administrativeArea = null,
          neighbourhood = "Midtown East",
          country = "United States",
          countryCode = "USA",
          continent = "North America",
          label = "Apple Store, New York, NY, USA",
        ),
      ),
    )
    assertEquals(
      expected = expected,
      actual = response,
    )
  }

  private fun getResourceJson(file: String): String {
    return getResourceAsStream(file)
      .bufferedReader()
      .use { it.readText() }
  }

  private companion object {
    val API_KEY = PositionStackApiKey(apiKey = "PLACEHOLDER")
  }
}
