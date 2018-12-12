package me.mfathy.home24.android.articles.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.mfathy.home24.android.articles.ui.state.ResourceState
import io.reactivex.observers.DisposableObserver
import me.mfathy.home24.android.articles.config.AppConstants.ARTICLES_COUNT
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.domain.articles.GetArticles
import me.mfathy.home24.android.articles.ui.state.Resource
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 */
open class ReviewViewModel @Inject internal constructor(
        private val getArticles: GetArticles) : ViewModel() {

    private val articlesLiveData: MutableLiveData<Resource<List<ArticleEntity>>> = MutableLiveData()

    override fun onCleared() {
        getArticles.dispose()
        super.onCleared()
    }

    /**
     * Returns a live data object containing a list of articles wrapped in a resource change object.
     */
    fun getArticlesLiveData(): LiveData<Resource<List<ArticleEntity>>> {
        return articlesLiveData
    }

    /**
     * Fetches articles by running getArticles use case.
     */
    fun fetchArticles() {
        articlesLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getArticles.execute(ArticlesSubscriber(), GetArticles.Params.forGetArticles(ARTICLES_COUNT))
    }

    /**
     * GetArticles subscriber to receive data changes from the use case.
     */
    inner class ArticlesSubscriber : DisposableObserver<List<ArticleEntity>>() {
        override fun onComplete() { }

        override fun onNext(articles: List<ArticleEntity>) {
            articlesLiveData.postValue(Resource(ResourceState.SUCCESS, articles, null))
        }

        override fun onError(e: Throwable) {
            articlesLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }
}