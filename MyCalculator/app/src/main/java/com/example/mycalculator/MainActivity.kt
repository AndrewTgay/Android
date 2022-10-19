package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.mycalculator.Operations.*
import com.example.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var firstNumber: Double = 0.0
    var currentOperation: Operations? = null
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addCallBacks()
    }



    private fun addCallBacks() {
        binding.btnClear.setOnClickListener {
            clearInputAtAll()
        }
        binding.btnAdd.setOnClickListener {
            initOperations(Plus)
        }
        binding.btnSubtract.setOnClickListener {
            initOperations(Minus)

        }
        binding.btnDivide.setOnClickListener {
            initOperations(Div)

        }
        binding.btnMultiply.setOnClickListener {
            initOperations(Mul)

        }
        binding.btnEquals.setOnClickListener {
            result()
        }
        binding.btnBack.setOnClickListener{
            backOneStep()
        }
    }

    private fun clearInputAtAll() {
        binding.txtInput.hint = "0"
        binding.txtInput.text = ""
        firstNumber=0.0
        currentOperation=null

    }

    private fun backOneStep() {
        binding.txtInput.text = binding.txtInput.text.subSequence(0,binding.txtInput.text.length-1)
    }

    private fun result() {
        val secondNumber = binding.txtInput.text.toString().toDouble()
        binding.txtInput.text = when (currentOperation) {
            Plus -> {
                (firstNumber + secondNumber).toString()
            }
            Minus -> {
                (firstNumber - secondNumber).toString()
            }
            Div -> {
                (firstNumber / secondNumber).toString()
            }
            Mul -> {
                (firstNumber * secondNumber).toString()
            }
            null -> "Error"
        }
    }
    private fun initOperations(op: Operations) {
        if(binding.txtInput.text!="") {
            firstNumber = binding.txtInput.text.toString().toDouble()
            clearInput()
            currentOperation = op
        }
    }
    private fun clearInput() {
        binding.txtInput.hint = binding.txtInput.text
        binding.txtInput.text = ""
    }
    fun onClickNumbers(v: View) {
        val number = (v as Button).text.toString()
        val oldText = binding.txtInput.text.toString()
        binding.txtInput.text = oldText + number

    }

}