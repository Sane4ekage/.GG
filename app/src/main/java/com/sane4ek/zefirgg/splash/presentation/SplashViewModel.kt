package com.sane4ek.zefirgg.splash.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sane4ek.zefirgg.core.data.ApiResult
import com.sane4ek.zefirgg.core.data.UserDataModel
import com.sane4ek.zefirgg.splash.data.SplashRepositoryImpl
import com.sane4ek.zefirgg.splash.data.model.SummonerData
import com.sane4ek.zefirgg.splash.data.model.lal.MatchData
import com.sane4ek.zefirgg.splash.domain.*
import kotlinx.coroutines.*

class SplashViewModel : ViewModel() {

    private val TAG = "splashvm"

    private val splashRepository = SplashRepositoryImpl()

    private val getSummonerUseCase by lazy { GetSummonerUseCase(splashRepository = splashRepository) }
    private val getMatchUseCase by lazy { GetMatchUseCase(splashRepository = splashRepository) }
    private val getRankedMatchesListUseCase by lazy { GetRankedMatchesListUseCase(splashRepository = splashRepository) }
    private val getMatchesListUseCase by lazy { GetMatchesListUseCase(splashRepository = splashRepository) }
    private val getQueuesUseCase by lazy { GetQueuesUseCase(splashRepository = splashRepository) }

    private val matchesIdList = ArrayList<String>()
    private val rankedMatchesIdList = ArrayList<String>()
    private val matchesList = ArrayList<MatchData>()
    private val queuesList = ArrayList<com.sane4ek.zefirgg.splash.data.Queue>()

    private val mutableSuccessLiveData = MutableLiveData<UserDataModel>()
    val successLiveData = mutableSuccessLiveData

    private val mutableSummonerLiveData = MutableLiveData<SummonerData>()
    val summonerLiveData = mutableSummonerLiveData

    private val mutableFailureLiveData = MutableLiveData<Int>()
    val failureLiveData = mutableFailureLiveData

    fun getAllInfo(summoner: SummonerData, api_key: String) = viewModelScope.launch(Dispatchers.IO) {
        val summonerTask = arrayListOf(
            getListMatches(puuid = summoner.puuid, api_key = api_key),
            getListMatchesRanked(puuid = summoner.puuid, api_key = api_key)
        )
        summonerTask.joinAll()

        // берем каждый матч по его id
        val time = System.currentTimeMillis()
        runBlocking {
            withContext(coroutineContext) {
                val matchesTask = arrayListOf<Job>()
                matchesTask.add(getQueues(id = summoner.id, api_key = api_key))
                matchesIdList.take(5).forEach {
                    matchesTask.add(getMatch(id = it, api_key = api_key))
                }
                matchesTask.joinAll()
            }
        }
        Log.i(TAG, "getAllInfo: код выполнялся ${(System.currentTimeMillis() - time).toDouble() / 1000} секунд")
        // найти где Ranked Solo/Duo
        matchesList.forEach { match ->
            rankedMatchesIdList.forEach { id ->
                if (match.metadata.matchId == id) {
                    match.info.gameMode = "Solo/Duo"
                }
            }
        }
        matchesList.sortByDescending { it.info.gameCreation }
        Log.i(TAG, "getAllInfo matchesList size: ${matchesList.size}")
        Log.i(TAG, "getAllInfo queuesList size: ${queuesList.size}")
        Log.i(TAG, "getAllInfo queuesList: $queuesList")

        // сохраняем UserDataModel и даем success response
        withContext(Dispatchers.Main) {
            mutableSuccessLiveData.value = UserDataModel(
                summoner = summonerLiveData.value!!,
                matches = matchesList,
                idsMatches = matchesIdList,
                rankedIdsMatches = rankedMatchesIdList,
                queues = queuesList
            )
        }
    }

    private fun getQueues(id: String, api_key: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val result = getQueuesUseCase.execute(id, api_key)) {
            is ApiResult.Success -> {
                queuesList.addAll(result.data)

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

    private fun getListMatches(puuid: String, api_key: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val result = getMatchesListUseCase.execute(puuid, api_key)) {
            is ApiResult.Success -> {
                matchesIdList.addAll(result.data)

                Log.i(TAG, "getListMatches success: ${result.data}")
                Log.i(TAG, "getListMatches success: ${result.data.size}")
            }
            is ApiResult.Failure -> {
                withContext(Dispatchers.Main) {
                    mutableFailureLiveData.value = result.statusCode
                }

                Log.i(TAG, "getListMatches error, code - " + result.statusCode + " " + result)
            }
        }
    }

    private fun getListMatchesRanked(puuid: String, api_key: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val result = getRankedMatchesListUseCase.execute(puuid, api_key)) {
            is ApiResult.Success -> {
                rankedMatchesIdList.addAll(result.data)

                Log.i(TAG, "getListMatches success: ${result.data}")
                Log.i(TAG, "getListMatches success: ${result.data.size}")
            }
            is ApiResult.Failure -> {
                withContext(Dispatchers.Main) {
                    mutableFailureLiveData.value = result.statusCode
                }

                Log.i(TAG, "getListMatches error, code - " + result.statusCode + " " + result)
            }
        }
    }

    // ----------------- Получить призывателя -----------------
    fun getSummoner(api_key: String) = viewModelScope.launch(Dispatchers.IO) {
        val summonerTask = getSummonerInfo(api_key = api_key)
        summonerTask.join()
    }

    private fun getSummonerInfo(api_key: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val result = getSummonerUseCase.execute(api_key)) {
            is ApiResult.Success -> {
                withContext(Dispatchers.Main) {
                    mutableSummonerLiveData.value = result.data
                }

                Log.i(TAG, "getSummonerInfo success: ${result.data}")
            }
            is ApiResult.Failure -> {
                withContext(Dispatchers.Main) {
                    mutableFailureLiveData.value = result.statusCode
                }

                Log.i(TAG, "getSummonerInfo error, code - " + result.statusCode + " " + result)
            }
        }
    }
}