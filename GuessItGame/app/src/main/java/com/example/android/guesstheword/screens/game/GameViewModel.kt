package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel() {
    companion object{
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIMER = 10000L
    }
    private val timer:CountDownTimer

    private var _timerString = MutableLiveData<String>()
    val timerString : LiveData<String>
    get() = _timerString
    // The current word
    private var _word = MutableLiveData<String>()
    val word : LiveData<String>
    get() = _word
    // The current score
    private var _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = _score
    private var _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish : LiveData<Boolean>
        get() = _eventGameFinish
    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        _eventGameFinish.value=false
        _score.value=0
        _word.value=""
        resetList()
        nextWord()
        timer = object : CountDownTimer(COUNTDOWN_TIMER, ONE_SECOND){
            override fun onTick(p0: Long) {
                _timerString.value = DateUtils.formatElapsedTime(p0/1000)
            }

            override fun onFinish() {
                _eventGameFinish.value=true
            }
        }.start()

    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel","GameViewModel destroyed")
    }
    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "بطرس",
            "ادم",
            "قايين",
            "نوح",
            "ابراهيم",
            "اسحق",
            "اسماعيل",
            "يعقوب",
            "يوسف",
            "موسي",
            "هارون",
            "شمشون",
            "دبورة",
            "جدعون",
            "يشوع",
            "صموئيل",
            "شاول",
            "داود",
            "سليمان",
            "راعوث",
            "نحميا"
        )
        wordList.shuffle()
    }
    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)

    }
    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value=_score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value=_score.value?.plus(1)
        nextWord()
    }
    fun gameHasFinish(){
        _eventGameFinish.value=false
    }

}