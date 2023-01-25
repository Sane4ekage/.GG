package com.sane4ek.zefirgg.splash.domain

import com.sane4ek.zefirgg.core.data.ApiResult
import com.sane4ek.zefirgg.splash.data.QueuesData
import com.sane4ek.zefirgg.splash.data.SplashRepository
import com.sane4ek.zefirgg.splash.data.model.lal.MatchData

class GetQueuesUseCase(private val splashRepository: SplashRepository) {

    suspend fun execute(id: String, api_key: String) : ApiResult<QueuesData> {
        return splashRepository.getQueues(id, api_key)
    }
}