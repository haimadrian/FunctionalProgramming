package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.services

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.google.inject.Provider
import play.libs.Json

/**
 * @author Haim Adrian
 * @since 19 Sep 2021
 */
object CustomObjectMapper extends Provider[ObjectMapper] {
  override def get(): ObjectMapper = {
    val mapper = new ObjectMapper()
      .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
      .setSerializationInclusion(Include.NON_NULL)
      .setSerializationInclusion(Include.NON_EMPTY)

    // Needs to set to Json helper
    Json.setObjectMapper(mapper)

    mapper
  }
}
