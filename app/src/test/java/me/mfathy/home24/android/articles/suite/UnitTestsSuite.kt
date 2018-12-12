package me.mfathy.home24.android.articles.suite

import me.mfathy.home24.android.articles.data.mapper.ArticleEntityCacheMapperTest
import me.mfathy.home24.android.articles.data.mapper.ArticleEntityNetworkMapperTest
import me.mfathy.home24.android.articles.data.repository.ArticlesDataRepositoryTest
import me.mfathy.home24.android.articles.data.store.ArticlesDataStoreFactoryTest
import me.mfathy.home24.android.articles.data.store.cache.CacheArticlesDataStoreTest
import me.mfathy.home24.android.articles.data.store.cache.dao.CachedArticlesDaoTest
import me.mfathy.home24.android.articles.data.store.remote.RemoteArticlesDataStoreTest
import me.mfathy.home24.android.articles.domain.articles.*
import me.mfathy.home24.android.articles.ui.review.ReviewViewModelTest
import me.mfathy.home24.android.articles.ui.select.SelectViewModelTest
import me.mfathy.home24.android.articles.ui.start.StartViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by Mohammed Fathy on 12/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests suite.
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(ArticleEntityCacheMapperTest::class,
        ArticleEntityNetworkMapperTest::class,
        ArticlesDataStoreFactoryTest::class,
        RemoteArticlesDataStoreTest::class,
        CacheArticlesDataStoreTest::class,
        ArticlesDataRepositoryTest::class,
        GetReviewedArticlesTest::class,
        CachedArticlesDaoTest::class,
        ReviewViewModelTest::class,
        SelectViewModelTest::class,
        DisLikeArticleTest::class,
        ClearArticlesTest::class,
        StartViewModelTest::class,
        LikeArticleTest::class,
        GetArticlesTest::class)
class UnitTestsSuite