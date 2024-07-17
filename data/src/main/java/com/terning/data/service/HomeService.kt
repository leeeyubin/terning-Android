package com.terning.data.service

import com.terning.data.dto.BaseResponse
import com.terning.data.dto.response.HomeRecommendInternResponseDto
import com.terning.data.dto.response.HomeTodayInternResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("api/v1/home/today")
    suspend fun getHomeTodayIntern(): BaseResponse<List<HomeTodayInternResponseDto>>

    @GET("api/v1/home")
    suspend fun getRecommendIntern(
        @Query("sortBy") sortBy: String,
        @Query("startYear") startYear: Int,
        @Query("startMonth") startMonth: Int,
    ): BaseResponse<List<HomeRecommendInternResponseDto>>
}