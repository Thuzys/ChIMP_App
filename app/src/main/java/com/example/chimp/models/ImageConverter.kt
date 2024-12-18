package com.example.chimp.models

import com.example.chimp.R

/**
 * Converts a string to an icon drawable resource
 *
 * NOTE: ***This function is hardcoded only to simplify the application implementation
 *     In a real-world application, this function should be replaced with a more
 *     flexible solution***
 *
 * @return The drawable resource of the icon
 */
fun String.toIcon() =
    when(this) {
        "/defaultIcons/icon1.jpg" -> R.drawable.icon1
        "/defaultIcons/icon2.jpg" -> R.drawable.icon2
        "/defaultIcons/icon3.jpg" -> R.drawable.icon3
        "/defaultIcons/icon4.png" -> R.drawable.icon4
        "/defaultIcons/icon5.png" -> R.drawable.icon5
        "/defaultIcons/icon6.png" -> R.drawable.icon6
        "/defaultIcons/icon7.png" -> R.drawable.icon7
        "/defaultIcons/icon8.png" -> R.drawable.icon8
        "/defaultIcons/icon9.png" -> R.drawable.icon9
        "/defaultIcons/icon10.png" -> R.drawable.icon10
        "/defaultIcons/icon11.png" -> R.drawable.icon11
        "/defaultIcons/icon12.png" -> R.drawable.icon12
        "/defaultIcons/icon13.png" -> R.drawable.icon13
        "/defaultIcons/icon14.png" -> R.drawable.icon14
        "/defaultIcons/icon15.png" -> R.drawable.icon15
        "/defaultIcons/icon16.png" -> R.drawable.icon16
        "/defaultIcons/icon17.png" -> R.drawable.icon17
        "/defaultIcons/icon18.png" -> R.drawable.icon18
        "/defaultIcons/icon19.png" -> R.drawable.icon19
        "/defaultIcons/icon20.png" -> R.drawable.icon20
        else -> R.drawable.default_icon
    }