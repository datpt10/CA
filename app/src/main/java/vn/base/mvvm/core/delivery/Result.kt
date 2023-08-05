package vn.base.mvvm.core.delivery

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Failure(val reason: Reason) : Result<Nothing>()
    data class  Success<out T>(val successData: T) : Result<T>()
}

//region Extensions

inline fun <T> Result<T>.onResultHandle(
    loadingBlock: () -> Unit,
    failureBlock: (Reason) -> Unit,
    successBlock: (T) -> Unit
) {
    when (this) {
        is Result.Success -> successBlock(successData)
        is Result.Failure -> failureBlock(reason)
        is Result.Loading -> loadingBlock()
    }
}

inline fun <T> Result<T>.onSuccess(successBlock: (T) -> Unit): Result<T> {
    if (this is Result.Success)
        successBlock(successData)

    return this
}

inline fun <T> Result<T>.onFailure(errorBlock: (Reason) -> Unit): Result<T> {
    if (this is Result.Failure)
        errorBlock(reason)

    return this
}

inline fun <T> Result<T>.onLoading(loadingBlock: () -> Unit): Result<T> {
    if (this is Result.Loading)
        loadingBlock()

    return this
}
//endregion