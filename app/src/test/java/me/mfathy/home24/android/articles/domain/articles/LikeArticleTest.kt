package me.mfathy.home24.android.articles.domain.articles

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import me.mfathy.home24.android.articles.data.repository.ArticlesRepository
import me.mfathy.home24.android.articles.domain.executor.PostExecutionThread
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
 * Unit tests for LikeArticle
 */
@RunWith(MockitoJUnitRunner::class)
class LikeArticleTest {
    private lateinit var likeArticle: LikeArticle
    @Mock
    lateinit var dataRepository: ArticlesRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Mock
    lateinit var subscriberThread: SubscriberThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        likeArticle = LikeArticle(dataRepository, subscriberThread, postExecutionThread)
    }

    @Test
    fun likeArticleCompletes() {
        val articleEntity = ArticlesDataFactory.makeArticleEntity()
        stubDataRepositoryLikeArticle(Completable.complete())

        val testObserver = likeArticle.buildUseCaseCompletable(
                LikeArticle.Params(articleEntity)).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun likeArticleThrowsException() {
        likeArticle.buildUseCaseCompletable().test()
    }

    @Test
    fun likeArticleCallsRepository() {
        val articleEntity = ArticlesDataFactory.makeArticleEntity()
        stubDataRepositoryLikeArticle(Completable.complete())

        likeArticle.buildUseCaseCompletable(LikeArticle.Params(articleEntity)).test()
        verify(dataRepository).likeArticle(articleEntity)
    }

    private fun stubDataRepositoryLikeArticle(completable: Completable) {
        whenever(dataRepository.likeArticle(any())).thenReturn(completable)
    }

}