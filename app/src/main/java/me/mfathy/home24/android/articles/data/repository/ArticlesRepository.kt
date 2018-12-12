package me.mfathy.home24.android.articles.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import me.mfathy.home24.android.articles.data.model.ArticleEntity

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * Data repository contract to define connection between domain layer and data layer.
 */
interface ArticlesRepository {

    /**
     * Returns an observable which emits a list of ArticleEntity.
     */
    fun getArticles(articleCount: Int): Observable<List<ArticleEntity>>

    /**
     * Like an article and return a completable observable which emit complete event or error.
     */
    fun likeArticle(entity: ArticleEntity): Completable

    /**
     * Dislike an article and return a completable observable which emit complete event or error.
     */
    fun dislikeArticle(entity: ArticleEntity): Completable

    /**
     * Returns an observable which emits a list of already reviewed articles.
     */
    fun getReviewedArticles(): Observable<List<ArticleEntity>>

    /**
     * Clears or deletes all articles from data repository.
     */
    fun clearArticles(): Completable

}