package com.example.furlencotask.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Sourik on 7/11/20.
 */

class MainViewModel @ViewModelInject constructor() : ViewModel() {
    private val onFavouriteChangedLD = MutableLiveData<Pair<String,Boolean>>()

    val onChangedFav: LiveData<Pair<String,Boolean>>
        get() = onFavouriteChangedLD

    fun onChangeFav(pair:Pair<String,Boolean>) {
        onFavouriteChangedLD.postValue(pair)
    }
}