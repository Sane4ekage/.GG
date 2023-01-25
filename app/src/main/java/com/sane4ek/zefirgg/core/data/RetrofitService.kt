package com.sane4ek.zefirgg.core.data

import com.sane4ek.zefirgg.splash.data.QueuesData
import com.sane4ek.zefirgg.splash.data.model.MatchesListData
import com.sane4ek.zefirgg.splash.data.model.SummonerData
import com.sane4ek.zefirgg.splash.data.model.lal.MatchData
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    //--------------- ru ---------------
    @GET("lol/summoner/v4/summoners/by-name/ZEFIROFF")
    suspend fun getSummoner(@Query("api_key") api_key: String) : Response<SummonerData>

    @GET("lol/league/v4/entries/by-summoner/{id}")
    suspend fun getQueues(@Path("id") id: String, @Query("api_key") api_key: String) : Response<QueuesData>


    //--------------- eu ---------------
    @GET("lol/match/v5/matches/by-puuid/{puuid}/ids?count=15")
    suspend fun getListMatches(@Path("puuid") puuid: String, @Query("api_key") api_key: String) : Response<MatchesListData>

    @GET("lol/match/v5/matches/by-puuid/{puuid}/ids?queue=420&count=15")
    suspend fun getListMatchesRanked(@Path("puuid") puuid: String, @Query("api_key") api_key: String) : Response<MatchesListData>

    @GET("lol/match/v5/matches/{match_id}")
    suspend fun getMatch(@Path("match_id") match_id: String, @Query("api_key") api_key: String) : Response<MatchData>

    companion object{
        fun getRetrofit(url: String): RetrofitService {
            return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
        }
    }
}