package com.example.ny.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parceler

@Entity(tableName = "school_table")
class School : Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "dbn")
    var dbn: String

    @ColumnInfo(name = "school_name")
    var school_name: String

    @ColumnInfo(name = "overview_paragraph")
    var overview_paragraph: String

    @ColumnInfo(name = "phone_number")
    var phone_number: String

    @ColumnInfo(name = "school_email")
    var school_email: String

    @ColumnInfo(name = "website")
    var website: String

    @ColumnInfo(name = "total_students")
    var total_students: String

    @ColumnInfo(name = "primary_address_line_1")
    var primary_address_line_1: String

    @ColumnInfo(name = "city")
    var city: String

    @ColumnInfo(name = "zip")
    var zip: String

    @ColumnInfo(name = "latitude")
    var latitude: String

    @ColumnInfo(name = "longitude")
    var longitude: String

    /**
     * Parcelable support from here
     */
    constructor(dbn: String) {
        this.dbn = dbn
        school_name = ""
        longitude = ""
        latitude = ""
        zip = ""
        city = ""
        primary_address_line_1 = ""
        overview_paragraph = ""
        phone_number = ""
        school_email = ""
        website = ""
        total_students = ""
    }

    constructor(parcel: Parcel) {
        dbn = parcel.readString()!!
        school_name = parcel.readString()!!
        longitude = parcel.readString()!!
        latitude = parcel.readString()!!
        zip = parcel.readString()!!
        city = parcel.readString()!!
        primary_address_line_1 = parcel.readString()!!
        overview_paragraph = parcel.readString()!!
        phone_number = parcel.readString()!!
        school_email = parcel.readString()!!
        website = parcel.readString()!!
        total_students = parcel.readString()!!
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(dbn)
        dest.writeString(school_name)
        dest.writeString(longitude)
        dest.writeString(latitude)
        dest.writeString(zip)
        dest.writeString(city)
        dest.writeString(primary_address_line_1)
        dest.writeString(overview_paragraph)
        dest.writeString(phone_number)
        dest.writeString(school_email)
        dest.writeString(website)
        dest.writeString(total_students)
    }

    companion object {
        @JvmField
        val CREATOR: Creator<School> = object : Creator<School> {
            override fun createFromParcel(parcel: Parcel): School? {
                return School(parcel)
            }

            override fun newArray(size: Int): Array<School?> {
                return arrayOfNulls(0)
            }
        }
    }
}