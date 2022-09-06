package com.wit.farmersmarketredo.ui.farmers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FarmersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is About Fragment"
    }
    val text: LiveData<String> = _text
}