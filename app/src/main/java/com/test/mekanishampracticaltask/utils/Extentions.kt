package com.test.mekanishampracticaltask.utils

import android.view.View
import com.test.mekanishampracticaltask.R


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

private val listOfColors = listOf(
    R.color.color_1,
    R.color.color_2,
    R.color.color_3,
    R.color.color_4,
    R.color.color_5,
    R.color.color_6,
    R.color.color_7,
    R.color.color_8,
    R.color.color_9,
    R.color.color_10
)

fun getRandomColor(): Int {
    return listOfColors.random()
}