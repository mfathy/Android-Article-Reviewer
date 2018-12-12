package me.mfathy.home24.android.articles.data.mapper

import me.mfathy.home24.android.articles.data.store.cache.model.CachedArticle
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * A data mapper to map and convert CachedArticle to ArticleEntity and vice-versa.
 */
class ArticleEntityCacheMapper @Inject constructor(): EntityMapper<ArticleEntity, CachedArticle> {
    override fun mapFromEntity(entity: ArticleEntity): CachedArticle {
        return CachedArticle(entity.title, entity.sku, entity.imageUrl, entity.isLiked, entity.isReviewed)
    }

    override fun mapToEntity(domain: CachedArticle): ArticleEntity {
        return ArticleEntity(domain.title, domain.sku, domain.imageUrl, domain.isLiked, domain.isReviewed)
    }
}