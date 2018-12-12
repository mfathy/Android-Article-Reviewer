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
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for GetArticles
 */
@RunWith(MockitoJUnitRunner::class)
class GetArticlesTest {
    private lateinit var getArticles: GetArticles
    @Mock
    lateinit var dataRepository: ArticlesRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Mock
    lateinit var subscriberThread: SubscriberThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getArticles = GetArticles(dataRepository, subscriberThread, postExecutionThread)
    }

    @Test
    fun getLikedArticlesCompletes() {
        stubDataRepositoryGetArticles(
                Observable.just(ArticlesDataFactory.makeArticleEntityList(2)))

        val testObserver = getArticles.buildUseCaseObservable(GetArticles.Params(2)).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getArticlesThrowsException() {
        getArticles.buildUseCaseObservable().test()
    }

    @Test
    fun getLikedArticlesCallsRepository() {
        stubDataRepositoryGetArticles(
                Observable.just(ArticlesDataFactory.makeArticleEntityList(2)))

        getArticles.buildUseCaseObservable(GetArticles.Params(2)).test()
        verify(dataRepository).getArticles(anyInt())
    }

    private fun stubDataRepositoryGetArticles(single: Observable<List<ArticleEntity>>) {
        whenever(dataRepository.getArticles(anyInt())).thenReturn(single)
    }
}