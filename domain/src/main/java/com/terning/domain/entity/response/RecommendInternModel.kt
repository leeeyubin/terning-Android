package com.terning.domain.entity.response

data class RecommendInternModel(
    val internshipAnnouncementId: Long,
    val title: String,
    val dDay: String,
    val workingPeriod: String,
    val companyImage: String,
    val isScrapped: Boolean,
)