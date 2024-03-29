package com.smrahmadi.cityguide.view.place

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.Window
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.smrahmadi.cityguide.R
import com.smrahmadi.cityguide.data.model.Item
import com.smrahmadi.cityguide.utils.GlideApp
import com.smrahmadi.cityguide.utils.NetworkUtils
import com.smrahmadi.cityguide.utils.toast
import kotlinx.android.synthetic.main.activity_place.*


class PlaceActivity : AppCompatActivity() {

    companion object {
        const val ITEM_KEY = "item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_place)
        setDataFromIntent()
    }

    private fun setDataFromIntent() {
        val item: Item = intent.getParcelableExtra(ITEM_KEY)!!
        showMap(item.location)
        name.text = item.name
        type.text = item.type
        distance.text = item.distance
        address.text = item.address

        val iconSize = resources.getDimension(R.dimen.dp_32).toInt()

        GlideApp.with(this)
            .load(item.icon)
            .transition(DrawableTransitionOptions.withCrossFade())
            .override(iconSize, iconSize)
            .into(typeIcon)

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun showMap(location: Location) {
        if (NetworkUtils.isNetworkAvailable(this)) {
            val url = "http://maps.google.com/maps?q=${location.latitude},${location.longitude}"
            map.webViewClient = WebViewClient()
            map.settings.javaScriptEnabled = true
            map.loadUrl(url)
        } else
            toast(getString(R.string.no_internet_for_map))
    }
}
