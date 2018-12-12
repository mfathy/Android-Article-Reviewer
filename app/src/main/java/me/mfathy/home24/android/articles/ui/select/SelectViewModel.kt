package me.mfathy.home24.android.articles.ui.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.mfathy.home24.android.articles.ui.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import me.mfathy.home24.android.articles.config.AppConstants
import me.mfathy.home24.android.articles.config.AppConstants.ARTICLES_COUNT
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.domain.articles.DisLikeArticle
import me.mfathy.home24.android.articles.domain.articles.GetArticles
import me.mfathy.home24.android.articles.domain.articles.GetReviewedArticles
import me.mfathy.home24.android.articles.domain.articles.LikeArticle
import me.mfathy.home24.android.articles.ui.state.Resource
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 */
open class SelectViewModel @Inject internal constructor(
        private val getArticles: GetArticles,
        private val getReviewedArticles: GetReviewedArticles,
        private val likeArticle: LikeArticle,
        private val dislikeArticle: DisLikeArticle) : ViewModel() {

    private val articlesLiveData: MutableLiveData<Resource<List<ArticleEntity>>> = MutableLiveData()
    private val reviewedArticlesLiveData: MutableLiveData<Resource<List<ArticleEntity>>> = MutableLiveData()
    private val likedLiveData: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    private val dislikedLiveData: MutableLiveData<Resource<Boolean>> = MutableLiveData()


    override fun onCleared() {
        getArticles.dispose()
        getReviewedArticles.dispose()
        likeArticle.dispose()
        dislikeArticle.dispose()
        super.onCleared()
    }

    /**
     * Returns articles live data object.
     */
    fun getArticlesLiveData(): LiveData<Resource<List<ArticleEntity>>> {
        return articlesLiveData
    }

    /**
     * Returns reviewed articles live data.
     */
    fun getReviewedArticlesLiveData(): LiveData<Resource<List<ArticleEntity>>> {
        return reviewedArticlesLiveData
    }

    /**
     * Returns likeArticle live data object.
     */
    fun getLikedLiveData(): LiveData<Resource<Boolean>> {
        return likedLiveData
    }

    /**
     * Returns dislikeArticle live data object.
     */
    fun getDisLikedLiveData(): LiveData<Resource<Boolean>> {
        return dislikedLiveData
    }

    /**
     * Fetches getArticles use case.
     */
    fun fetchArticles() {
        articlesLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getArticles.execute(ArticlesSubscriber(), GetArticles.Params.forGetArticles(ARTICLES_COUNT))
    }

    /**
     * Fetches getReviewedArticles use case.
     */
    fun fetchReviewedArticles() {
        reviewedArticlesLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getReviewedArticles.execute(ReviewedArticlesSubscriber())
    }

    inner class ArticlesSubscriber : DisposableObserver<List<ArticleEntity>>() {
        override fun onComplete() { }

        override fun onNext(articles: List<ArticleEntity>) {
            articlesLiveData.postValue(Resource(ResourceState.SUCCESS, articles, null))
        }

        override fun onError(e: Throwable) {
            articlesLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }

    inner class ReviewedArticlesSubscriber : DisposableObserver<List<ArticleEntity>>() {
        override fun onComplete() { }

        override fun onNext(articles: List<ArticleEntity>) {
            reviewedArticlesLiveData.postValue(Resource(ResourceState.SUCCESS, articles, null))
        }

        override fun onError(e: Throwable) {
            reviewedArticlesLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }

    fun likeArticle(entity: ArticleEntity) {
        likedLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return likeArticle.execute(LikedArticleSubscriber(),
                LikeArticle.Params.forLikeArticle(entity))
    }

    fun dislikeArticle(entity: ArticleEntity) {
        dislikedLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return dislikeArticle.execute(DisLikedArticleSubscriber(),
                DisLikeArticle.Params.forDisLikeArticle(entity))
    }

    inner class LikedArticleSubscriber: DisposableCompletableObserver() {
        override fun onComplete() {
            likedLiveData.postValue(Resource(ResourceState.SUCCESS, true, null))
        }

        override fun onError(e: Throwable) {
            likedLiveData.postValue(Resource(ResourceState.ERROR, false,
                    e.localizedMessage))
        }
    }

    inner class DisLikedArticleSubscriber: DisposableCompletableObserver() {
        override fun onComplete() {
            dislikedLiveData.postValue(Resource(ResourceState.SUCCESS, true, null))
        }

        override fun onError(e: Throwable) {
            dislikedLiveData.postValue(Resource(ResourceState.ERROR, false,
                    e.localizedMessage))
        }
    }
}