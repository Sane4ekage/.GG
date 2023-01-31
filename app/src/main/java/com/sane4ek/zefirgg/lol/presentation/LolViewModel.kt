package com.sane4ek.zefirgg.lol.presentation

import android.service.autofill.FieldClassification.Match
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.sane4ek.zefirgg.R
import com.sane4ek.zefirgg.core.data.ApiResult
import com.sane4ek.zefirgg.core.data.UserDataModel
import com.sane4ek.zefirgg.lol.model.LolModel
import com.sane4ek.zefirgg.splash.data.Queue
import com.sane4ek.zefirgg.splash.data.SplashRepositoryImpl
import com.sane4ek.zefirgg.splash.data.model.SummonerData
import com.sane4ek.zefirgg.splash.data.model.lal.MatchData
import com.sane4ek.zefirgg.splash.domain.GetMatchUseCase
import kotlinx.coroutines.*
import java.math.RoundingMode
import kotlin.math.roundToInt

class LolViewModel : ViewModel() {

    private val TAG = "lolvm"

    private val mutableDataFromMatchesLiveData = MutableLiveData<LolModel>()
    val dataFromMatchesLiveData = mutableDataFromMatchesLiveData

    private val mutableSoloQueueDataLiveData = MutableLiveData<Queue?>()
    val soloQueueDataLiveData = mutableSoloQueueDataLiveData

    private val mutableFlexQueueDataLiveData = MutableLiveData<Queue?>()
    val flexQueueDataLiveData = mutableFlexQueueDataLiveData

    private val splashRepository = SplashRepositoryImpl()
    private val getMatchUseCase by lazy { GetMatchUseCase(splashRepository = splashRepository) }

    private val matchesList = ArrayList<MatchData>()

    private val mutableMatchesLiveData = MutableLiveData<ArrayList<MatchData>>()
    val matchesLiveData = mutableMatchesLiveData

    private val mutableFailureLiveData = MutableLiveData<Int>()
    val failureLiveData = mutableFailureLiveData

    fun get20Matches(matchesIdList: ArrayList<String>, rankedIdList: ArrayList<String>, api_key: String) = viewModelScope.launch(Dispatchers.IO) {
        // берем каждый матч по его id
        val time = System.currentTimeMillis()
        runBlocking {
            withContext(coroutineContext) {
                val matchesTask = arrayListOf<Job>()
                matchesIdList.forEach {
                    matchesTask.add(getMatch(id = it, api_key = api_key))
                }
                matchesTask.joinAll()
            }
        }
        Log.i(TAG, "getAllInfo: код выполнялся ${(System.currentTimeMillis() - time).toDouble() / 1000} секунд")
        // найти где Ranked Solo/Duo
        matchesList.forEach { match ->
            rankedIdList.forEach { id ->
                if (match.metadata.matchId == id) {
                    match.info.gameMode = "Solo/Duo"
                }
            }
        }
        matchesList.sortByDescending { it.info.gameCreation }
        Log.i(TAG, "getAllInfo matchesList size: ${matchesList.size}")

        // сохраняем UserDataModel и даем success response
        withContext(Dispatchers.Main) {
            mutableMatchesLiveData.value = matchesList
        }
        matchesList.clear()
    }

    private fun getMatch(id: String, api_key: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val result = getMatchUseCase.execute(id, api_key)) {
            is ApiResult.Success -> {
                matchesList.add(result.data)

                Log.i(TAG, "getMatch success: ${result.data}")
            }
            is ApiResult.Failure -> {
                withContext(Dispatchers.Main) {
                    mutableFailureLiveData.value = result.statusCode
                }

                Log.i(TAG, "getMatch error, code - " + result.statusCode + " " + result)
            }
        }
    }

    fun getDataFromMatches(matches: ArrayList<MatchData>, puuid: String) {
        var wins = 0
        var loses = 0
        var killsCount = 0
        var deathsCount = 0
        var assistsCount = 0
// ------------- Пробегаемся по матчам и собираем всю инфу -------------
        matches.forEach {
            val me = it.info.participants.find { it2 ->
                it2.puuid == puuid
            } // юзер  в каждом матче

            if (me!!.win) {
                wins++
            } else {
                loses++
            }

            // посчитать KDA
            killsCount += me.kills
            deathsCount += me.deaths
            assistsCount += me.assists
        }

        val averageKills = killsCount.toDouble() / matches.size
        val averageDeaths = deathsCount.toDouble() / matches.size
        val averageAssists = assistsCount.toDouble() / matches.size
        val kda = (averageKills / matches.size + averageAssists / matches.size) / (averageDeaths / matches.size)

        Log.i(TAG, "getDataFromMatches: ${            LolModel(
            wins = wins,
            loses = loses,
            killsCount = killsCount,
            deathsCount = deathsCount,
            assistsCount = assistsCount,
            kills = averageKills.toBigDecimal().setScale(1, RoundingMode.UP).toDouble(),
            deaths = averageDeaths.toBigDecimal().setScale(1, RoundingMode.UP).toDouble(),
            assists = averageAssists.toBigDecimal().setScale(1, RoundingMode.UP).toDouble(),
            kda = kda.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
        )}")
        
        mutableDataFromMatchesLiveData.postValue(
            LolModel(
                wins = wins,
                loses = loses,
                killsCount = killsCount,
                deathsCount = deathsCount,
                assistsCount = assistsCount,
                kills = averageKills.toBigDecimal().setScale(1, RoundingMode.UP).toDouble(),
                deaths = averageDeaths.toBigDecimal().setScale(1, RoundingMode.UP).toDouble(),
                assists = averageAssists.toBigDecimal().setScale(1, RoundingMode.UP).toDouble(),
                kda = kda.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
            )
        )
    }

    fun getQueuesData(queues: ArrayList<Queue>) = viewModelScope.launch(Dispatchers.IO) {
        val soloque = queues.find {
            it.queueType == "RANKED_SOLO_5x5"
        }
        if (soloque != null) {
            mutableSoloQueueDataLiveData.postValue(soloque)
        } else {
            mutableSoloQueueDataLiveData.postValue(null)
        }

        val flex = queues.find {
            it.queueType == "RANKED_TEAM_5x5"
        }
        if (flex != null) {
            mutableFlexQueueDataLiveData.postValue(flex)
        } else {
            mutableFlexQueueDataLiveData.postValue(null)
        }
    }
}