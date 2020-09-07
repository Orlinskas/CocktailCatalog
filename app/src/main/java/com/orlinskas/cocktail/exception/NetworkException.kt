package com.orlinskas.cocktail.exception

import android.content.Context
import com.orlinskas.cocktail.R
import com.orlinskas.cocktail.network.ErrorResponse
import okhttp3.ResponseBody

/**
 * General exception for server responses
 */
open class NetworkException(
    val code: Int,
    private val responseMessage: String?,
    val errorResponse: ErrorResponse?,
    responseBody: ResponseBody?,
    resource: Int = 0
) :
    HandledException(resource) {

    private val contentType = responseBody?.contentType()
    private val source = responseBody?.string()

    val body: ResponseBody?
        get() = if (contentType == null || source == null) {
            null
        } else {
            ResponseBody.create(contentType, source)
        }

    override fun getText(context: Context): String =
        responseMessage ?: "Network exception - $code"
}

/**
 * Exception indicates server-side errors with code 5XX
 */
class ServerException(code: Int, responseMessage: String?, errorResponse: ErrorResponse?, responseBody: ResponseBody?) :
    NetworkException(code, responseMessage, errorResponse, responseBody, R.string.error_api_server)

/**
 * Exception indicates that user token expired or not valid
 */
class UnauthorizedException(code: Int, responseMessage: String?, errorResponse: ErrorResponse?, responseBody: ResponseBody?) :
    NetworkException(code, responseMessage, errorResponse, responseBody)

/**
 * Exception that indicates that internet connection or server not available
 */
class NoConnectionException : HandledException(R.string.error_no_connection)
