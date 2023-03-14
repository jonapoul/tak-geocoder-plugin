package dev.jonpoulton.geocoder.core

import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.fredporciuncula.flow.preferences.NullableSerializer
import com.fredporciuncula.flow.preferences.Preference
import com.fredporciuncula.flow.preferences.Serializer

data class PrefKey(private val key: String) {
  override fun toString(): String = key
}

data class PrefPair<T>(val key: PrefKey, val default: T) {
  constructor(key: String, default: T) : this(key.prefixedKey, default)

  private companion object {
    private val String.prefixedKey: PrefKey
      get() = PrefKey("GEOCODER_$this")
  }
}

inline fun <reified T : Enum<T>> FlowSharedPreferences.getEnum(pair: PrefPair<T>): Preference<T> =
  getEnum(pair.key.toString(), pair.default)

fun FlowSharedPreferences.getBoolean(pair: PrefPair<Boolean>): Preference<Boolean> =
  getBoolean(pair.key.toString(), pair.default)

fun FlowSharedPreferences.getString(pair: PrefPair<String>): Preference<String> =
  getString(pair.key.toString(), pair.default)

fun <T : Any> FlowSharedPreferences.getObject(pair: PrefPair<T>, serializer: Serializer<T>): Preference<T> =
  getObject(pair.key.toString(), serializer, pair.default)

fun <T : Any> FlowSharedPreferences.getNullableObject(pair: PrefPair<T?>, serializer: NullableSerializer<T>) =
  getNullableObject(pair.key.toString(), serializer, pair.default)
