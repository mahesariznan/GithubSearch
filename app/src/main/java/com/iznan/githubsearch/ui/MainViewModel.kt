package com.iznan.githubsearch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iznan.githubsearch.model.ItemsItem
import com.iznan.githubsearch.repo.RemoteRepository
import com.iznan.githubsearch.util.Constant.Companion.USER_NOT_FOUND
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val remoteRepository: RemoteRepository) : ViewModel() {

    val compositeDisposable = CompositeDisposable()
    val userList = MutableLiveData<List<ItemsItem>>()
    val errorMessage = MutableLiveData<String>()
    val moreUserList = MutableLiveData<List<ItemsItem>>()
    var page = 0
    var username = ""

    @Synchronized
    fun setUserList(userName: String) {
        page = 1
        username = userName
        remoteRepository.api().getUsers(userName, page, 100)
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
                } else {
                    errorMessage.value = USER_NOT_FOUND
                }
            }, { throwable ->
                errorMessage.value = throwable.toString()
            }).also {
                compositeDisposable.add(it)
            }
    }

    fun loadMore() {
        page += 1
        remoteRepository.api().getUsers(username, page, 100)
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
                    moreUserList.value = listOfUser
                }
            }, { throwable ->

            }).also {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}