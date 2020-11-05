package com.example.furlencotask.ui.activities

/**
 * Created by Sourik on 5/11/20.
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.furlencotask.R
import com.example.furlencotask.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("ACtiviy",mainViewModel.toString())
        mainViewModel.resultLD.observe(this,{
            text.text = it[0].description
        })
        mainViewModel.getSearchResults()
    }
}