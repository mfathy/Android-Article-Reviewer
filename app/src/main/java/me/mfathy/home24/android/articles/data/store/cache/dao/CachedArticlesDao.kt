package me.mfathy.home24.android.articles.data.store.cache.dao

import androidx.room.*
import io.reactivex.Flowable
import me.mfathy.home24.android.articles.config.AppConstants.DELETE_ARTICLES
import me.mfathy.home24.android.articles.config.AppConstants.QUERY_ARTICLES
import me.mfathy.home24.android.articles.config.AppConstants.QUERY_REVIEWED_ARTICLES
import me.mfathy.home24.android.articles.data.store.cache.model.CachedArticle


/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * CachedArticlesDao is the data access layer for Articles database.
 * This class provides helper methods to access the database easily.
 */
@Dao
abstract class CachedArticlesDao {

    @Query(QUERY_ARTICLES)
    @JvmSuppressWildcards
    abstract fun getArticles(): Flowable<List<CachedArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertArticles(articles: List<CachedArticle>)

    @Query(DELETE_ARTICLES)
    abstract fun deleteArticles()

    @Query(QUERY_REVIEWED_ARTICLES)
    abstract fun getReviewedArticles(): Flowable<List<CachedArticle>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setLikedArticle(article: CachedArticle)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setDisLikedArticle(article: CachedArticle)

}