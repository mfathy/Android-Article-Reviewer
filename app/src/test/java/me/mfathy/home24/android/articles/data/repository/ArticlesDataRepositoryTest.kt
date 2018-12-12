package me.mfathy.home24.android.articles.data.repository

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.data.store.ArticlesDataStore
import me.mfathy.home24.android.articles.data.store.ArticlesDataStoreFactory
import me.mfathy.home24.android.articles.factory.ArticlesDataFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for ArticlesDataRepository.
 */
@RunWith(MockitoJUnitRunner::class)
class ArticlesDataRepositoryTest {

    private val store = mock<ArticlesDataStore>()
    private val factory = mock<ArticlesDataStoreFactory>()
    private val repository = ArticlesDataRepository(factory)

    @Before
    fun setup() {
        stubFactoryGetCacheDataStore()
    }

    @Test
    fun getArticlesCompletes() {
        stubGetArticles(Flowable.just(listOf(ArticlesDataFactory.makeArticleEntity())))

        val testObserver = repository.getArticles(1).test()
        testObserver.assertComplete()
    }

    @Test
    fun getArticlesReturnsData() {
        val articleEntity = ArticlesDataFactory.makeArticleEntity()
        stubGetArticles(Flowable.just(listOf(articleEntity)))

        val testObserver = repository.getArticles(1).test()
        testObserver.assertValue(listOf(articleEntity))
    }

    @Test
    fun clearArticlesCompletes() {
        stubClearArticles(Completable.complete())

        val testObserver = repository.clearArticles().test()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    @Test
    fun likeArticleCompletes() {
        stubLikeArticles(Completable.complete())
        val articleEntity = ArticlesDataFactory.makeArticleEntity()
        articleEntity.isLiked = 1

        val testObserver = repository.likeArticle(articleEntity).test()
        testObserver.assertComplete()
    }

    @Test
    fun dislikeArticleCompletes() {
        stubDislikeArticle(Completable.complete())
        val articleEntity = ArticlesDataFactory.makeArticleEntity()
        articleEntity.isLiked = 0

        val testObserver = repository.dislikeArticle(articleEntity).test()
        testObserver.assertComplete()
    }

    @Test
    fun getReviewedArticlesCompletes() {
        val articleEntity = ArticlesDataFactory.makeArticleEntity()
        articleEntity.isReviewed = true
        stubGetReviewedArticles(Flowable.just(listOf(articleEntity)))

        val testObserver = repository.getReviewedArticles().test()
        testObserver.assertComplete()
    }

    @Test
    fun getReviewedArticlesReturnData() {
        val articleEntity = ArticlesDataFactory.makeArticleEntity()
        articleEntity.isReviewed = true
        stubGetReviewedArticles(Flowable.just(listOf(articleEntity)))

        val testObserver = repository.getReviewedArticles().test()
        testObserver.assertNoErrors()
        testObserver.assertValue(listOf(articleEntity))
    }


    private fun stubLikeArticles(completable: Completable) {
        whenever(factory.getCacheDataStore().setLikedArticle(any()))
                .thenReturn(completable)
    }

    private fun stubDislikeArticle(completable: Completable) {
        whenever(factory.getCacheDataStore().setDislikedArticle(any()))
                .thenReturn(completable)
    }


    private fun stubGetArticles(observable: Flowable<List<ArticleEntity>>) {
        whenever(store.getArticles(anyInt()))
                .thenReturn(observable)
    }

    private fun stubGetReviewedArticles(observable: Flowable<List<ArticleEntity>>) {
        whenever(store.getReviewedArticles())
                .thenReturn(observable)
    }

    private fun stubFactoryGetDataStore() {
        whenever(factory.getDataStore(anyBoolean()))
                .thenReturn(store)
    }

    private fun stubFactoryGetCacheDataStore() {
        whenever(factory.getCacheDataStore())
                .thenReturn(store)
    }

    private fun stubClearArticles(completable: Completable) {
        whenever(factory.getCacheDataStore().clearArticles())
                .thenReturn(completable)
    }


    private fun stubSaveArticles(completable: Completable) {
        whenever(store.saveArticles(any()))
                .thenReturn(completable)
    }
}