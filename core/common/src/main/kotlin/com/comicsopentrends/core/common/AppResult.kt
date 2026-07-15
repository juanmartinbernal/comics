package com.comicsopentrends.core.common

/**
 * Resultado de una operación que puede fallar, usado en el límite entre capas
 * (data -> domain -> presentation) para no propagar excepciones directamente.
 */
sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    data class Error(val cause: Throwable, val message: String? = cause.message) : AppResult<Nothing>
}

inline fun <T, R> AppResult<T>.map(transform: (T) -> R): AppResult<R> = when (this) {
    is AppResult.Success -> AppResult.Success(transform(data))
    is AppResult.Error -> this
}

inline fun <T> AppResult<T>.onSuccess(action: (T) -> Unit): AppResult<T> {
    if (this is AppResult.Success) action(data)
    return this
}

inline fun <T> AppResult<T>.onError(action: (Throwable, String?) -> Unit): AppResult<T> {
    if (this is AppResult.Error) action(cause, message)
    return this
}

suspend inline fun <T> runCatchingResult(crossinline block: suspend () -> T): AppResult<T> = try {
    AppResult.Success(block())
} catch (cancellation: kotlinx.coroutines.CancellationException) {
    throw cancellation
} catch (throwable: Throwable) {
    AppResult.Error(throwable)
}
