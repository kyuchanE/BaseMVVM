package com.example.basemvvm.base

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.example.basemvvm.utils.load
import com.google.gson.JsonObject

/*------------------------------------------------------------------------------
 * DESC    : DataBinding사용시 자동적용
 *------------------------------------------------------------------------------*/

object BaseBindingAdapter {

    @BindingAdapter("android:visibleIf")
    @JvmStatic
    fun View.setVisibleIf(value: Boolean) {
        isVisible = value
    }

    @BindingAdapter("url")
    @JvmStatic
    fun ImageView.url(url: String) {
        if (url.isEmpty()) return
        load(url)
    }


}