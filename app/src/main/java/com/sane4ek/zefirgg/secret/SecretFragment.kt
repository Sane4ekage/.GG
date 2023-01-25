package com.sane4ek.zefirgg.secret

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sane4ek.zefirgg.R
import com.sane4ek.zefirgg.databinding.FragmentSecretBinding

class SecretFragment : Fragment() {

    private lateinit var binding: FragmentSecretBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSecretBinding.inflate(inflater)

        return binding.root
    }
}