package com.sane4ek.zefirgg.lol.presentation

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sane4ek.zefirgg.databinding.ItemHistoryLoseBinding
import com.sane4ek.zefirgg.databinding.ItemHistoryWinBinding
import com.sane4ek.zefirgg.splash.data.model.SummonerData
import com.sane4ek.zefirgg.splash.data.model.lal.MatchData
import com.sane4ek.zefirgg.splash.data.model.lal.Participant
import com.sane4ek.zefirgg.utils.correctRoundScaleTo2
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

private const val TYPE_WIN: Int = 0
private const val TYPE_LOSE: Int = 1

class HistoryAdapter(private var matchesList: ArrayList<MatchData>, private val summoner: SummonerData,
                       private val onClickListener: (MatchData) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "historyadapter"

    inner class WinHolder(private val binding: ItemHistoryWinBinding) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        @SuppressLint("SetTextI18n")
        fun bind(model: MatchData) {
            val me = model.info.participants.find { it2 ->
                it2.puuid == summoner.puuid
            } as Participant

            // kills / deaths / assists
            binding.textKills.text = "${me.kills} / "
            binding.textDeaths.text = "${me.deaths}"
            binding.textAssists.text = " / ${me.assists}"

            // KDA:1
            if (me.deaths == 0) {
                binding.textKDA.text = "0 смертей иди нахуй"
            } else {
                val kda = (me.kills.toDouble() + me.assists.toDouble()) / me.deaths.toDouble()
                binding.textKDA.text = "${correctRoundScaleTo2(kda)}:1 KDA"
            }

            // Time
            binding.textTime.text = " - ${model.info.gameDuration / 60} м"

            // Type
            binding.textType.text = model.info.gameMode

            // CS + kills percent
            val cs = me.neutralMinionsKilled + me.totalMinionsKilled
            val myTeam = model.info.teams.find {
                it.teamId == me.teamId
            }
            if (myTeam!!.objectives.champion.kills != 0) {
                val killsPercent = (((me.kills + me.assists).toDouble() / myTeam.objectives.champion.kills.toDouble()) * 100).roundToInt()
                binding.textCSKillpercent.text = "$cs CS - ${killsPercent}% Уч. в\n уб."
            }

            // Date
            val now = Calendar.getInstance()
            val diff = now.time.time - model.info.gameEndTimestamp
            var date = 0
            if (diff < 84600000) { // часы
                date = ((diff / 1000 / 60).toDouble() / 60.toDouble()).roundToInt()
                binding.textDays.text = "${date} ч. назад"
            } else { // дни
                date = ((diff / 1000 / 60 / 60).toDouble() / 24.toDouble()).roundToInt()
                binding.textDays.text = "${date} д. назад"
            }

            // Champion
            Glide.with(context).load("https://opgg-static.akamaized.net/meta/images/lol/champion/${me.championName}.png?image=c_crop,h_103,w_103,x_9,y_9/q_auto,f_png,w_96&v=1673596760664").into(binding.imageChampion)

            // 6 Items
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item0}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.item1)
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item1}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.item2)
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item2}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.item3)
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item3}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.item4)
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item4}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.item5)
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item5}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.item6)
            // Totem
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item6}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.totem)

            // Summoner skills
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/spell/${getSummonerSkillById(me.summoner1Id)}.png?image=q_auto,f_png,w_auto&v=1673930547899")
                .into(binding.imageSpell1)
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/spell/${getSummonerSkillById(me.summoner2Id)}.png?image=q_auto,f_png,w_auto&v=1673930547899")
                .into(binding.imageSpell2)

            //click on game
            binding.layout.setOnClickListener {
                onClickListener(model)
            }
        }
    }

    inner class LoseHolder(private val binding: ItemHistoryLoseBinding) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        @SuppressLint("SetTextI18n")
        fun bind(model: MatchData) {
            val me = model.info.participants.find { it2 ->
                it2.puuid == summoner.puuid
            } as Participant

            // kills / deaths / assists
            binding.textKills.text = "${me.kills} / "
            binding.textDeaths.text = "${me.deaths}"
            binding.textAssists.text = " / ${me.assists}"

            // KDA:1
            if (me.deaths == 0) {
                binding.textKDA.text = "0 смертей иди нахуй"
            } else {
                val kda = (me.kills.toDouble() + me.assists.toDouble()) / me.deaths.toDouble()
                binding.textKDA.text = "${correctRoundScaleTo2(kda)}:1 KDA"
            }

            // Time
            binding.textTime.text = " - ${model.info.gameDuration / 60} м"

            // Type
            binding.textType.text = model.info.gameMode

            // CS + kills percent
            val cs = me.neutralMinionsKilled + me.totalMinionsKilled
            val myTeam = model.info.teams.find {
                it.teamId == me.teamId
            }
            if (myTeam!!.objectives.champion.kills != 0) {
                val killsPercent = (((me.kills + me.assists).toDouble() / myTeam.objectives.champion.kills.toDouble()) * 100).roundToInt()
                binding.textCSKillpercent.text = "$cs CS - ${killsPercent}% Уч. в\n уб."
            }

            // Date
            val now = Calendar.getInstance()
            val diff = now.time.time - model.info.gameEndTimestamp
            var date = 0
            if (diff < 84600000) { // часы
                date = ((diff / 1000 / 60).toDouble() / 60.toDouble()).roundToInt()
                binding.textDays.text = "${date} ч. назад"
            } else { // дни
                date = ((diff / 1000 / 60 / 60).toDouble() / 24.toDouble()).roundToInt()
                binding.textDays.text = "${date} д. назад"
            }

            // Champion
            Glide.with(context).load("https://opgg-static.akamaized.net/meta/images/lol/champion/${me.championName}.png?image=c_crop,h_103,w_103,x_9,y_9/q_auto,f_png,w_96&v=1673596760664").into(binding.imageChampion)

            // 6 Items
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item0}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.item1)
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item1}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.item2)
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item2}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.item3)
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item3}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.item4)
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item4}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.item5)
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item5}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.item6)
            // Totem
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/item/${me.item6}.png?image=q_auto,f_png,w_44&v=1673596760664")
                .into(binding.totem)

            // Summoner skills
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/spell/${getSummonerSkillById(me.summoner1Id)}.png?image=q_auto,f_png,w_auto&v=1673930547899")
                .into(binding.imageSpell1)
            Glide.with(context)
                .load("https://opgg-static.akamaized.net/meta/images/lol/spell/${getSummonerSkillById(me.summoner2Id)}.png?image=q_auto,f_png,w_auto&v=1673930547899")
                .into(binding.imageSpell2)

            //click on game
            binding.layout.setOnClickListener {
                onClickListener(model)
            }
        }
    }

    private fun getSummonerSkillById(id: Int) : String {
        return when (id) {
            4 -> { "SummonerFlash" }
            12 -> { "SummonerTeleport" }
            11 -> { "SummonerSmite" }
            14 -> { "SummonerDot" }
            7 -> { "SummonerHeal" }
            1 -> { "SummonerBoost" }
            6 -> { "SummonerHaste" }
            21 -> { "SummonerBarrier" }
            3 -> { "SummonerExhaust" }
            32 -> { "SummonerSnowball" }
            else -> { "" }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_WIN) {
            val view = ItemHistoryWinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            WinHolder(view)
        } else {
            val view = ItemHistoryLoseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoseHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_WIN) {
            (holder as WinHolder).bind(model = matchesList[position])
        } else {
            (holder as LoseHolder).bind(model = matchesList[position])
        }
    }

    // очистить кэщ картинок в списке
    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
//        holder.logo.setImageDrawable(null)
//        Glide.with(context).clear(holder.logo)
    }

    override fun getItemViewType(position: Int): Int {
        val me = matchesList[position].info.participants.find { it2 ->
            it2.puuid == summoner.puuid
        }
        val winningTeam = matchesList[position].info.teams.find { itTeam ->
            itTeam.win
        }
        return if (me!!.teamId == winningTeam!!.teamId) {
            TYPE_WIN
        } else {
            TYPE_LOSE
        }
    }

    override fun getItemCount(): Int {
        return matchesList.size
    }
}