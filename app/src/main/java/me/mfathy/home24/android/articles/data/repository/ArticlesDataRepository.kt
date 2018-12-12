package me.mfathy.home24.android.articles.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.data.store.ArticlesDataStoreFactory
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 11/12/2018.
 * dev.mfathy@gmail.com
 *
 * Data repository implementation > check ArticlesRepository for more details.
 */
class ArticlesDataRepository @Inject constructor(private val factory: ArticlesDataStoreFactory) : ArticlesRepository {
    override fun getArticles(articleCount: Int): Observable<List<ArticleEntity>> {
        return factory.getCacheDataStore().getArticles(articleCount)
                .toObservable()
                .flatMap { cachedArticles: List<ArticleEntity> ->
                    if (cachedArticles.isEmpty()) {
                        factory.getRemoteDataStore().getArticles(articleCount).toObservable().distinctUntilChanged()
                                .flatMap { factory.getCacheDataStore().saveArticles(it).andThen(Observable.just(it)) }
                    } else {
                        factory.getCacheDataStore().getArticles(articleCount).toObservable()
                    }
                }
    }

    override fun likeArticle(entity: ArticleEntity): Completable {
        return factory.getCacheDataStore().setLikedArticle(entity)
    }

    override fun dislikeArticle(entity: ArticleEntity): Completable {
        return factory.getCacheDataStore().setDislikedArticle(entity)
    }

    override fun getReviewedArticles(): Observable<List<ArticleEntity>> {
        return factory.getCacheDataStore().getReviewedArticles().toObservable()
    }

    override fun clearArticles(): Completable {
        return factory.getCacheDataStore().clearArticles()
    }

}