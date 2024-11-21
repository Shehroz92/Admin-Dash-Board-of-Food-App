package eu.practice.admindashboardoffoodapp.activities.activity.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class OrderDetails(): Serializable{

    var userUid: String? = null
    var userName: String? = null
    var address: String? = null
    var totalAmount: String? = null
    var itemPushKey: String? = null
    var phoneNumber: String? = null
    var foodImage: MutableList<String>? = null
    var foodNames: MutableList<String>? = null
    var foodPrices: MutableList<String>? = null
    var foodQuantities: MutableList<Int>? = null
    var orderAccepted: Boolean = false
    var paymentReceived: Boolean = false
    var currentTime: Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalAmount = parcel.readString()
        itemPushKey = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        currentTime = parcel.readLong()
    }

    fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }

}