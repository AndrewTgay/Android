package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.mycalculator.Operations.*

class MainActivity : AppCompatActivity() {
    lateinit var buttonPlus: Button
    lateinit var buttonMinus: Button
    lateinit var buttonDiv: Button
    lateinit var buttonMul: Button
    lateinit var buttonRes: Button
    lateinit var buttonClear: Button
    lateinit var textFinalResult: TextView
    var firstNumber: Double = 0.0
    var currentOperation: Operations? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        addCallBacks()
    }

    private fun initViews() {
        buttonPlus = findViewById(R.id.btnAdd)
        buttonMinus = findViewById(R.id.btnSubtract)
        buttonMul = findViewById(R.id.btnMultiply)
        buttonDiv = findViewById(R.id.btnDivide)
        buttonRes = findViewById(R.id.btnEquals)
        buttonClear = findViewById(R.id.btnClear)
        textFinalResult = findViewById(R.id.txtInput)
    }

    private fun addCallBacks() {
        buttonClear.setOnClickListener {
            clearInput()
        }
        buttonPlus.setOnClickListener {
            initOperations(Plus)
        }
        buttonMinus.setOnClickListener {
            initOperations(Minus)

        }
        buttonDiv.setOnClickListener {
            initOperations(Div)

        }
        buttonMul.setOnClickListener {
            initOperations(Mul)

        }
        buttonRes.setOnClickListener {
            result()
        }
    }

    private fun result() {
        val secondNumber = textFinalResult.text.toString().toDouble()
        textFinalResult.text = when (currentOperation) {
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
        firstNumber = textFinalResult.text.toString().toDouble()
        clearInput()
        currentOperation = op
    }
    private fun clearInput() {
        textFinalResult.hint = textFinalResult.text
        textFinalResult.text = ""
    }
    fun onClickNumbers(v: View) {
        val number = (v as Button).text.toString()
        val oldText = textFinalResult.text.toString()
        textFinalResult.text = oldText + number

    }

}