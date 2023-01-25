package com.sane4ek.zefirgg.splash.domain

import com.sane4ek.zefirgg.core.data.ApiResult
import com.sane4ek.zefirgg.splash.data.SplashRepository
import com.sane4ek.zefirgg.splash.data.model.SummonerData

class GetSummonerUseCase(private val splashRepository: SplashRepository) {

    suspend fun execute(api_key: String) : ApiResult<SummonerData> {
        return splashRepository.getSummoner(api_key)
    }
}