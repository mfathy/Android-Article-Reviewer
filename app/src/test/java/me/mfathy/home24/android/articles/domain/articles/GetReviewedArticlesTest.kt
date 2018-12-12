package me.mfathy.home24.android.articles.domain.articles

import me.mfathy.home24.android.articles.domain.executor.PostExecutionThread
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.data.repository.ArticlesRepository
import me.mfathy.home24.android.articles.domain.executor.SubscriberThread
import me.mfathy.home24.android.articles.factory.ArticlesDataFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for GetReviewedArticles
 */
@RunWith(MockitoJUnitRunner::class)
class GetReviewedArticlesTest {
    private lateinit var getReviewedArticles: GetReviewedArticles
    @Mock
    lateinit var dataRepository: ArticlesRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Mock
    lateinit var subscriberThread: SubscriberThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getReviewedArticles = GetReviewedArticles(dataRepository, subscriberThread, postExecutionThread)
    }

    @Test
    fun getReviewedArticlesCompletes() {
        stubDataRepositoryGetReviewedArticles(
                Observable.just(ArticlesDataFactory.makeArticleEntityList(2)))

        val testObserver = getReviewedArticles.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getLikedArticlesCallsRepository() {
        stubDataRepositoryGetReviewedArticles(
                Observable.just(ArticlesDataFactory.makeArticleEntityList(2)))

        getReviewedArticles.buildUseCaseObservable().test()
        verify(dataRepository).getReviewedArticles()
    }

    private fun stubDataRepositoryGetReviewedArticles(single: Observable<List<ArticleEntity>>) {
        whenever(dataRepository.getReviewedArticles()).thenReturn(single)
    }
}