package com.sane4ek.zefirgg.lol.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.service.autofill.FieldClassification.Match
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.sane4ek.zefirgg.R
import com.sane4ek.zefirgg.core.data.UserDataModel
import com.sane4ek.zefirgg.databinding.FragmentLolBinding
import com.sane4ek.zefirgg.lol.model.LolModel
import com.sane4ek.zefirgg.splash.data.Queue
import com.sane4ek.zefirgg.splash.data.model.lal.MatchData
import com.sane4ek.zefirgg.utils.Consts
import com.sane4ek.zefirgg.utils.CustomGridLayoutManager
import com.sane4ek.zefirgg.utils.SharedPrefs
import java.math.RoundingMode
import kotlin.math.roundToInt

@SuppressLint("SetTextI18n")
class LolFragment : Fragment() {

    private lateinit var binding: FragmentLolBinding
    private lateinit var userDataModel: UserDataModel
    private val lolViewModel: LolViewModel by viewModels()
    private var unloadedMatchesIds = ArrayList<String>()
    private val historyList = ArrayList<MatchData>()
    private var adapter: HistoryAdapter? = null

    private val TAG = "lolfrag"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLolBinding.inflate(inflater, container, false).apply {
            viewLifecycleOwner
            lolViewModel
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.i("hohol", "onStart: lol")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBtns()
        init()
    }

    private fun init() {
        // назначаем обсерверы
        lolViewModel.dataFromMatchesLiveData.observe(viewLifecycleOwner, getDataFromMatches())
        lolViewModel.soloQueueDataLiveData.observe(viewLifecycleOwner, getSoloQueue())
        lolViewModel.flexQueueDataLiveData.observe(viewLifecycleOwner, getFlexQueue())
        lolViewModel.matchesLiveData.observe(viewLifecycleOwner, get20Matches())

        // достаем данные пользователя
        userDataModel = SharedPrefs.getUserDataModel(Consts.PREFS_APP_DATA, requireContext())
        unloadedMatchesIds.addAll(userDataModel.idsMatches)
        unloadedMatchesIds = unloadedMatchesIds.drop(5) as ArrayList<String> // убирем те, которые грузим в сплеше (15)

        // фильтруем данные по очередям и матчам
        lolViewModel.getDataFromMatches(matches = userDataModel.matches, puuid = userDataModel.summoner.puuid)
        lolViewModel.getQueuesData(queues = userDataModel.queues)

        // показываем список матчей
        binding.rvHistory.layoutManager = object : LinearLayoutManager(requireContext()) { override fun canScrollVertically() = false }
        historyList.addAll(userDataModel.matches)
        adapter = HistoryAdapter(historyList, userDataModel.summoner) { clickOnGame(it) }
        binding.rvHistory.adapter = adapter

// ------------- Назначить текста -------------

        binding.textLvl.text = " - ${userDataModel.summoner.summonerLevel}"
    }

    private fun get20Matches() = Observer<ArrayList<MatchData>> {
        historyList.addAll(it)
        lolViewModel.getDataFromMatches(matches = historyList, puuid = userDataModel.summoner.puuid)
        adapter?.notifyItemInserted(historyList.size - 1)
        binding.historyProgressBar.visibility = View.INVISIBLE
        binding.btnLoadMore.visibility = View.VISIBLE
    }


    private fun getDataFromMatches() = Observer<LolModel> {
// ------------- Назначить текста -------------
        binding.textWins.text = "${it.loses}L"
        binding.textLoses.text = "${it.wins}W"
        binding.textKills.text = "${it.kills}"
        binding.textDeathsAssists.text = " / ${it.deaths}" + " / ${it.assists}"
        binding.textKda.text = "${it.kda}:1"

// ------------- Кольцо -------------
        initChart(it.wins, it.loses)
    }

    private fun getFlexQueue() = Observer<Queue?> { flex ->
        if (flex != null) {
            binding.flexTextLP.text = " - ${flex.leaguePoints}LP"
            binding.flexTextRank.text = getRank(flex.tier, flex.rank)
            Glide.with(requireContext()).load(getRankImageUrl(flex.tier)).into(binding.flexImageRank)
            binding.flexTextWinsLoses.text = "${flex.wins} win / ${flex.losses} lose"
            binding.flexTextWinrate.text = "Win ${((flex.wins.toDouble() / (flex.wins + flex.losses)) * 100).roundToInt()} %"
        } else {
            binding.flexTextLP.text = " =("
            binding.flexTextRank.text = "Откалибруйся"
            Glide.with(requireContext()).load(R.drawable.crying_emoji).into(binding.flexImageRank)
            binding.flexTextWinsLoses.text = "ты тушканчик"
            binding.flexTextWinrate.text = "Win sosi%"
        }
    }

