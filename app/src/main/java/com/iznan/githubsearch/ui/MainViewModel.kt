package com.iznan.githubsearch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iznan.githubsearch.model.ItemsItem
import com.iznan.githubsearch.repo.RemoteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val remoteRepository: RemoteRepository) : ViewModel() {

    val compositeDisposable = CompositeDisposable()
    val userList = MutableLiveData<List<ItemsItem>>()

    fun setUserList(userName: String) {
        remoteRepository.api().getUsers(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response != null && response.totalCount != 0) {
                    val listOfUser = mutableListOf<ItemsItem>()
                    response.items?.let {
                        for (item in it) {
                            listOfUser.add(item!!)
                        }
                    }
                    userList.value = listOfUser
                }
            }, { throwable ->
                throwable.printStackTrace()
            }).also {
                compositeDisposable.add(it)
            }
    }

}