package ca.uqam.ucycle.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.Exclude

data class Product(

    @get:Exclude
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var localisation: String? = null,
    var urlImage: String? = null,
    var categoryId: String? = null

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(localisation)
        parcel.writeString(urlImage)
        parcel.writeString(categoryId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}