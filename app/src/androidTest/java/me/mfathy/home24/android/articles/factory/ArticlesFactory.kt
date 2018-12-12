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
 *
 * Data factory class used to generate dummy data for test.
 */
object ArticlesFactory {

    fun makeCachedArticle(): CachedArticle {
        return CachedArticle(
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                -1,
                false
        )

    }

    fun makeReviewedCacheArticle(): CachedArticle {
        return CachedArticle(
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                -1,
                true
        )
    }

    fun makeArticleEntity(): ArticleEntity {
        return ArticleEntity(
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                "https://cdn1.home24.net/resized/media/catalog/product/m/a/matratzenbezug-smood-webstoff-180-x-200cm-3477221.png?output-format=jpg&interpolation=progressive-bicubic",
                -1,
                false
        )
    }

    fun makeReviewedArticleEntity(): ArticleEntity {
        return ArticleEntity(
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                1,
                true
        )
    }

    fun makeArticleItem(): ArticleItem {
        return ArticleItem(
                listOf(MediaItem(RandomFactory.randomString())),
                RandomFactory.randomString(),
                RandomFactory.randomString()
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