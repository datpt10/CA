package vn.base.mvvm.core.delivery

abstract class Reason : Throwable() {

    abstract val errorCode: Int

    abstract val errorDesc: String
}

fun Throwable.toReason() = message?.let { GenericError(it) } ?: GenericError()

//region CommonErrorDefinitions
object ReasonDescription {
    const val reason_generic = "Something went wrong"
    const val reason_network = "Slow or no internet connection."
    const val reason_empty = "Response empty data"
    const val reason_response = "Oops, seems we got a server error response"
    const val reason_timeout = "Connection timed out."
    const val reason_not_found = "Not found"
    const val reason_authorized = "User Unauthorized"
}

const val ERROR_CODE_DEFAULT = -1

sealed class ErrorReason(
    override val errorDesc: String,
    override val errorCode: Int = ERROR_CODE_DEFAULT,
) : Reason()

class GenericError(
    override val errorDesc: String = ReasonDescription.reason_generic,
    override val errorCode: Int = ERROR_CODE_DEFAULT
) :
    ErrorReason(errorDesc, errorCode)

sealed class NetworkError(
    override val errorDesc: String,
    override val errorCode: Int = ERROR_CODE_DEFAULT
) :
    ErrorReason(errorDesc, errorCode)

class ConnectionError() : NetworkError(ReasonDescription.reason_network)
class ResponseError : NetworkError(ReasonDescription.reason_response)
class EmptyResultError : NetworkError(ReasonDescription.reason_empty)
class TimeoutError : NetworkError(ReasonDescription.reason_timeout)
class NotFoundError : NetworkError(ReasonDescription.reason_not_found)
class UnAuthorizedError(error: String = ReasonDescription.reason_authorized) : NetworkError(error)
//endregion
