package com.terning.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeUpcomingInternResponseDto(
    @SerialName("internshipAnnouncementId")
    val internshipAnnouncementId: Long,
    @SerialName("companyImage")
    val companyImage: String,
    @SerialName("title")
    val title: String,
    @SerialName("dDay")
    val dDay: String,
    @SerialName("deadline")
    val deadline: String,
    @SerialName("workingPeriod")
    val workingPeriod: String,
    @SerialName("isScrapped")
    val isScrapped: Boolean,
    @SerialName("color")
    val color: String,
    @SerialName("startYearMonth")
    val startYearMonth: String,
    @SerialName("companyInfo")
    val companyInfo: String,
)
