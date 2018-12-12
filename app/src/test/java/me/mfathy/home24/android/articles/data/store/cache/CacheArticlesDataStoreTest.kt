package me.mfathy.home24.android.articles.data.store.cache

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import me.mfathy.home24.android.articles.data.store.cache.db.ArticlesDatabase
import me.mfathy.home24.android.articles.data.mapper.ArticleEntityCacheMapper
import me.mfathy.home24.android.articles.factory.ArticlesDataFactory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for CacheArticlesDataStore
 */
@RunWith(RobolectricTestRunner::class)
class CacheArticlesDataStoreTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ArticlesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    private val entityMapper = ArticleEntityCacheMapper()
    private val cache = CacheArticlesDataStore(database, entityMapper)

    @Test
    fun clearTablesCompletes() {
        val testObserver = cache.clearArticles().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveArticlesCompletes() {
        val articles = listOf(ArticlesDataFactory.makeArticleEntity())

        val testObserver = cache.saveArticles(articles).test()
        testObserver.assertComplete()
    }

    @Test
    fun getArticlesReturnsData() {
        val articles = listOf(ArticlesDataFactory.makeArticleEntity())
        cache.saveArticles(articles).test()

        val testObserver = cache.getArticles(10).test()
        testObserver.assertValue(articles)
    }

    @Test
    fun getReviewedArticlesReturnsData() {
        val reviewedArticle = ArticlesDataFactory.makeReviewedArticleEntity()
        val articleEntity = ArticlesDataFactory.makeArticleEntity()
        val articles = listOf(articleEntity, reviewedArticle)
        cache.saveArticles(articles).test()

        val testObserver = cache.getReviewedArticles().test()
        testObserver.assertValue(listOf(reviewedArticle))
    }

    @Test
    fun setArticleAsLikedCompletes() {
        val articleEntity = ArticlesDataFactory.makeArticleEntity()
        articleEntity.isLiked = 1
        val articles = listOf(articleEntity)
        cache.saveArticles(articles).test()

        val testObserver = cache.setLikedArticle(articleEntity).test()
        testObserver.assertComplete()

        val testObserverValue = cache.getArticles(10).test()
        testObserverValue.assertValue(listOf(articleEntity))
    }

    @Test
    fun setArticleAsDislikedCompletes() {
        val articleEntity = ArticlesDataFactory.makeArticleEntity()
        articleEntity.isLiked = 0
        val articles = listOf(articleEntity)
        cache.saveArticles(articles).test()

        val testObserver = cache.setDislikedArticle(articleEntity).test()
        testObserver.assertComplete()

        val testObserverValue = cache.getArticles(10).test()
        testObserverValue.assertValue(listOf(articleEntity))
    }

}