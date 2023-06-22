package com.el3asas.utils.recyclerView

import androidx.annotation.DrawableRes

data class RadioItemDataModel<T>(
    val name: String,
    val id: Int,
    @DrawableRes val img: Int? = null,
    var isChecked: Boolean = false,
    var data: T? = null
)