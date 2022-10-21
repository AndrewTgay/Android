package com.example.shoesappfwd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shoesappfwd.databinding.FragmentWelcomeBinding

class WelcomeFragment:Fragment() {
    lateinit var binding:FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_welcome,container,false)
        callBacks()
        return binding.root
    }


    private fun callBacks() {
       binding.btnnextFromWelcome.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_instructionFragment)
        }
    }
}