package com.sra.jpmc.nyc.network

/**
 * [APIResult] to hold data for any response inclduing success data or error
 *
 * @property data
 * @property responseBodyData
 * @property responseCode
 */
sealed class APIResult<T>(
    val data: T? = null,
    val responseBodyData: Any? = null,
    val responseCode: Int? = null
) {
    class Success<T>(data: T) : APIResult<T>(data)
    class Error<T>(responseBodyData: Any, responseCode: Int?) :
        APIResult<T>(null, responseBodyData, responseCode)
}
