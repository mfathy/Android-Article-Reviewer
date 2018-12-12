package me.mfathy.home24.android.articles.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.observers.DisposableCompletableObserver
import me.mfathy.home24.android.articles.ui.state.ResourceState
import io.reactivex.observers.DisposableObserver
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.domain.articles.ClearArticles
import me.mfathy.home24.android.articles.domain.articles.GetReviewedArticles
import me.mfathy.home24.android.articles.ui.state.Resource
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 */

open class StartViewModel @Inject internal constructor(
        private val getReviewedArticles: GetReviewedArticles,
        private val clearArticles: ClearArticles) : ViewModel() {

    private val reviewedArticlesLiveData: MutableLiveData<Resource<List<ArticleEntity>>> = MutableLiveData()
    private val isArticlesClearedLiveData: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    override fun onCleared() {
        getReviewedArticles.dispose()
        clearArticles.dispose()
        super.onCleared()
    }

    fun getReviewedArticlesLiveData(): LiveData<Resource<List<ArticleEntity>>> {
        return reviewedArticlesLiveData
    }

    fun getIsArticlesClearedLiveData(): LiveData<Resource<Boolean>> {
        return isArticlesClearedLiveData
    }

    fun fetchReviewedArticles() {
        reviewedArticlesLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getReviewedArticles.execute(ReviewedArticlesSubscriber())
    }

    fun clearArticles() {
        isArticlesClearedLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        clearArticles.execute(IsArticlesClearedSubscriber(), ClearArticles.Params())
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

    inner class IsArticlesClearedSubscriber: DisposableCompletableObserver() {
        override fun onComplete() {
            isArticlesClearedLiveData.postValue(Resource(ResourceState.SUCCESS, true, null))
        }

        override fun onError(e: Throwable) {
            isArticlesClearedLiveData.postValue(Resource(ResourceState.ERROR, false,
                    e.localizedMessage))
        }
    }
}