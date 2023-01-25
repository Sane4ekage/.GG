package com.sane4ek.zefirgg.splash.domain

import com.sane4ek.zefirgg.core.data.ApiResult
import com.sane4ek.zefirgg.splash.data.SplashRepository
import com.sane4ek.zefirgg.splash.data.model.MatchesListData
import com.sane4ek.zefirgg.splash.data.model.SummonerData

class GetRankedMatchesListUseCase(private val splashRepository: SplashRepository) {

    suspend fun execute(puuid: String, api_key: String) : ApiResult<MatchesListData> {
        return splashRepository.getListMatchesRanked(puuid, api_key)
    }
}