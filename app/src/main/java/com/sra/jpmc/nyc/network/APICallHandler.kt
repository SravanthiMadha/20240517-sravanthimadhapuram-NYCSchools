package com.sra.jpmc.nyc.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * This helps us to make API call and handle multiple cases
 *
 */
suspend fun <T : Any> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): APIResult<T> {
    return withContext(dispatcher) {
        try {
            APIResult.Success(apiCall.invoke())
        } catch (e: Exception) {
            error(e.message ?: e.toString())
        }
    }
}

private fun <T> error(errorMessage: String, responseCode: Int? = null): APIResult<T> =
    APIResult.Error(
        responseBodyData = "Api Failure $errorMessage",
        responseCode = responseCode
    )

