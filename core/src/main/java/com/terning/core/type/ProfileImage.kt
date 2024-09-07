package com.terning.core.type

import com.terning.core.R

enum class ProfileImage(
    val drawableResId: Int,
    val stringValue: String
) {

    BASIC(drawableResId = R.drawable.ic_terning_profile_00, stringValue = "basic"),
    LUCKY(drawableResId = R.drawable.ic_terning_profile_01, stringValue = "lucky"),
    SMART(drawableResId = R.drawable.ic_terning_profile_02, stringValue = "smart"),
    GLASS(drawableResId = R.drawable.ic_terning_profile_03, stringValue = "glass"),
    CALENDAR(drawableResId = R.drawable.ic_terning_profile_04, stringValue = "calendar"),
    PASSION(drawableResId = R.drawable.ic_terning_profile_05, stringValue = "passion");

    companion object {
        fun fromString(value: String): ProfileImage = when (value) {
            "basic" -> BASIC
            "lucky" -> LUCKY
            "smart" -> SMART
            "glass" -> GLASS
            "calendar" -> CALENDAR
            else -> PASSION
        }

        fun toIndex(profileImage: ProfileImage): Int {
            return entries.indexOf(profileImage)
        }
    }
}
