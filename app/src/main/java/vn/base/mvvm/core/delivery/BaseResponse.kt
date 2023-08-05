package vn.base.mvvm.core.delivery

import com.google.gson.annotations.SerializedName
import java.io.Serializable

abstract class BaseResponse<T>(
    @SerializedName("error_code") val timestamp: Int? = null,
    @SerializedName("message") val status: Int? = null,
    @SerializedName("data") val content: T? = null,
) : Serializable {
    fun isSuccess() = status == 0
}

fun <T> Result<BaseResponse<T>>.toResultData(preReturn: ((data: T) -> Unit)? = null): Result<T> {
    return when (this) {
        is Result.Success ->
            this.successData.toResult(preReturn)

        is Result.Failure -> this
        else -> Result.Loading
    }
}

fun <T> BaseResponse<T>.toResult(preReturn: ((data: T) -> Unit)? = null): Result<T> {
    return if (content != null) {
        preReturn?.invoke(content)
        Result.Success(content)
    } else
        Result.Failure(EmptyResultError())
}

class EmptyResponse : BaseResponse<ActionDone>()

fun Result<EmptyResponse>.toResultDataAction(preReturn: ((data: ActionDone) -> Unit)? = null) =
    when (this) {
        is Result.Success -> {
            preReturn?.invoke(ActionDone)
            Result.Success(ActionDone)
        }

        is Result.Failure -> this
        else -> Result.Loading
    }

data class ErrorResponse(
    @SerializedName("message") val message: String? = null,
    @SerializedName("error_code") val code: Int? = null
)