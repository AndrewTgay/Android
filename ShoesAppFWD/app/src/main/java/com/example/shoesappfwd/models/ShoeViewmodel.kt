package com.example.shoesappfwd.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class ShoeViewmodel:ViewModel() {
    private val shoeMutableLiveData = MutableLiveData<List<Shoe>>()
    val shoeList  : LiveData<List<Shoe>>
        get() = shoeMutableLiveData

    fun addNewShoe(s:Shoe) {
        val currentShoeList = shoeMutableLiveData.value
        val newShoeList = mutableListOf<Shoe>()
        newShoeList.addAll(currentShoeList ?: emptyList())
        newShoeList.add(s)
        shoeMutableLiveData.value = newShoeList
    }

}