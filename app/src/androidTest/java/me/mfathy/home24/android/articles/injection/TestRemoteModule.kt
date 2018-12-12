package me.mfathy.home24.android.articles.injection

import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import me.mfathy.home24.android.articles.data.store.ArticlesDataStore
import me.mfathy.home24.android.articles.data.store.remote.service.RemoteService

@Module
object TestRemoteModule {

    @Provides
    @JvmStatic
    fun provideRemoteService(): RemoteService {
        return mock()
    }

    @Provides
    @JvmStatic
    fun provideRemoteArticlesDataStore(): ArticlesDataStore {
        return mock()
    }

}