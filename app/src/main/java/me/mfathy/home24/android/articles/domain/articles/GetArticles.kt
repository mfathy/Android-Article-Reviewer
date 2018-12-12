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
 * GetArticles is a use case for getting all the articles from data stores.
 */
open class GetArticles @Inject constructor(
        private val dataRepository: ArticlesRepository,
        subscriberThread: SubscriberThread,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<ArticleEntity>, GetArticles.Params?>(subscriberThread, postExecutionThread) {
    public override fun buildUseCaseObservable(params: Params?): Observable<List<ArticleEntity>> {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        return dataRepository.getArticles(params.articleCount)
    }

    data class Params constructor(val articleCount: Int) {
        companion object {
            fun forGetArticles(articleCount: Int): Params {
                return Params(articleCount)
            }
        }
    }
}