package me.mfathy.home24.android.articles.injection

import android.app.Application
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import me.mfathy.home24.android.articles.data.store.ArticlesDataStore
import me.mfathy.home24.android.articles.data.store.cache.db.ArticlesDatabase

@Module
object TestCacheModule {

    @Provides
    @JvmStatic
    fun provideDatabase(application: Application): ArticlesDatabase {
        return ArticlesDatabase.getInstance(application)
    }

    @Provides
    @JvmStatic
    fun provideCacheStore(): ArticlesDataStore {
        return mock()
    }

}