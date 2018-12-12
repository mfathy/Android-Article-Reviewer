package me.mfathy.home24.android.articles.ui.review

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import me.mfathy.home24.android.articles.ui.state.ResourceState
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableObserver
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.domain.articles.GetArticles
import me.mfathy.home24.android.articles.factory.ArticlesDataFactory
import me.mfathy.home24.android.articles.factory.DataFactory
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Captor


/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for ReviewViewModel
 */
@RunWith(JUnit4::class)
class ReviewViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    var getArticles = mock<GetArticles>()
    var reviewViewModel = ReviewViewModel(getArticles)

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<ArticleEntity>>>()

    @Test
    fun fetchArticlesExecutesUseCase() {
        reviewViewModel.fetchArticles()

        verify(getArticles, times(1)).execute(any(), any())
    }

    @Test
    fun fetchArticlesReturnsSuccess() {
        val articles = ArticlesDataFactory.makeArticleEntityList(2)

        reviewViewModel.fetchArticles()

        verify(getArticles).execute(captor.capture(), any())
        captor.firstValue.onNext(articles)

        assertEquals(ResourceState.SUCCESS,
                reviewViewModel.getArticlesLiveData().value?.status)
    }

    @Test
    fun fetchArticlesReturnsData() {
        val articles = ArticlesDataFactory.makeArticleEntityList(2)

        reviewViewModel.fetchArticles()

        verify(getArticles).execute(captor.capture(), any())
        captor.firstValue.onNext(articles)

        assertEquals(articles, reviewViewModel.getArticlesLiveData().value?.data)
    }

    @Test
    fun fetchArticlesReturnsError() {
        reviewViewModel.fetchArticles()

        verify(getArticles).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.ERROR,
                reviewViewModel.getArticlesLiveData().value?.status)
    }

    @Test
    fun fetchArticlesReturnsMessageForError() {
        val errorMessage = DataFactory.randomString()
        reviewViewModel.fetchArticles()

        verify(getArticles).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException(errorMessage))

        assertEquals(errorMessage,
                reviewViewModel.getArticlesLiveData().value?.message)
    }

}