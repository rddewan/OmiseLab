package com.richarddewan.omiselab.data.remote.response

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName

data class CharityResponseList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("logo_url")
    val logoUrl: String
){
    companion object{
        @JvmStatic
        @BindingAdapter("logoUrl")
        fun ImageView.setLogoUrl(url:String?){
            if (url != null){
                Glide.with(context)
                    .load(url)
                    .into(this)
            }
            else {
                this.visibility = View.GONE
            }
        }

    }
}