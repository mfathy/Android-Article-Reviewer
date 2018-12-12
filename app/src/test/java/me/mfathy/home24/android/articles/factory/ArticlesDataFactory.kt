package me.mfathy.home24.android.articles.factory

import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.data.store.cache.model.CachedArticle
import me.mfathy.home24.android.articles.data.store.remote.model.ArticleItem
import me.mfathy.home24.android.articles.data.store.remote.model.ArticleModel
import me.mfathy.home24.android.articles.data.store.remote.model.Embedded
import me.mfathy.home24.android.articles.data.store.remote.model.MediaItem

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 */
object ArticlesDataFactory {

    fun makeCachedArticle(): CachedArticle {
        return CachedArticle(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                -1,
                false
        )

    }

    fun makeReviewedCacheArticle(): CachedArticle {
        return CachedArticle(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                -1,
                true
        )
    }

    fun makeArticleEntity(): ArticleEntity {
        return ArticleEntity(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                -1,
                false
        )
    }

    fun makeReviewedArticleEntity(): ArticleEntity {
        return ArticleEntity(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                1,
                true
        )
    }

    fun makeArticleItem(): ArticleItem {
        return ArticleItem(
                listOf(MediaItem(DataFactory.randomString())),
                DataFactory.randomString(),
                DataFactory.randomString()
        )
    }

    fun makeArticleModelResponse(): ArticleModel {
        return ArticleModel(Embedded((listOf(makeArticleItem()))))
    }

    fun makeArticleEntityList(count: Int): List<ArticleEntity> {
        val articles = mutableListOf<ArticleEntity>()
        repeat(count) {
            articles.add(makeArticleEntity())
        }
        return articles
    }
}