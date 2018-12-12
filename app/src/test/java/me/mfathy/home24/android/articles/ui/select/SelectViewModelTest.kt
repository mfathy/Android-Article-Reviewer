package me.mfathy.home24.android.articles.ui.select

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.domain.articles.DisLikeArticle
import me.mfathy.home24.android.articles.domain.articles.GetArticles
import me.mfathy.home24.android.articles.domain.articles.GetReviewedArticles
import me.mfathy.home24.android.articles.domain.articles.LikeArticle
import me.mfathy.home24.android.articles.factory.ArticlesDataFactory
import me.mfathy.home24.android.articles.factory.DataFactory
import me.mfathy.home24.android.articles.ui.state.ResourceState
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

/**
 * Created by Mohammed Fathy on 11/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for SelectViewModel
 */
@RunWith(JUnit4::class)
class SelectViewModelTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    var getArticles = mock<GetArticles>()
    var likeArticle = mock<LikeArticle>()
    var dislikeArticle = mock<DisLikeArticle>()
    var getReviewedArticles = mock<GetReviewedArticles>()
    var selectViewModel = SelectViewModel(getArticles, getReviewedArticles, likeArticle, dislikeArticle)

    @Captor
    val captorArticles = argumentCaptor<DisposableObserver<List<ArticleEntity>>>()

    @Captor
    val captorLikeArticle = argumentCaptor<DisposableCompletableObserver>()

    @Test
    fun fetchArticlesExecutesUseCase() {
        selectViewModel.fetchArticles()

        verify(getArticles, times(1)).execute(any(), any())
    }

    @Test
    fun fetchArticlesReturnsSuccess() {
        val articles = ArticlesDataFactory.makeArticleEntityList(2)

        selectViewModel.fetchArticles()

        verify(getArticles).execute(captorArticles.capture(), any())
        captorArticles.firstValue.onNext(articles)

        assertEquals(ResourceState.SUCCESS,
                selectViewModel.getArticlesLiveData().value?.status)
    }

    @Test
    fun likeArticleReturnsSuccess() {
        val article = ArticlesDataFactory.makeArticleEntity()
        article.isLiked = 1
        selectViewModel.likeArticle(article)

        verify(likeArticle).execute(captorLikeArticle.capture(), any())
        captorLikeArticle.firstValue.onComplete()

        assertEquals(ResourceState.SUCCESS,
                selectViewModel.getLikedLiveData().value?.status)
    }

    @Test
    fun dislikeArticleReturnsSuccess() {
        val article = ArticlesDataFactory.makeArticleEntity()
        article.isLiked = 0
        selectViewModel.dislikeArticle(article)

        verify(dislikeArticle).execute(captorLikeArticle.capture(), any())
        captorLikeArticle.firstValue.onComplete()

        assertEquals(ResourceState.SUCCESS,
                selectViewModel.getDisLikedLiveData().value?.status)
    }

    @Test
    fun fetchArticlesReturnsData() {
        val articles = ArticlesDataFactory.makeArticleEntityList(2)

        selectViewModel.fetchArticles()

        verify(getArticles).execute(captorArticles.capture(), any())
        captorArticles.firstValue.onNext(articles)

        assertEquals(articles, selectViewModel.getArticlesLiveData().value?.data)
    }

    @Test
    fun fetchArticlesReturnsError() {
        selectViewModel.fetchArticles()

        verify(getArticles).execute(captorArticles.capture(), any())
        captorArticles.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.ERROR,
                selectViewModel.getArticlesLiveData().value?.status)
    }

    @Test
    fun fetchArticlesReturnsMessageForError() {
        val errorMessage = DataFactory.randomString()
        selectViewModel.fetchArticles()

        verify(getArticles).execute(captorArticles.capture(), any())
        captorArticles.firstValue.onError(RuntimeException(errorMessage))

        assertEquals(errorMessage,
                selectViewModel.getArticlesLiveData().value?.message)
    }
}