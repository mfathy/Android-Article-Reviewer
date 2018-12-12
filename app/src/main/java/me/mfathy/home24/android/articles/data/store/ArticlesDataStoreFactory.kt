package me.mfathy.home24.android.articles.data.store

import me.mfathy.home24.android.articles.data.store.cache.CacheArticlesDataStore
import me.mfathy.home24.android.articles.data.store.remote.RemoteArticlesDataStore
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * ArticlesDataStoreFactory is a factory class responsible for creating and providing the suitable data store
 * object.
 */
open class ArticlesDataStoreFactory @Inject constructor(
        private val cacheDataStore: CacheArticlesDataStore,
        private val remoteDataStore: RemoteArticlesDataStore) {

    open fun getDataStore(isArticlesCached: Boolean): ArticlesDataStore {
        return if (isArticlesCached) {
            cacheDataStore
        } else {
            remoteDataStore
        }
    }

    open fun getCacheDataStore(): ArticlesDataStore {
        return cacheDataStore
    }

    fun getRemoteDataStore(): ArticlesDataStore {
        return remoteDataStore
    }
}