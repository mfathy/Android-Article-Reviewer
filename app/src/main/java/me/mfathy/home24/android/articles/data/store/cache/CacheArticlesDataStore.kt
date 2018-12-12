package me.mfathy.home24.android.articles.data.store.cache

import io.reactivex.Completable
import io.reactivex.Flowable
import me.mfathy.home24.android.articles.data.mapper.ArticleEntityCacheMapper
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.data.store.ArticlesDataStore
import me.mfathy.home24.android.articles.data.store.cache.db.ArticlesDatabase
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * CacheArticlesDataStore is the data store implementation for cache layer. it provides access to data
 * from the local database.
 */
open class CacheArticlesDataStore @Inject constructor(
        private val articleDatabase: ArticlesDatabase,
        private val mapper: ArticleEntityCacheMapper) : ArticlesDataStore {

    override fun getArticles(articleCount: Int): Flowable<List<ArticleEntity>> {
        return articleDatabase.cachedArticlesDao().getArticles()
                .map { articles ->
                    articles.map { mapper.mapToEntity(it) }
                }
    }

    override fun saveArticles(articles: List<ArticleEntity>): Completable {
        return Completable.defer {
            articleDatabase.cachedArticlesDao().insertArticles(
                    articles.map { mapper.mapFromEntity(it) })
            Completable.complete()
        }
    }

    override fun clearArticles(): Completable {
        return Completable.defer{
            articleDatabase.cachedArticlesDao().deleteArticles()
            Completable.complete()
        }
    }

    override fun getReviewedArticles(): Flowable<List<ArticleEntity>> {
        return articleDatabase.cachedArticlesDao().getReviewedArticles()
                .map { articles ->
                    articles.map { mapper.mapToEntity(it) }
                }
    }

    override fun setLikedArticle(entity: ArticleEntity): Completable {
        return Completable.defer{
            articleDatabase.cachedArticlesDao().setLikedArticle(mapper.mapFromEntity(entity))
            Completable.complete()
        }
    }

    override fun setDislikedArticle(entity: ArticleEntity): Completable {
        return Completable.defer{
            articleDatabase.cachedArticlesDao().setDisLikedArticle(mapper.mapFromEntity(entity))
            Completable.complete()
        }
    }
}