package dev.jonpoulton.geocoder.plugin

import app.cash.turbine.test
import com.atakmap.android.user.geocode.GeocodeManager.Geocoder
import dev.jonpoulton.geocoder.geocoding.GeocodedState
import dev.jonpoulton.geocoder.settings.PluginPreferences
import dev.jonpoulton.geocoder.widget.self.SelfMarkerLocationMonitor
import dev.jonpoulton.geocoder.widget.self.SelfMarkerWidgetMapComponentViewModel
import gov.tak.platform.graphics.Color
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class SelfMarkerWidgetMapComponentViewModelTest {
  @get:Rule
  val mockkRule = MockKRule(this)

  @MockK
  private lateinit var locationMonitor: SelfMarkerLocationMonitor

  @MockK
  private lateinit var pluginPreferences: PluginPreferences

  @MockK
  private lateinit var mockGeocoder: Geocoder

  private lateinit var viewModel: SelfMarkerWidgetMapComponentViewModel

  @Before
  fun before() {
    every { mockGeocoder.title } returns GeocoderTitle
    viewModel = SelfMarkerWidgetMapComponentViewModel(
      locationMonitor = locationMonitor,
      pluginPreferences = pluginPreferences,
    )
  }

  @Test
  fun `Not available without tag`() = runTestCase(
    includeTag = false,
    state = GeocodedState.NotAvailable(mockGeocoder),
    expectedColor = GeocodedState.Orange,
    expectedString = "Geocoding not supported",
  )

  @Test
  fun `Not available with tag`() = runTestCase(
    includeTag = true,
    state = GeocodedState.NotAvailable(mockGeocoder),
    expectedColor = GeocodedState.Orange,
    expectedString = """
      $GeocoderTitle:
      Geocoding not supported
    """.trimIndent(),
  )

  @Test
  fun `No registered geocoders`() = runTestCase(
    includeTag = false,
    state = GeocodedState.NoGeocoders,
    expectedColor = GeocodedState.Orange,
    expectedString = "No registered geocoders",
  )

  @Test
  fun `Working state without tag`() = runTestCase(
    includeTag = false,
    state = GeocodedState.Working(mockGeocoder),
    expectedColor = Color.YELLOW,
    expectedString = "Geocoding...",
  )

  @Test
  fun `Success with tag`() = runTestCase(
    includeTag = true,
    state = GeocodedState.Success(
      string = """
        123 Road Street
        Cityville
        Country
        ABCD 1234
      """.trimIndent(),
      geocoder = mockGeocoder,
    ),
    expectedColor = Color.CYAN,
    expectedString = """
        $GeocoderTitle:
        123 Road Street
        Cityville
        Country
        ABCD 1234
    """.trimIndent(),
  )

  @Test
  fun `Success without tag`() = runTestCase(
    includeTag = false,
    state = GeocodedState.Success(
      string = """
        123 Road Street
        Cityville
        Country
        ABCD 1234
      """.trimIndent(),
      geocoder = mockGeocoder,
    ),
    expectedColor = Color.CYAN,
    expectedString = """
        123 Road Street
        Cityville
        Country
        ABCD 1234
    """.trimIndent(),
  )

  private fun runTestCase(
    includeTag: Boolean,
    state: GeocodedState,
    expectedColor: Int,
    expectedString: String,
  ) = runTest {
    setIncludeTag(includeTag)
    setGeocodedState(state)
    viewModel.geocodedState.test {
      val actualState = awaitItem() as GeocodedState.Visible
      assertEquals(expected = expectedColor, actual = actualState.color)
      assertEquals(expected = expectedString, actual = actualState.string)
      cancelAndIgnoreRemainingEvents()
    }
  }

  private fun setIncludeTag(includeTag: Boolean) {
    every { pluginPreferences.includeTag } returns mockk {
      every { asFlow() } returns flowOf(includeTag)
    }
  }

  private fun setGeocodedState(state: GeocodedState) {
    every { locationMonitor.geocodedState } returns flowOf(state)
  }

  private companion object {
    const val GeocoderTitle = "MyGeocoder"
  }
}
