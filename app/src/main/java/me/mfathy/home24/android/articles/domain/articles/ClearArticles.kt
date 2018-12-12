package me.mfathy.home24.android.articles.domain.articles

import io.reactivex.Completable
import me.mfathy.home24.android.articles.data.repository.ArticlesRepository
import me.mfathy.home24.android.articles.domain.executor.PostExecutionThread
import me.mfathy.home24.android.articles.domain.executor.SubscriberThread
import me.mfathy.home24.android.articles.domain.interactor.CompletableUseCase
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * ClearArticles use case used for clearing data stores from articles.
 */
open class ClearArticles @Inject constructor(
        private val dataRepository: ArticlesRepository,
        subscriberThread: SubscriberThread,
        postExecutionThread: PostExecutionThread)
    : CompletableUseCase<ClearArticles.Params>(subscriberThread, postExecutionThread) {
    public override fun buildUseCaseCompletable(params: Params?): Completable {
        return dataRepository.clearArticles()
    }

    class Params

}