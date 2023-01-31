package com.sane4ek.zefirgg.core

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sane4ek.zefirgg.R
import com.sane4ek.zefirgg.databinding.FragmentMainBinding
import java.lang.reflect.Field

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.i("hohol", "onStart: mainfrag")
        val navController = Navigation.findNavController(requireActivity(), R.id.mainBottomNavFragmentContainer)
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.itemIconTintList = null
    }
}