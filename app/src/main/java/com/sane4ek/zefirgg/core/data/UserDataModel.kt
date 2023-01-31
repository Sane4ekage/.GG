package com.sane4ek.zefirgg.core.data

import com.sane4ek.zefirgg.splash.data.Queue
import com.sane4ek.zefirgg.splash.data.QueuesData
import com.sane4ek.zefirgg.splash.data.model.SummonerData
import com.sane4ek.zefirgg.splash.data.model.lal.MatchData
import java.io.Serializable

class UserDataModel (
    val summoner: SummonerData,
    val matches: ArrayList<MatchData>,
    val idsMatches: ArrayList<String>,
    val rankedIdsMatches: ArrayList<String>,
    val queues: ArrayList<Queue>,
) : Serializable