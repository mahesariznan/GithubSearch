package com.iznan.githubsearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iznan.githubsearch.R
import com.iznan.githubsearch.model.ItemsItem
import com.iznan.githubsearch.viewModel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = obtainViewModel()

        mainViewModel.setUserList("iznan")
        observeViewModel()
    }

    private fun observeViewModel(){
        mainViewModel.userList.observe(this, Observer<List<ItemsItem>> {
            textView.setText(it[0].login)
        })
    }

    private fun obtainViewModel(): MainViewModel {
        val factory = ViewModelFactory.getInstance()
        return ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }
}
