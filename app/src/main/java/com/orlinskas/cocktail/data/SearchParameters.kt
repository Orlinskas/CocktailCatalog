package com.orlinskas.cocktail.data

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.orlinskas.cocktail.extensions.readBooleanValue
import com.orlinskas.cocktail.extensions.writeBooleanValue

@Keep
class SearchParameters(
    var param: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        param = if (parcel.readBooleanValue()) parcel.readString() else null
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let { parcel ->
            with(parcel) {
                writeBooleanValue(param != null)
                writeString(param)
            }
        }
    }

    override fun describeContents() = 0

    val clone: SearchParameters
        get() = SearchParameters(
            param
        )

    fun apply(params: SearchParameters) {
        param = params.param
    }

    companion object CREATOR : Parcelable.Creator<SearchParameters> {
        override fun createFromParcel(parcel: Parcel): SearchParameters {
            return SearchParameters(parcel)
        }

        override fun newArray(size: Int): Array<SearchParameters?> {
            return arrayOfNulls(size)
        }
    }
}
