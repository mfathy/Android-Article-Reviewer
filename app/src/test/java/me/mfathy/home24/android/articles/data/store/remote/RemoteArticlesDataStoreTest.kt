package me.mfathy.home24.android.articles.data.store.remote

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import me.mfathy.home24.android.articles.config.AppConstants.APP_DOMAIN
import me.mfathy.home24.android.articles.config.AppConstants.APP_LOCAL
import me.mfathy.home24.android.articles.data.mapper.ArticleEntityNetworkMapper
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.data.store.remote.RemoteArticlesDataStore
import me.mfathy.home24.android.articles.data.store.remote.model.ArticleItem
import me.mfathy.home24.android.articles.data.store.remote.model.ArticleModel
import me.mfathy.home24.android.articles.data.store.remote.service.RemoteService
import me.mfathy.home24.android.articles.factory.ArticlesDataFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for RemoteArticlesDataStore
 */
@RunWith(JUnit4::class)
class RemoteArticlesDataStoreTest{
    private val mapper = mock<ArticleEntityNetworkMapper>()
    private val service = mock<RemoteService>()
    private val remote = RemoteArticlesDataStore(service, mapper)

    @Test
    fun getArticlesCompletes() {
        stubRemoteServiceGetArticles(Single.just(
                ArticlesDataFactory.makeArticleModelResponse()))
        stubArticlesResponseModelMapperMapFromModel(any(),
                ArticlesDataFactory.makeArticleEntity())

        val testObserver = remote.getArticles(1).test()
        testObserver.assertComplete()
    }

    @Test
    fun getArticlesCallsServer() {
        stubRemoteServiceGetArticles(Single.just(
                ArticlesDataFactory.makeArticleModelResponse()))
        stubArticlesResponseModelMapperMapFromModel(any(),
                ArticlesDataFactory.makeArticleEntity())

        remote.getArticles(1).test()
        verify(service).getArticles(anyInt(), anyString(), anyInt())
    }

    @Test
    fun getArticlesReturnsData() {
        val response = ArticlesDataFactory.makeArticleModelResponse()
        stubRemoteServiceGetArticles(Single.just(response))
        val entities = mutableListOf<ArticleEntity>()
        response.embedded.articles.forEach {
            val entity = ArticlesDataFactory.makeArticleEntity()
            entities.add(entity)
            stubArticlesResponseModelMapperMapFromModel(it, entity)
        }
        val testObserver = remote.getArticles(1).test()
        testObserver.assertValue(entities)
    }

    @Test
    fun getArticlesCallsServerWithCorrectParameters() {
        stubRemoteServiceGetArticles(Single.just(
                ArticlesDataFactory.makeArticleModelResponse()))
        stubArticlesResponseModelMapperMapFromModel(any(),
                ArticlesDataFactory.makeArticleEntity())

        remote.getArticles(1).test()
        verify(service).getArticles(APP_DOMAIN, APP_LOCAL, 1)
    }

    private fun stubArticlesResponseModelMapperMapFromModel(model: ArticleItem, entity: ArticleEntity) {
        whenever(mapper.mapToEntity(model))
                .thenReturn(entity)
    }

    private fun stubRemoteServiceGetArticles(observable: Single<ArticleModel>) {
        whenever(service.getArticles(anyInt(), anyString(), anyInt()))
                .thenReturn(observable)
    }
}