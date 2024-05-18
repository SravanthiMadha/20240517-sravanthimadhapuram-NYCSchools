package com.sra.jpmc.nyc.network

/**
 * This are the extensions for [APIResult] to handle success and error easily
 *
 * @param T
 * @param action
 * @return
 */
inline fun <T : Any> APIResult<T>.onSuccess(action: (T) -> Unit): APIResult<T> {
    if (this is APIResult.Success) {
        this.data?.let { action(it) }
    }
    return this
}

inline fun <T : Any> APIResult<T>.onError(action: (APIResult.Error<*>) -> Unit): APIResult<T> {
    if (this is APIResult.Error<*>) {
        action(this)
    }
    return this
}
