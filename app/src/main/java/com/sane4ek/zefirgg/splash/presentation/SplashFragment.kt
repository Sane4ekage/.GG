package com.sane4ek.zefirgg.splash.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sane4ek.zefirgg.R
import com.sane4ek.zefirgg.core.data.UserDataModel
import com.sane4ek.zefirgg.databinding.FragmentSplashBinding
import com.sane4ek.zefirgg.splash.data.model.SummonerData
import com.sane4ek.zefirgg.utils.Consts
import com.sane4ek.zefirgg.utils.SharedPrefs
import com.sane4ek.zefirgg.utils.isNetworkConnected

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false).apply {
            viewLifecycleOwner
            splashViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        splashViewModel.summonerLiveData.observe(viewLifecycleOwner, summonerResponse())
        splashViewModel.successLiveData.observe(viewLifecycleOwner, successResponse())
        splashViewModel.failureLiveData.observe(viewLifecycleOwner, failureResponse())

        if (isNetworkConnected(requireContext())) {
            splashViewModel.getSummoner(Consts.KEY_API)
        } else {
            Toast.makeText(requireContext(), "Денис ты в хамамчике раскумарился что ли кукумбер? Включи интернет", Toast.LENGTH_LONG).show()
        }
    }

    private fun summonerResponse() = Observer<SummonerData> {
        splashViewModel.getAllInfo(it, Consts.KEY_API)
    }

    private fun successResponse() = Observer<UserDataModel> {
        SharedPrefs.saveUserDataModel(Consts.PREFS_APP_DATA, it,requireContext())
        findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
    }

    private fun failureResponse() = Observer<Int> {
        Toast.makeText(requireContext(), "Code $it", Toast.LENGTH_SHORT).show()
    }
}