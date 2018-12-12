package me.mfathy.home24.android.articles.domain.articles

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import me.mfathy.home24.android.articles.data.repository.ArticlesRepository
import me.mfathy.home24.android.articles.domain.executor.PostExecutionThread
import me.mfathy.home24.android.articles.domain.executor.SubscriberThread
import me.mfathy.home24.android.articles.factory.ArticlesDataFactory
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy on 11/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for ClearArticles.
 */
@RunWith(MockitoJUnitRunner::class)
class ClearArticlesTest{
    private lateinit var clearArticles: ClearArticles
    @Mock
    lateinit var dataRepository: ArticlesRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Mock
    lateinit var subscriberThread: SubscriberThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        clearArticles = ClearArticles(dataRepository, subscriberThread, postExecutionThread)
    }

    @Test
    fun clearArticleCompletes() {
        stubDataRepositoryClearArticles(Completable.complete())

        val testObserver = clearArticles.buildUseCaseCompletable(ClearArticles.Params()).test()
        testObserver.assertComplete()
    }

    @Test
    fun clearArticlesCallsRepository() {
        stubDataRepositoryClearArticles(Completable.complete())

        clearArticles.buildUseCaseCompletable(ClearArticles.Params()).test()
        verify(dataRepository).clearArticles()
    }

    private fun stubDataRepositoryClearArticles(completable: Completable) {
        whenever(dataRepository.clearArticles()).thenReturn(completable)
    }
}