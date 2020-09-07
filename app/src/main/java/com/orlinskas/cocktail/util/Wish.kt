package com.orlinskas.cocktail.util

import com.orlinskas.cocktail.exception.HandledException

/**
 * Represents fail or success principle
 */
class Wish<out S>(private val successValue: S?, private val exceptionValue: HandledException?) {

    companion object {

        /**
         * Runs block of code if any of [HandledException] is thrown it will be returned as [Wish] with failure
         */
        fun <S> of(block: () -> (S)): Wish<S> {
            try {
                return Wish(block.invoke())
            } catch (ex: Exception) {
                if (ex is HandledException) {
                    return Wish(ex)
                }
                throw ex
            }
        }
    }

    constructor(success: S) : this(success, null)
    constructor(exception: HandledException) : this(null, exception)

    /**
     * Returns success result - [ok] should be checked first
     */
    val success: S
        get() = successValue!!

    /**
     * Returns failure result - [ok] should be checked first
     */
    val failure: HandledException
        get() = exceptionValue!!

    /**
     * Returns true if [Wish] has [success] result
     */
    val ok: Boolean
        get() = successValue != null

    /**
     * Maps result to required success data with map block
     */
    fun <T> map(block: (S) -> (T)): Wish<T> =
        if (ok) {
            Wish(block.invoke(success))
        } else {
            Wish(failure)
        }

    /**
     * Maps failure
     */
    fun mapError(block: (HandledException) -> HandledException): Wish<S> =
        if (ok) {
            Wish(success)
        } else {
            Wish(block.invoke(failure))
        }

    /**
     * Executes block of code if result was successful
     */
    inline fun onSuccess(block: (S) -> (Unit)) {
        if (ok) {
            block.invoke(success)
        }
    }

    /**
     * Executes block of code if result was failed
     */
    inline fun onFailure(block: (HandledException) -> Unit) {
        if (!ok) {
            block.invoke(failure)
        }
    }
}