    private fun getSoloQueue() = Observer<Queue?> { soloque ->
        if (soloque != null) {
            binding.soloqueTextLP.text = " - ${soloque.leaguePoints}LP"
            binding.soloqueTextRank.text = getRank(soloque.tier, soloque.rank)
            Glide.with(requireContext()).load(getRankImageUrl(soloque.tier)).into(binding.soloqueImageRank)
            binding.soloqueTextWinsLoses.text = "${soloque.wins} win / ${soloque.losses} lose"
            binding.soloqueTextWinrate.text = "Win ${((soloque.wins.toDouble() / (soloque.wins + soloque.losses)) * 100).roundToInt()} %"
        } else {
            binding.soloqueTextLP.text = " пупс"
            binding.soloqueTextRank.text = "Откалибруйся"
            Glide.with(requireContext()).load(R.drawable.crying_emoji).into(binding.soloqueImageRank)
            binding.soloqueTextWinsLoses.text = "ты фунчоза немытая"
            binding.soloqueTextWinrate.text = "Win sosi%"
        }
    }

    private fun clickOnGame(it: MatchData) = run {
        Toast.makeText(requireContext(), "пососи чуваш", Toast.LENGTH_SHORT).show()
    }

    private fun initBtns() {
        binding.btnLoadMore.setOnClickListener {
            binding.btnLoadMore.visibility = View.INVISIBLE
            val matches = ArrayList<String>()
            if (unloadedMatchesIds.size != 0) {
                val games = unloadedMatchesIds.take(3) // 20
                matches.addAll(games)
                Log.i(TAG, "initBtns: ${unloadedMatchesIds.size}")
                lolViewModel.get20Matches(matches, userDataModel.rankedIdsMatches, Consts.KEY_API)
                if (unloadedMatchesIds.size == 3) { // 20
                    unloadedMatchesIds.clear()
                } else {
                    unloadedMatchesIds = unloadedMatchesIds.drop(3) as ArrayList<String> // 20, которые только что загрузили
                    binding.historyProgressBar.visibility = View.GONE
                }
            } else {
                Toast.makeText(requireContext(), "пошел ты нахуй заебало уже грузить", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnOpenClose.setOnClickListener {
            binding.motionContainer.transitionToEnd()
        }

        binding.btnOpenClose.setOnClickListener {
            if (binding.textOpenClose.text == "Открыть") {
                binding.textDesc.maxLines = 100
                binding.textOpenClose.text = "Закрыть"
                binding.imgArrowOpenClose.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_up))
                binding.motionContainer.transitionToEnd()
            } else {
                binding.textDesc.maxLines = 2
                binding.textOpenClose.text = "Открыть"
                binding.imgArrowOpenClose.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_down))
                binding.motionContainer.transitionToStart()
            }
        }
    }

    private fun getRankImageUrl(rank: String) : String {
        var url = ""
        when (rank) {
            "BRONZE" -> {
                url = "https://lolg-cdn.porofessor.gg/img/s/league-icons-v3/160/2.png?v=8"
            }
            "SILVER" -> {
                url = "https://lolg-cdn.porofessor.gg/img/s/league-icons-v3/160/3.png?v=8"
            }
            "GOLD" -> {
                url = "https://lolg-cdn.porofessor.gg/img/s/league-icons-v3/160/4.png?v=8"
            }
            "PLATINUM" -> {
                url = "https://lolg-cdn.porofessor.gg/img/s/league-icons-v3/160/5.png?v=8"
            }
            "DIAMOND" -> {
                url = "https://lolg-cdn.porofessor.gg/img/s/league-icons-v3/160/6.png?v=8"
            }
        }
        return url
    }

    private fun getRank(rank: String, division: String) : String {
        var result = ""
        when (rank) {
            "BRONZE" -> {
                result = "Еблан "
            }
            "SILVER" -> {
                result = "Лапух "
            }
            "GOLD" -> {
                result = "Норм голд "
            }
            "PLATINUM" -> {
                result = "Ахуеть платка "
            }
            "DIAMOND" -> {
                result = "ИДИ НАХУЙ "
            }
        }
        when (division) {
            "I" -> {
                result += "1"
            }
            "II" -> {
                result += "2"
            }
            "III" -> {
                result += "3"
            }
            "IV" -> {
                result += "4"
            }
        }
        return result
    }

    private fun initChart(wins: Int, loses: Int) {
        //вычисляем процент побед
        binding.textWinsPercent.text = "${((wins.toDouble() / (wins + loses)) * 100).roundToInt()}%"

        // назначаем данные для кольца
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(loses.toFloat())) //loses
        entries.add(PieEntry(wins.toFloat())) //wins
        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.setDrawValues(false)
        dataSet.colors = mutableListOf(Color.rgb(140, 130, 255), Color.rgb(247, 199, 239))
        dataSet.iconsOffset = MPPointF(0F, 40F)
        dataSet.selectionShift = 1f // чем больше, тем кольцо меньше
        dataSet.valueFormatter = PercentFormatter()
        val data = PieData(dataSet)

        val l: Legend = binding.chart.legend
        l.isEnabled = false

        binding.chart.data = data
        binding.chart.setDrawMarkers(false)
        binding.chart.description.isEnabled = false
        binding.chart.setDrawCenterText(false)
        binding.chart.setDrawEntryLabels(false)
        binding.chart.highlightValues(null)
        binding.chart.invalidate()
        binding.chart.holeRadius = 72F
        binding.chart.setHoleColor(Color.TRANSPARENT)
        binding.chart.animateY(1000, Easing.EaseInOutQuad)
    }
}