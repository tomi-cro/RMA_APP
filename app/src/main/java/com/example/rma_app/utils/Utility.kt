package com.example.rma_app.utils

import android.content.Intent
import android.text.Html
import android.text.Spanned
import com.example.rma_app.views.activities.HomePageActivity

object Utility {
    fun setTextHTML(html: String): Spanned {
        val result: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
        return result
    }
}