package me.mfathy.home24.android.articles.domain.articles

import me.mfathy.home24.android.articles.domain.executor.PostExecutionThread
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
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
 * Unit tests for DisLikeArticle
 */
@RunWith(MockitoJUnitRunner::class)
class DisLikeArticleTest {
    private lateinit var disLikeArticle: DisLikeArticle
    @Mock
    lateinit var dataRepository: ArticlesRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Mock
    lateinit var subscriberThread: SubscriberThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        disLikeArticle = DisLikeArticle(dataRepository, subscriberThread, postExecutionThread)
    }

    @Test
    fun disLikeArticleCompletes() {
        val articleEntity = ArticlesDataFactory.makeArticleEntity()
        stubDataRepositoryDisLikeArticle(Completable.complete())

        val testObserver = disLikeArticle.buildUseCaseCompletable(
                DisLikeArticle.Params(articleEntity)).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun dislikeArticleThrowsException() {
        disLikeArticle.buildUseCaseCompletable().test()
    }

    @Test
    fun dislikeArticleCallsRepository() {
        val articleEntity = ArticlesDataFactory.makeArticleEntity()
        articleEntity.isLiked = 0
        stubDataRepositoryDisLikeArticle(Completable.complete())

        disLikeArticle.buildUseCaseCompletable(DisLikeArticle.Params(articleEntity)).test()
        verify(dataRepository).dislikeArticle(articleEntity)
    }

    private fun stubDataRepositoryDisLikeArticle(completable: Completable) {
        whenever(dataRepository.dislikeArticle(any())).thenReturn(completable)
    }

}