package com.example.meteorpedia.adapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleGone")
fun View.setVisibility(show: Boolean = false) {
    visibility = if (show) View.VISIBLE else View.GONE
}