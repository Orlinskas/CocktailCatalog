package com.orlinskas.cocktail.network.client

import com.google.gson.Gson
import com.orlinskas.cocktail.network.api.CocktailApi
import com.sandiplus.b2b.data.RegistrationBundle
import com.sandiplus.b2b.exception.CompositeValidationException
import com.sandiplus.b2b.exception.NetworkException
import com.sandiplus.b2b.exception.ValidationException
import com.sandiplus.b2b.extensions.call
import com.orlinskas.cocktail.network.LoginBody
import com.orlinskas.cocktail.network.LoginResponse
import com.orlinskas.cocktail.network.QRCodeBody
import com.orlinskas.cocktail.network.RegistrationFields
import com.orlinskas.cocktail.network.RegistrationValidationResponse
import com.orlinskas.cocktail.network.RestorePasswordBody
import com.sandiplus.b2b.network.api.AccountApi
import com.sandiplus.b2b.util.UniqueId
import com.sandiplus.b2b.util.ValidationFieldType
import com.sandiplus.b2b.util.Wish
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.HashMap
import javax.inject.Inject

class CocktailApiClient @Inject constructor(private val api: CocktailApi) {

    fun login(email: String, password: String): Wish<LoginResponse> {
        val body = LoginBody(email, password, UniqueId.generate())
        return api.login(body)
            .call()
    }

    fun getRegions() =
        api.getRegions()
            .call()
            .map { it.toModelList() }

    fun registerUser(bundle: RegistrationBundle): Wish<String> {

        val requestMap = HashMap<String, RequestBody>().apply {
            putTextBody(RegistrationFields.NAME, bundle.name)
            putTextBody(RegistrationFields.SURNAME, bundle.surname)
            putTextBody(RegistrationFields.PATRONYMIC, bundle.patronymic)
            putTextBody(RegistrationFields.PHONE, bundle.phone)
            putTextBody(RegistrationFields.SITE, bundle.site)
            putTextBody(RegistrationFields.EMAIL, bundle.email)
            putTextBody(RegistrationFields.PASSWORD, bundle.password)
            putTextBody(RegistrationFields.CONFIRM_PASSWORD, bundle.confirmPassword)
            putTextBody(RegistrationFields.REGION, bundle.regionRef)
        }

        val mediaTypeImage = MediaType.parse("image/*")

        var fopBody: MultipartBody.Part? = null
        bundle.fopImagePath?.let {
            val fopFile = File(bundle.fopImagePath)
            val fop = RequestBody.create(mediaTypeImage, fopFile)
            fopBody = MultipartBody.Part.createFormData(RegistrationFields.FOP_IMAGE, fopFile.name, fop)
        }

        var fee1Body: MultipartBody.Part? = null
        bundle.feePage1ImagePath?.let {
            val fee1File = File(bundle.feePage1ImagePath)
            val fee1 = RequestBody.create(mediaTypeImage, fee1File)
            fee1Body = MultipartBody.Part.createFormData(RegistrationFields.FEE_PAGE_1_IMAGE, fee1File.name, fee1)
        }

        var fee2Body: MultipartBody.Part? = null
        bundle.feePage2ImagePath?.let {
            val fee2File = File(bundle.feePage2ImagePath)
            val fee2 = RequestBody.create(mediaTypeImage, fee2File)
            fee2Body = MultipartBody.Part.createFormData(RegistrationFields.FEE_PAGE_2_IMAGE, fee2File.name, fee2)
        }

        return api.registerUser(requestMap, fopBody, fee1Body, fee2Body)
            .call()
            .map { it.message }
            .mapError {
                return@mapError if (it is NetworkException && it.body != null && it.code == 422) {
                    try {
                        val response = Gson().fromJson(it.body!!.string(), RegistrationValidationResponse::class.java)
                        val errors = mutableListOf<ValidationException>()

                        errors.addAll(response.surname.extractError(ValidationFieldType.SURNAME))
                        errors.addAll(response.name.extractError(ValidationFieldType.NAME))
                        errors.addAll(response.patronymic.extractError(ValidationFieldType.PATRONYMIC))
                        errors.addAll(response.phone.extractError(ValidationFieldType.PHONE))
                        errors.addAll(response.site.extractError(ValidationFieldType.SITE))
                        errors.addAll(response.email.extractError(ValidationFieldType.EMAIL))
                        errors.addAll(response.password.extractError(ValidationFieldType.PASSWORD))
                        errors.addAll(response.confirmPassword.extractError(ValidationFieldType.CONFIRM_PASSWORD))
                        errors.addAll(response.regionRef.extractError(ValidationFieldType.REGION))
                        errors.addAll(response.fopImagePath.extractError(ValidationFieldType.FOP))
                        errors.addAll(response.feePage1ImagePath.extractError(ValidationFieldType.FEE_PAGE_1))
                        errors.addAll(response.feePage2ImagePath.extractError(ValidationFieldType.FEE_PAGE_2))

                        CompositeValidationException(errors)
                    } catch (ex: Throwable) {
                        it
                    }
                } else it
            }
    }

    private fun HashMap<String, RequestBody>.putTextBody(field: String, value: String?) {
        value?.let {
            this[field] = RequestBody.create(MediaType.parse("text/plain"), it)
        }
    }

    private fun Array<String?>?.extractError(type: ValidationFieldType): List<ValidationException> {
        val list = mutableListOf<ValidationException>()
        this?.let {
            for (message in it) {
                if (!message.isNullOrBlank()) {
                    list.add(ValidationException(message, type))
                }
            }
        }
        return list
    }

    fun getAccount() = api.getAccount()
        .call()
        .map { it.toModel() }

    fun getOperationalData() = api.getOperationalData().call()

    fun sendQRCode(qrCode: String) = api.sendQRCode(QRCodeBody(qrCode, UniqueId.generate())).call().map { it.message }

    fun restorePassword(email: String): Wish<Boolean> = api.restorePassword(RestorePasswordBody(email)).call().map { true }
}
