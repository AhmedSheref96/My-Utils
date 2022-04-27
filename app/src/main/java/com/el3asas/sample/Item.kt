package com.el3asas.sample

import androidx.annotation.DrawableRes

data class Item(val title: String, @DrawableRes val icon: Int, val isChecked: Boolean)