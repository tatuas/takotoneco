package com.tatuas.takotoneco.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ComputeDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoilOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GitHubOkHttpClient
