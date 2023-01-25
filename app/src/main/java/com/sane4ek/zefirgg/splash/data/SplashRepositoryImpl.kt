package com.sane4ek.zefirgg.splash.data

import com.sane4ek.zefirgg.core.data.ApiResult
import com.sane4ek.zefirgg.core.data.CloudDataSource
import com.sane4ek.zefirgg.core.data.RetrofitService
import com.sane4ek.zefirgg.splash.data.model.MatchesListData
import com.sane4ek.zefirgg.splash.data.model.SummonerData
import com.sane4ek.zefirgg.splash.data.model.lal.MatchData
import com.sane4ek.zefirgg.utils.Consts
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SplashRepositoryImpl : SplashRepository {

    override suspend fun getSummoner(api_key: String): ApiResult<SummonerData> {
        val call: (suspend () -> Response<SummonerData>) =  {
            RetrofitService.getRetrofit(Consts.API_RIOT_RU).getSummoner(api_key)
        }
        return CloudDataSource<SummonerData>().execute(call)
    }

    override suspend fun getListMatches(puuid: String, api_key: String): ApiResult<MatchesListData> {
        val call: (suspend () -> Response<MatchesListData>) =  {
            RetrofitService.getRetrofit(Consts.API_RIOT_EUROPE).getListMatches(puuid, api_key)
        }
        return CloudDataSource<MatchesListData>().execute(call)
    }

    override suspend fun getListMatchesRanked(puuid: String, api_key: String): ApiResult<MatchesListData> {
        val call: (suspend () -> Response<MatchesListData>) =  {
            RetrofitService.getRetrofit(Consts.API_RIOT_EUROPE).getListMatchesRanked(puuid, api_key)
        }
        return CloudDataSource<MatchesListData>().execute(call)
    }

    override suspend fun getMatch(id: String, api_key: String): ApiResult<MatchData> {
        val call: (suspend () -> Response<MatchData>) =  {
            RetrofitService.getRetrofit(Consts.API_RIOT_EUROPE).getMatch(id, api_key)
        }
        return CloudDataSource<MatchData>().execute(call)
    }

    override suspend fun getQueues(id: String, api_key: String): ApiResult<QueuesData> {
        val call: (suspend () -> Response<QueuesData>) =  {
            RetrofitService.getRetrofit(Consts.API_RIOT_RU).getQueues(id, api_key)
        }
        return CloudDataSource<QueuesData>().execute(call)
    }
}