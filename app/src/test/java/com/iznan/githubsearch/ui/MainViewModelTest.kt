package com.iznan.githubsearch.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.iznan.githubsearch.model.ItemsItem
import com.iznan.githubsearch.repo.RemoteRepository
import com.iznan.githubsearch.util.Constant.Companion.USER_NOT_FOUND
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private val listOfUser = mutableListOf(
        ItemsItem(
            login = "mahesar",
            id = 48434312,
            nodeId = "MDEyOk9yZ2FuaXphdGlvbjQ4NDM0MzEy",
            avatarUrl = "https://avatars3.githubusercontent.com/u/48434312?v=4",
            gravatarId = "",
            url = "https://api.github.com/users/mahesar",
            htmlUrl = "https://github.com/mahesar",
            followersUrl = "https://api.github.com/users/mahesar/followers",
            followingUrl = "https://api.github.com/users/mahesar/following{/other_user}",
            gistsUrl = "https://api.github.com/users/mahesar/gists{/gist_id}",
            starredUrl = "https://api.github.com/users/mahesar/starred{/owner}{/repo}",
            subscriptionsUrl = "https://api.github.com/users/mahesar/subscriptions",
            organizationsUrl = "https://api.github.com/users/mahesar/orgs",
            reposUrl = "https://api.github.com/users/mahesar/repos",
            eventsUrl = "https://api.github.com/users/mahesar/events{/privacy}",
            receivedEventsUrl = "https://api.github.com/users/mahesar/received_events",
            type = "Organization",
            siteAdmin = false,
            score = 1.0
        ), ItemsItem(
            login = "mahesaragil23",
            id = 56819954,
            nodeId = "MDQ6VXNlcjU2ODE5OTU0",
            avatarUrl = "https://avatars3.githubusercontent.com/u/56819954?v=4",
            gravatarId = "",
            url = "https://api.github.com/users/mahesaragil23",
            htmlUrl = "https://github.com/mahesaragil23",
            followersUrl = "https://api.github.com/users/mahesaragil23/followers",
            followingUrl = "https://api.github.com/users/mahesaragil23/following{/other_user}",
            gistsUrl = "https://api.github.com/users/mahesaragil23/gists{/gist_id}",
            starredUrl = "https://api.github.com/users/mahesaragil23/starred{/owner}{/repo}",
            subscriptionsUrl = "https://api.github.com/users/mahesaragil23/subscriptions",
            organizationsUrl = "https://api.github.com/users/mahesaragil23/orgs",
            reposUrl = "https://api.github.com/users/mahesaragil23/repos",
            eventsUrl = "https://api.github.com/users/mahesaragil23/events{/privacy}",
            receivedEventsUrl = "https://api.github.com/users/mahesaragil23/received_events",
            type = "User",
            siteAdmin = false,
            score = 1.0
        ), ItemsItem(
            login = "mahesariznan",
            id = 39899231,
            nodeId = "MDQ6VXNlcjM5ODk5MjMx",
            avatarUrl = "https://avatars2.githubusercontent.com/u/39899231?v=4",
            gravatarId = "",
            url = "https://api.github.com/users/mahesariznan",
            htmlUrl = "https://github.com/mahesariznan",
            followersUrl = "https://api.github.com/users/mahesariznan/followers",
            followingUrl = "https://api.github.com/users/mahesariznan/following{/other_user}",
            gistsUrl = "https://api.github.com/users/mahesariznan/gists{/gist_id}",
            starredUrl = "https://api.github.com/users/mahesariznan/starred{/owner}{/repo}",
            subscriptionsUrl = "https://api.github.com/users/mahesariznan/subscriptions",
            organizationsUrl = "https://api.github.com/users/mahesariznan/orgs",
            reposUrl = "https://api.github.com/users/mahesariznan/repos",
            eventsUrl = "https://api.github.com/users/mahesariznan/events{/privacy}",
            receivedEventsUrl = "https://api.github.com/users/mahesariznan/received_events",
            type = "User",
            siteAdmin = false,
            score = 1.0
        )
    )

    @Test
    fun getName(){
        val int = (0..2).random()
        Assert.assertNotNull(viewModel.userList.value?.get(int)?.login)
        Assert.assertEquals(listOfUser[int].login, viewModel.userList.value?.get(int)?.login)
    }

    @Test
    fun getAvatar(){
        val int = (0..2).random()
        Assert.assertNotNull(viewModel.userList.value?.get(int)?.avatarUrl)
        Assert.assertEquals(listOfUser[int].avatarUrl, viewModel.userList.value?.get(int)?.avatarUrl)
    }

    @Test
    fun getId(){
        val int = (0..2).random()
        Assert.assertNotNull(viewModel.userList.value?.get(int)?.id)
        Assert.assertEquals(listOfUser[int].id, viewModel.userList.value?.get(int)?.id)
    }

    @Test
    fun getUrl(){
        val int = (0..2).random()
        Assert.assertNotNull(viewModel.userList.value?.get(int)?.url)
        Assert.assertEquals(listOfUser[int].url, viewModel.userList.value?.get(int)?.url)
    }

    @Test
    fun getError(){
        Assert.assertNotNull(viewModel.errorMessage)
        Assert.assertEquals(USER_NOT_FOUND, viewModel.errorMessage.value)
    }

    @Before
    fun setUp() {
        viewModel = MainViewModel(RemoteRepository())
        createDummyData()
    }

    fun createDummyData() {
        viewModel.userList.value = listOfUser
        viewModel.errorMessage.value = USER_NOT_FOUND
    }
}