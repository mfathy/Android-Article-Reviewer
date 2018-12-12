package me.mfathy.home24.android.articles.ui.start

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.domain.articles.ClearArticles
import me.mfathy.home24.android.articles.domain.articles.GetReviewedArticles
import me.mfathy.home24.android.articles.factory.ArticlesDataFactory
import me.mfathy.home24.android.articles.factory.DataFactory
import me.mfathy.home24.android.articles.ui.state.ResourceState
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

/**
 * Created by Mohammed Fathy on 11/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for StartViewModel
 */
@RunWith(JUnit4::class)
class StartViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    var getReviewedArticles = mock<GetReviewedArticles>()
    var clearArticles = mock<ClearArticles>()
    var startViewModel = StartViewModel(getReviewedArticles, clearArticles)

    @Captor
    val captorArticles = argumentCaptor<DisposableObserver<List<ArticleEntity>>>()

    @Captor
    val captorClearArticle = argumentCaptor<DisposableCompletableObserver>()

    @Test
    fun fetchReviewedArticlesExecutesUseCase() {
        startViewModel.fetchReviewedArticles()

        verify(getReviewedArticles, times(1)).execute(captorArticles.capture(), eq(null))
    }

    @Test
    fun fetchReviewedArticlesReturnsSuccess() {
        val articles = ArticlesDataFactory.makeArticleEntityList(2)

        startViewModel.fetchReviewedArticles()

        verify(getReviewedArticles).execute(captorArticles.capture(), eq(null))
        captorArticles.firstValue.onNext(articles)

        Assert.assertEquals(ResourceState.SUCCESS,
                startViewModel.getReviewedArticlesLiveData().value?.status)
    }

    @Test
    fun clearArticlesReturnsSuccess() {
        startViewModel.clearArticles()

        verify(clearArticles).execute(captorClearArticle.capture(), any())
        captorClearArticle.firstValue.onComplete()

        Assert.assertEquals(ResourceState.SUCCESS,
                startViewModel.getIsArticlesClearedLiveData().value?.status)
    }

    @Test
    fun fetchReviewedArticlesReturnsData() {
        val articles = ArticlesDataFactory.makeArticleEntityList(2)

        startViewModel.fetchReviewedArticles()

        verify(getReviewedArticles).execute(captorArticles.capture(), eq(null))
        captorArticles.firstValue.onNext(articles)

        Assert.assertEquals(articles, startViewModel.getReviewedArticlesLiveData().value?.data)
    }

    @Test
    fun fetchReviewedArticlesReturnsError() {
        startViewModel.fetchReviewedArticles()

        verify(getReviewedArticles).execute(captorArticles.capture(), eq(null))
        captorArticles.firstValue.onError(RuntimeException())

        Assert.assertEquals(ResourceState.ERROR,
                startViewModel.getReviewedArticlesLiveData().value?.status)
    }


    @Test
    fun fetchReviewedArticlesReturnsMessageForError() {
        val errorMessage = DataFactory.randomString()
        startViewModel.fetchReviewedArticles()

        verify(getReviewedArticles).execute(captorArticles.capture(), eq(null))
        captorArticles.firstValue.onError(RuntimeException(errorMessage))

        Assert.assertEquals(errorMessage,
                startViewModel.getReviewedArticlesLiveData().value?.message)
    }
}