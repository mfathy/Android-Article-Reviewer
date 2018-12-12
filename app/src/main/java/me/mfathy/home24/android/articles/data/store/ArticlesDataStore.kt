package me.mfathy.home24.android.articles.data.store

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.mfathy.home24.android.articles.data.model.ArticleEntity

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * Data store contract which defines what data stores do for manipulating data.
 */
interface ArticlesDataStore {

    /**
     * Returns a Flowable which emits all articles for the data store specified.
     */
    fun getArticles(articleCount: Int): Flowable<List<ArticleEntity>>

    /**
     * Saves articles to the specified data store and return a completable observable to represent result.
     */
    fun saveArticles(articles: List<ArticleEntity>): Completable

    /**
     * Clears all articles from the specified data store and return a completable observable to represent result.
     */
    fun clearArticles(): Completable

    /**
     * Returns a Flowable which emits all reviewed articles for the data store specified.
     */
    fun getReviewedArticles(): Flowable<List<ArticleEntity>>

    /**
     * Like an article and return a completable observable which emit complete event or error.
     */
    fun setLikedArticle(entity: ArticleEntity): Completable

    /**
     * Dislike an article and return a completable observable which emit complete event or error.
     */
    fun setDislikedArticle(entity: ArticleEntity): Completable
}