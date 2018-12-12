package me.mfathy.home24.android.articles.data.mapper

import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.data.store.remote.model.ArticleItem
import me.mfathy.home24.android.articles.factory.ArticlesDataFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for testing ArticleEntityNetworkMapperTest.
 */
@RunWith(JUnit4::class)
class ArticleEntityNetworkMapperTest{

    private val mapper = ArticleEntityNetworkMapper()

    @Test
    fun mapToEntityMapsData() {
        val articleItem = ArticlesDataFactory.makeArticleItem()
        val articleEntity = mapper.mapToEntity(articleItem)

        assertEqualData(articleItem, articleEntity)
    }

    private fun assertEqualData(model: ArticleItem,
                                entity: ArticleEntity) {
        kotlin.test.assertEquals(model.media.first().uri, entity.imageUrl)
        kotlin.test.assertEquals(model.sku, entity.sku)
        kotlin.test.assertEquals(model.title, entity.title)
    }
}