package com.sane4ek.zefirgg.valorant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sane4ek.zefirgg.R
import com.sane4ek.zefirgg.databinding.FragmentSplashBinding
import com.sane4ek.zefirgg.databinding.FragmentValorantBinding

class ValorantFragment : Fragment() {

    private lateinit var binding: FragmentValorantBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentValorantBinding.inflate(inflater)

        return binding.root
    }

}