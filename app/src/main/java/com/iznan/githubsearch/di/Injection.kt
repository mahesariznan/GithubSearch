package com.iznan.githubsearch.di

import com.iznan.githubsearch.repo.RemoteRepository

class Injection {
    fun provideRepository(): RemoteRepository {
        return RemoteRepository.getInstance()
    }
}