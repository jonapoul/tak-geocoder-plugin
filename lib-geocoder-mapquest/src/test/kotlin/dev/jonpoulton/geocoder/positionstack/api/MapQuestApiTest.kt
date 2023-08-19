package dev.jonpoulton.geocoder.positionstack.api

import dev.jonpoulton.geocoder.core.GeocoderBuildConfig
import dev.jonpoulton.geocoder.core.TestBuildConfig
import dev.jonpoulton.geocoder.di.LenientJson
import dev.jonpoulton.geocoder.di.httpModule
import dev.jonpoulton.geocoder.geocoding.Coordinates
import dev.jonpoulton.geocoder.mapquest.api.MapQuestApi
import dev.jonpoulton.geocoder.mapquest.api.MapQuestApiWrapper
import dev.jonpoulton.geocoder.mapquest.model.ForwardGeocodingRequest
import dev.jonpoulton.geocoder.mapquest.model.ForwardGeocodingResponse
import dev.jonpoulton.geocoder.mapquest.model.MapQuestApiKey
import dev.jonpoulton.geocoder.mapquest.model.MapQuestCoordinates
import dev.jonpoulton.geocoder.mapquest.model.ReverseGeocodingLocation
import dev.jonpoulton.geocoder.mapquest.model.ReverseGeocodingRequest
import dev.jonpoulton.geocoder.mapquest.model.ReverseGeocodingResponse
import dev.jonpoulton.geocoder.mapquest.model.ReverseSuccessLocation
import dev.jonpoulton.geocoder.test.MockWebServerRule
import dev.jonpoulton.geocoder.test.buildApi
import dev.jonpoulton.geocoder.test.enqueue
import dev.jonpoulton.geocoder.test.getResourceJson
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTestRule
import java.nio.charset.Charset
import kotlin.test.assertEquals

class MapQuestApiTest {
  @get:Rule
  val webServerRule = MockWebServerRule()

  @get:Rule
  val koinTestRule = KoinTestRule.create {
    modules(
      httpModule,
      module { single<GeocoderBuildConfig> { TestBuildConfig } },
    )
  }

  private lateinit var api: MapQuestApiWrapper

  @Before
  fun before() {
    api = MapQuestApiWrapper(buildApi(webServerRule, koinTestRule, MapQuestApi::class))
  }

  @Test
  fun `Forward request body encoded properly`() = runTest {
    /* Given some unused response is loaded to be returned as a response */
    val json = getResourceJson(file = "forward-error-response.json")
    webServerRule.enqueue(body = json)

    /* When */
    val body = ForwardGeocodingRequest(location = "hello world")
    api.forwardGeocoding(API_KEY, body)

    /* Then */
    val request = webServerRule.server.takeRequest()
    val requestUrl = request.requestUrl ?: error("Null request URL")
    val queries = requestUrl.queryParameterNames.associateWith { name -> requestUrl.queryParameter(name) }
    assertEquals(
      expected = mapOf("key" to API_KEY.toString()),
      actual = queries,
    )

    val bodyJson = request.body.readString(Charset.defaultCharset())
    val parsedBody = LenientJson.decodeFromString(ForwardGeocodingRequest.serializer(), bodyJson)
    assertEquals(expected = body, actual = parsedBody)
  }

  @Test
  fun `Forward no query failure decoded properly`() = runTest {
    /* Given */
    val json = getResourceJson(file = "forward-error-response.json")
    webServerRule.enqueue(body = json)

    /* When */
    val body = ForwardGeocodingRequest(location = "hello world")
    val response = api.forwardGeocoding(API_KEY, body)

    /* Then */
    val expected = ForwardGeocodingResponse.Error(
      statusCode = 400,
      messages = listOf("Illegal argument from request: Insufficient info for location"),
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
    val body = ForwardGeocodingRequest(location = "hello world")
    val response = api.forwardGeocoding(API_KEY, body)

    /* Then */
    val expected = ForwardGeocodingResponse.Success(
      results = listOf(
        Coordinates(latitude = 38.89037, longitude = -77.03196),
      ),
    )
    assertEquals(
      expected = expected,
      actual = response,
    )
  }

  @Test
  fun `Reverse request body encoded properly`() = runTest {
    /* Given some unused response is loaded to be returned as a response */
    val json = getResourceJson(file = "reverse-success-response.json")
    webServerRule.enqueue(body = json)

    /* When */
    val body = ReverseGeocodingRequest(
      location = ReverseGeocodingLocation(
        coordinates = MapQuestCoordinates(latitude = 1.2345, longitude = -7.8901),
      ),
    )
    api.reverseGeocoding(API_KEY, body)

    /* Then */
    val request = webServerRule.server.takeRequest()
    val requestUrl = request.requestUrl ?: error("Null request URL")
    val queries = requestUrl.queryParameterNames.associateWith { name -> requestUrl.queryParameter(name) }
    assertEquals(
      expected = mapOf("key" to API_KEY.toString()),
      actual = queries,
    )

    val bodyJson = request.body.readString(Charset.defaultCharset())
    val parsedBody = LenientJson.decodeFromString(ReverseGeocodingRequest.serializer(), bodyJson)
    assertEquals(expected = body, actual = parsedBody)
  }

  @Test
  fun `Reverse success decoded properly`() = runTest {
    /* Given */
    val json = getResourceJson(file = "reverse-success-response.json")
    webServerRule.enqueue(body = json)

    /* When */
    val body = ReverseGeocodingRequest(
      location = ReverseGeocodingLocation(
        coordinates = MapQuestCoordinates(latitude = 1.2345, longitude = -7.8901),
      ),
    )
    val response = api.reverseGeocoding(API_KEY, body)

    /* Then */
    val expected = ReverseGeocodingResponse.Success(
      results = listOf(
        ReverseSuccessLocation(
          street = "802 Arkenstone Dr",
          neighbourhood = "East Arlington",
          city = "Jacksonville",
          county = "Duval",
          state = "FL",
          country = "US",
          postalCode = "32225",
          coordinates = MapQuestCoordinates(latitude = 30.33353, longitude = -81.47004),
        ),
      ),
    )
    assertEquals(
      expected = expected,
      actual = response,
    )
  }

  private companion object {
    val API_KEY = MapQuestApiKey(apiKey = "PLACEHOLDER")
  }
}
