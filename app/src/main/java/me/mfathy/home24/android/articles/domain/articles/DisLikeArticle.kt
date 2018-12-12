package me.mfathy.home24.android.articles.domain.articles

import io.reactivex.Completable
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.data.repository.ArticlesRepository
import me.mfathy.home24.android.articles.domain.executor.PostExecutionThread
import me.mfathy.home24.android.articles.domain.executor.SubscriberThread
import me.mfathy.home24.android.articles.domain.interactor.CompletableUseCase
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * DisLikeArticle used for disliking article and update the data store.
 */
open class DisLikeArticle @Inject constructor(
        private val dataRepository: ArticlesRepository,
        subscriberThread: SubscriberThread,
        postExecutionThread: PostExecutionThread)
    : CompletableUseCase<DisLikeArticle.Params>(subscriberThread, postExecutionThread) {
    public override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        return dataRepository.dislikeArticle(params.entity)
    }

    data class Params constructor(val entity: ArticleEntity) {
        companion object {
            fun forDisLikeArticle(entity: ArticleEntity): Params {
                return Params(entity)
            }
        }
    }
}