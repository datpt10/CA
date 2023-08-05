package vn.base.mvvm.core.retrofit

import android.text.TextUtils
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response
import vn.base.mvvm.core.delivery.ErrorResponse
import vn.base.mvvm.core.delivery.GenericError
import vn.base.mvvm.core.delivery.Result
import vn.base.mvvm.core.delivery.TimeoutError
import vn.base.mvvm.core.delivery.UnAuthorizedError
import vn.base.mvvm.core.delivery.toReason
import vn.base.mvvm.utils.extensions.logE
import java.net.HttpURLConnection

abstract class SafeExecute {
    companion object {
        private const val TAG = "SafeExecute"
    }

    protected suspend fun <T : Any> execute(call: suspend () -> Response<T>): Result<T> {

        val response: Response<T>
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            t.printStackTrace()
            return Result.Failure(t.toReason())
        }

        if (response.isSuccessful) {
            if (response.body() != null) {
                return Result.Success(response.body()!!)
            }
        } else {
            val responseCode = response.code()
            val responseMessage = response.message()
            return Result.Failure(
                when (responseCode) {
                    HttpURLConnection.HTTP_BAD_GATEWAY -> GenericError()
//                    HttpURLConnection.HTTP_NOT_FOUND -> NotFoundError()
                    HttpURLConnection.HTTP_CLIENT_TIMEOUT -> TimeoutError()
                    HttpURLConnection.HTTP_UNAUTHORIZED, HttpURLConnection.HTTP_FORBIDDEN -> {
                        var error = UnAuthorizedError(responseMessage)
                        try {
                            response.errorBody()?.let {
                                val bodyString = it.stringSuspending()
                                val errorResponse =
                                    Gson().fromJson(bodyString, ErrorResponse::class.java)
                                logE(TAG, "${errorResponse.code} - ${errorResponse.message}")
                                if (!TextUtils.isEmpty(errorResponse?.message)) {
                                    error = UnAuthorizedError(errorResponse?.message!!)
                                }
                            }
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }

                        error
                    }

                    else -> {
                        var error = GenericError(responseMessage, responseCode)
                        try {
                            response.errorBody()?.let {
                                val bodyString = it.stringSuspending()
                                val errorResponse =
                                    Gson().fromJson(bodyString, ErrorResponse::class.java)
                                logE(TAG, "${errorResponse.code} - ${errorResponse.message}")
                                if (!TextUtils.isEmpty(errorResponse.message)) {
                                    error = GenericError(
                                        errorResponse.message!!,
                                        errorResponse.code ?: responseCode
                                    )
                                }
                            }
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }

                        error
                    }
                }
            )
        }
        return Result.Failure(GenericError())
    }

    /**
     * errorBody.string() If the response body is very large this may trigger an OutOfMemoryError.
     */
    @Suppress("BlockingMethodInNonBlockingContext")
    fun ResponseBody.stringSuspending(): String = string()
}