package me.mfathy.home24.android.articles.data.mapper

import me.mfathy.home24.android.articles.data.store.cache.model.CachedArticle
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.factory.ArticlesDataFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for testing ArticleEntityCacheMapper.
 */
@RunWith(JUnit4::class)
class ArticleEntityCacheMapperTest{

    private val mapper = ArticleEntityCacheMapper()

    @Test
    fun mapToEntityMapsData() {
        val cachedArticle = ArticlesDataFactory.makeCachedArticle()
        val articleEntity = mapper.mapToEntity(cachedArticle)

        assertEqualData(cachedArticle, articleEntity)
    }

    @Test
    fun mapFromEntityMapsData() {
        val articleEntity = ArticlesDataFactory.makeArticleEntity()
        val cachedArticle = mapper.mapFromEntity(articleEntity)

        assertEqualData(cachedArticle, articleEntity)
    }

    private fun assertEqualData(model: CachedArticle,
                                entity: ArticleEntity) {
        kotlin.test.assertEquals(model.imageUrl, entity.imageUrl)
        kotlin.test.assertEquals(model.isLiked, entity.isLiked)
        kotlin.test.assertEquals(model.sku, entity.sku)
        kotlin.test.assertEquals(model.title, entity.title)
        kotlin.test.assertEquals(model.isReviewed, entity.isReviewed)
    }

}