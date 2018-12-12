package me.mfathy.home24.android.articles.data.store

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import me.mfathy.home24.android.articles.data.store.cache.CacheArticlesDataStore
import me.mfathy.home24.android.articles.data.store.remote.RemoteArticlesDataStore
import org.junit.Test

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for ArticlesDataStoreFactory
 */
class ArticlesDataStoreFactoryTest{
    private val cacheStore = mock<CacheArticlesDataStore>()
    private val remoteStore = mock<RemoteArticlesDataStore>()
    private val factory = ArticlesDataStoreFactory(cacheStore, remoteStore)

    @Test
    fun getRemoteStoreRetrievesRemoteSource() {
        assert(factory.getRemoteDataStore() is RemoteArticlesDataStore)
    }

    @Test
    fun getCacheStoreRetrievesCacheSource() {
        assert(factory.getCacheDataStore() is CacheArticlesDataStore)
    }

    @Test
    fun getDataStoreReturnsRemoteSourceWhenCacheExpired() {
        assert(factory.getDataStore(false) is RemoteArticlesDataStore)
    }

}