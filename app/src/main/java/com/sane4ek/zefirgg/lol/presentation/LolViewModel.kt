package com.sane4ek.zefirgg.lol.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.sane4ek.zefirgg.R
import com.sane4ek.zefirgg.lol.model.LolModel
import com.sane4ek.zefirgg.splash.data.Queue
import com.sane4ek.zefirgg.splash.data.model.lal.MatchData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.RoundingMode
import kotlin.math.roundToInt

class LolViewModel : ViewModel() {

    private val mutableDataFromMatchesLiveData = MutableLiveData<LolModel>()
    val dataFromMatchesLiveData = mutableDataFromMatchesLiveData

    private val mutableSoloQueueDataLiveData = MutableLiveData<Queue?>()
    val soloQueueDataLiveData = mutableSoloQueueDataLiveData

    private val mutableFlexQueueDataLiveData = MutableLiveData<Queue?>()
    val flexQueueDataLiveData = mutableFlexQueueDataLiveData

    fun getDataFromMatches(matches: ArrayList<MatchData>, puuid: String) = viewModelScope.launch(Dispatchers.IO) {
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