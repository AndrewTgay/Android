package com.example.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.example.aboutme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myName=MyName("Andrew Tgay")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.myName = myName
        binding.addnicknameButton.setOnClickListener {
            addnickname(it)
        }
    }

    private fun addnickname(view: View) {
        binding.apply {
            myName?.nickName=nicknameEdittext.text.toString()
            nicknameText.text = binding.nicknameEdittext.text
            invalidateAll() //to reload all the data
            nicknameEdittext.visibility = View.GONE
            addnicknameButton.visibility = View.GONE
            nicknameText.visibility = View.VISIBLE
        }
        //to hide the keyboard
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}