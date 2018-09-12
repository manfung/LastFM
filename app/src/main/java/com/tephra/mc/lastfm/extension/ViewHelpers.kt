package com.tephra.mc.lastfm.extension

import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadFromUrl(url: String) {

    if (url != "") {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions().centerCrop())
                .into(this)
    }
}

fun TextView.htmlText(text: String) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    } else {
        this.text = Html.fromHtml(text)
    }
    movementMethod = LinkMovementMethod.getInstance()

}