package me.mfathy.home24.android.articles.injection.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import me.mfathy.home24.android.articles.BuildConfig
import me.mfathy.home24.android.articles.data.store.ArticlesDataStore
import me.mfathy.home24.android.articles.data.store.remote.RemoteArticlesDataStore
import me.mfathy.home24.android.articles.data.store.remote.service.RemoteService
import me.mfathy.home24.android.articles.data.store.remote.service.RemoteServiceFactory

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * Dagger module to provide remote dependencies.
 */
@Module
abstract class RemoteModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideRemoteService(): RemoteService {
            return RemoteServiceFactory.makeRemoteService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindRemoteStore(cache: RemoteArticlesDataStore): ArticlesDataStore

}