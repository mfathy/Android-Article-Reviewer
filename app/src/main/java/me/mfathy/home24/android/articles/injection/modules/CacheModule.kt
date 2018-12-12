package me.mfathy.home24.android.articles.injection.modules

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import me.mfathy.home24.android.articles.data.store.ArticlesDataStore
import me.mfathy.home24.android.articles.data.store.cache.CacheArticlesDataStore
import me.mfathy.home24.android.articles.data.store.cache.db.ArticlesDatabase

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * Dagger module to provide cache dependencies.
 */
@Module
abstract class CacheModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDataBase(application: Application): ArticlesDatabase {
            return ArticlesDatabase.getInstance(application)
        }
    }


    @Binds
    abstract fun bindCacheStore(cache: CacheArticlesDataStore): ArticlesDataStore
}