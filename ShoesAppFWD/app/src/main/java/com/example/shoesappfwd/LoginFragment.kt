package com.example.shoesappfwd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import androidx.viewbinding.ViewBindings
import com.example.shoesappfwd.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {
    lateinit var binding:FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        callBacks()
        return binding.root
    }
    override fun onStart() {
        super.onStart()
    }


    private fun callBacks() {
        binding.btnlogin.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_welcomeFragment)
            //Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_welcomeFragment)
        )

        binding.btnregister.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_welcomeFragment)
            //Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_welcomeFragment)
        )
       
    }
}