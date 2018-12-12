package me.mfathy.home24.android.articles.data.store.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import me.mfathy.home24.android.articles.data.store.cache.db.ArticlesDatabase
import me.mfathy.home24.android.articles.factory.ArticlesDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for CachedArticlesDaoTest.
 */
@RunWith(RobolectricTestRunner::class)
class CachedArticlesDaoTest {
    @Rule
    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ArticlesDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getArticlesReturnsData() {
        val article = ArticlesDataFactory.makeCachedArticle()
        database.cachedArticlesDao().insertArticles(listOf(article))

        val testObserver = database.cachedArticlesDao().getArticles().test()
        testObserver.assertValue(listOf(article))
        testObserver.assertValueCount(1)
    }

    @Test
    fun deleteArticlesClearsData() {
        val article = ArticlesDataFactory.makeCachedArticle()
        database.cachedArticlesDao().insertArticles(listOf(article))
        database.cachedArticlesDao().deleteArticles()

        val testObserver = database.cachedArticlesDao().getArticles().test()
        testObserver.assertValue(emptyList())
    }

    @Test
    fun getReviewedArticlesReturnsData() {
        val article = ArticlesDataFactory.makeCachedArticle()
        val reviewedArticle = ArticlesDataFactory.makeReviewedCacheArticle()
        database.cachedArticlesDao().insertArticles(listOf(article, reviewedArticle))

        val testObserver = database.cachedArticlesDao().getReviewedArticles().test()
        testObserver.assertValue(listOf(reviewedArticle))
        testObserver.assertValueCount(1)
    }

    @Test
    fun setArticleAsLikedSavesData() {
        val article = ArticlesDataFactory.makeCachedArticle()
        database.cachedArticlesDao().insertArticles(listOf(article))
        article.isLiked = 1 // 1 Acts as liked.
        database.cachedArticlesDao().setLikedArticle(article)

        val testObserver = database.cachedArticlesDao().getArticles().test()
        testObserver.assertValue(listOf(article))
    }

    @Test
    fun setArticleAsDislikedSavesData() {
        val article = ArticlesDataFactory.makeCachedArticle()
        database.cachedArticlesDao().insertArticles(listOf(article))
        article.isLiked = 0 // 1 Acts as disliked.
        database.cachedArticlesDao().setDisLikedArticle(article)

        val testObserver = database.cachedArticlesDao().getArticles().test()
        testObserver.assertValue(listOf(article))
    }
}