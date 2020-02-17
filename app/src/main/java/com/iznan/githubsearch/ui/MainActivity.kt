package com.iznan.githubsearch.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iznan.githubsearch.R
import com.iznan.githubsearch.model.ItemsItem
import com.iznan.githubsearch.util.Constant.Companion.ERROR_FORBIDEN_403
import com.iznan.githubsearch.util.Constant.Companion.ERROR_NO_INTERNET_CONNECTION
import com.iznan.githubsearch.util.Constant.Companion.NAME_CANNOT_EMPTY
import com.iznan.githubsearch.util.Constant.Companion.PLEASE_TURN_ON_YOUR_INTERNET_CONNECTION
import com.iznan.githubsearch.util.Constant.Companion.PLEASE_WAIT_10_SECOND_AND_TRY_AGAIN
import com.iznan.githubsearch.util.Constant.Companion.USER_NOT_FOUND
import com.iznan.githubsearch.util.SearchTextWatcher
import com.iznan.githubsearch.viewModel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private val adapter = AdapterRecyclerView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = obtainViewModel()
        setupRecyclerView()
        editText_name.addTextChangedListener(nameWatcher)

        observeViewModel()
        searchButton.setOnClickListener {
            val name = editText_name.text.toString()
            when (!TextUtils.isEmpty(name)) {
                true -> mainViewModel.setUserList(name)
                false -> {
                    textView_error.setText(NAME_CANNOT_EMPTY)
                    textView_error.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun observeViewModel(){
        mainViewModel.userList.observe(this, Observer<List<ItemsItem>> {
            adapter.swapData(it)
            rc.visibility = View.VISIBLE
            textView_error.visibility = View.GONE
            progressBar.visibility = View.GONE
            editText_name.isEnabled = true
            searchButton.isEnabled = true
        })

        mainViewModel.errorMessage.observe(this, Observer<String> {
            if (it.equals(USER_NOT_FOUND, true)) {
                textView_error.setText(USER_NOT_FOUND)
            } else if (it.equals(ERROR_FORBIDEN_403, true)) {
                textView_error.setText(PLEASE_WAIT_10_SECOND_AND_TRY_AGAIN)
            } else if (it.equals(ERROR_NO_INTERNET_CONNECTION, true)) {
                textView_error.setText(PLEASE_TURN_ON_YOUR_INTERNET_CONNECTION)
            } else {
                textView_error.setText(it)
            }
            searchButton.isEnabled = true
            progressBar.visibility = View.GONE
            rc.visibility = View.GONE
            textView_error.visibility = View.VISIBLE
            editText_name.isEnabled = true
        })
    }

    private val nameWatcher = object : SearchTextWatcher() {
        override fun typingStateStopped(s: Editable?) {
            if (!s.isNullOrBlank()) {
                editText_name.isEnabled = false
                searchButton.isEnabled = false
                mainViewModel.setUserList(s.toString())
                progressBar.visibility = View.VISIBLE
            } else {
                editText_name.isEnabled = true
                searchButton.isEnabled = true
                progressBar.visibility = View.GONE
            }
        }
    }

    fun setupRecyclerView() {
        rc.layoutManager = LinearLayoutManager(this)
        rc.adapter = adapter
    }

    private fun obtainViewModel(): MainViewModel {
        val factory = ViewModelFactory.getInstance()
        return ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    override fun onDestroy() {
        editText_name.removeTextChangedListener(nameWatcher)
        super.onDestroy()
    }
}
