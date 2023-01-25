package com.sane4ek.zefirgg.splash.data

import com.sane4ek.zefirgg.core.data.ApiResult
import com.sane4ek.zefirgg.core.data.RetrofitService
import com.sane4ek.zefirgg.splash.data.model.MatchesListData
import com.sane4ek.zefirgg.splash.data.model.SummonerData
import com.sane4ek.zefirgg.splash.data.model.lal.MatchData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface SplashRepository {

    suspend fun getSummoner(api_key: String) : ApiResult<SummonerData>

    suspend fun getListMatches(puuid: String, api_key: String) : ApiResult<MatchesListData>

    suspend fun getListMatchesRanked(puuid: String, api_key: String) : ApiResult<MatchesListData>

    suspend fun getMatch(id: String, api_key: String) : ApiResult<MatchData>

    suspend fun getQueues(id: String, api_key: String) : ApiResult<QueuesData>
}