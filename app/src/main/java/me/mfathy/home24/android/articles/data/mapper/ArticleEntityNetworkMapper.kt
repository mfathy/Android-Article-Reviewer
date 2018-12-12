package me.mfathy.home24.android.articles.data.mapper

import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.data.store.remote.model.ArticleItem
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * A data mapper to map and convert NetworkModels to ArticleEntity.
 */
open class ArticleEntityNetworkMapper @Inject constructor(): EntityMapper<ArticleEntity, ArticleItem> {
    override fun mapFromEntity(entity: ArticleEntity): ArticleItem {
        throw UnsupportedOperationException("ArticleItem is readonly: parsed from remote store only.")
    }

    override fun mapToEntity(domain: ArticleItem): ArticleEntity {
        return ArticleEntity(domain.title, domain.sku, domain.media.first().uri, -1, false)
    }
}