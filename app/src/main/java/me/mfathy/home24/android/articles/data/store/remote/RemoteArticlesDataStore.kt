package me.mfathy.home24.android.articles.data.store.remote

import io.reactivex.Completable
import io.reactivex.Flowable
import me.mfathy.home24.android.articles.config.AppConstants.APP_DOMAIN
import me.mfathy.home24.android.articles.config.AppConstants.APP_LOCAL
import me.mfathy.home24.android.articles.data.mapper.ArticleEntityNetworkMapper
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.data.store.ArticlesDataStore
import me.mfathy.home24.android.articles.data.store.remote.service.RemoteService
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * RemoteArticlesDataStore is a data store implementation to access the data from the backend service
 * through retrofit remote service.
 */
open class RemoteArticlesDataStore @Inject constructor(
        private val service: RemoteService,
        private val mapper: ArticleEntityNetworkMapper)
    : ArticlesDataStore {
    override fun getArticles(articleCount: Int): Flowable<List<ArticleEntity>> {
        return service.getArticles(APP_DOMAIN, APP_LOCAL, articleCount).map { articleModel ->
            articleModel.embedded.articles.map { mapper.mapToEntity(it) }
        }.toFlowable()
    }

    override fun saveArticles(articles: List<ArticleEntity>): Completable {
        throw UnsupportedOperationException("Save articles is not supported in remote backend")
    }

    override fun clearArticles(): Completable {
        throw UnsupportedOperationException("Clear articles is not supported in remote backend")
    }

    override fun getReviewedArticles(): Flowable<List<ArticleEntity>> {
        throw UnsupportedOperationException("Get liked articles is not supported in remote backend")
    }

    override fun setLikedArticle(entity: ArticleEntity): Completable {
        throw UnsupportedOperationException("Set articles as liked is not supported in remote backend")
    }

    override fun setDislikedArticle(entity: ArticleEntity): Completable {
        throw UnsupportedOperationException("Set articles as disliked is not supported in remote backend")
    }
}