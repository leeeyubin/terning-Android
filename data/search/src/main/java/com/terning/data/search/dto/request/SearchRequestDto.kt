package com.terning.data.search.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRequestDto(
    @SerialName("keyword")
    val keyword: String,
    @SerialName("sortBy")
    val sortBy: String,
    @SerialName("page")
    val page: Int,
)