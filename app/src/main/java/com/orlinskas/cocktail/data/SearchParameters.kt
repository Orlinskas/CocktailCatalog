package com.orlinskas.cocktail.data

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.orlinskas.cocktail.extensions.readBooleanValue
import com.orlinskas.cocktail.extensions.writeBooleanValue

@Keep
class SearchParameters(
    var param1: String? = null,
    var param2: String? = null,
    var param3: String? = null,
    var param4: String? = null,
    var param5: IntRange? = null,
    var param6: Boolean? = null,
    var param7: LongArray? = null,
    var param8: Boolean? = null,
    var param9: Boolean? = null,
    var param10: Int? = null,
    var param11: Boolean? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        param1 = if (parcel.readBooleanValue()) parcel.readString() else null,
        param2 = if (parcel.readBooleanValue()) parcel.readString() else null,
        param3 = if (parcel.readBooleanValue()) parcel.readString() else null,
        param4 = if (parcel.readBooleanValue()) parcel.readString() else null,
        param5 = if (parcel.readBooleanValue()) IntRange(
            parcel.readInt(),
            parcel.readInt()
        ) else null,
        param6 = if (parcel.readBooleanValue()) parcel.readBooleanValue() else null,
        param7 = if (parcel.readBooleanValue()) LongArray(parcel.readInt()) else null,
        param8 = if (parcel.readBooleanValue()) parcel.readBooleanValue() else null,
        param9 = if (parcel.readBooleanValue()) parcel.readBooleanValue() else null,
        param10 = if (parcel.readBooleanValue()) parcel.readInt() else null,
        param11 = if (parcel.readBooleanValue()) parcel.readBooleanValue() else null
    ) {
        param7?.let {
            if (it.isNotEmpty()) {
                parcel.readLongArray(it)
                param7 = it
            }
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let { parcel ->
            with(parcel) {
                writeBooleanValue(param1 != null)
                param1?.let {
                    writeString(it)
                }
                writeBooleanValue(param2 != null)
                param2?.let {
                    writeString(it)
                }
                writeBooleanValue(param3 != null)
                param3?.let {
                    writeString(it)
                }
                writeBooleanValue(param4 != null)
                param4?.let {
                    writeString(it)
                }
                writeBooleanValue(param5 != null)
                param5?.let {
                    writeInt(it.first)
                    writeInt(it.last)
                }
                writeBooleanValue(param6 != null)
                param6?.let {
                    writeBooleanValue(it)
                }
                writeBooleanValue(param7 != null)
                param7?.let {
                    writeInt(it.size)
                    writeLongArray(it)
                }
                writeBooleanValue(param8 != null)
                param8?.let {
                    writeBooleanValue(it)
                }
                writeBooleanValue(param9 != null)
                param9?.let {
                    writeBooleanValue(it)
                }
                writeBooleanValue(param10 != null)
                param10?.let {
                    writeInt(it)
                }
                writeBooleanValue(param11 != null)
                param11?.let {
                    writeBooleanValue(it)
                }
            }
        }
    }

    override fun describeContents() = 0

    val clone: SearchParameters
        get() = SearchParameters(
            param1,
            param2,
            param3,
            param4,
            param5,
            param6,
            param7,
            param8,
            param9,
            param10,
            param11
        )

    fun apply(params: SearchParameters) {
        param1 = params.param1
        param2 = params.param2
        param3 = params.param3
        param4 = params.param4
        param5 = params.param5
        param6 = params.param6
        param7 = params.param7
        param8 = params.param8
        param9 = params.param9
        param10 = params.param10
        param11 = params.param11
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
