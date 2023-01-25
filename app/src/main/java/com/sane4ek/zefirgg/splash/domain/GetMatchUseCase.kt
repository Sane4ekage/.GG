package com.sane4ek.zefirgg.splash.domain

import com.sane4ek.zefirgg.core.data.ApiResult
import com.sane4ek.zefirgg.splash.data.SplashRepository
import com.sane4ek.zefirgg.splash.data.model.MatchesListData
import com.sane4ek.zefirgg.splash.data.model.SummonerData
import com.sane4ek.zefirgg.splash.data.model.lal.MatchData

class GetMatchUseCase(private val splashRepository: SplashRepository) {

    suspend fun execute(id: String, api_key: String) : ApiResult<MatchData> {
        return splashRepository.getMatch(id, api_key)
    }
}