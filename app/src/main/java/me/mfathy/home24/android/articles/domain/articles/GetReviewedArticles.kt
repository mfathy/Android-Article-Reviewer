package me.mfathy.home24.android.articles.domain.articles

import me.mfathy.home24.android.articles.domain.executor.PostExecutionThread
import io.reactivex.Observable
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.data.repository.ArticlesRepository
import me.mfathy.home24.android.articles.domain.executor.SubscriberThread
import me.mfathy.home24.android.articles.domain.interactor.ObservableUseCase
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * GetReviewedArticles is a use case for getting all reviewed articles from data stores.
 */
open class GetReviewedArticles @Inject constructor(
        private val dataRepository: ArticlesRepository,
        subscriberThread: SubscriberThread,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<ArticleEntity>, Nothing?>(subscriberThread, postExecutionThread) {
    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<ArticleEntity>> {
        return dataRepository.getReviewedArticles()
    }
}