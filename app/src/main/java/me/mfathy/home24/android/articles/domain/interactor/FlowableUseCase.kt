package me.mfathy.home24.android.articles.domain.interactor

import me.mfathy.home24.android.articles.domain.executor.PostExecutionThread
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import me.mfathy.home24.android.articles.domain.executor.SubscriberThread

/**
 * FlowableUseCase is is an abstract class which provide a Flowable observable to
 * emit required data or error.
 *
 * This observable support backpressure.
 */
abstract class FlowableUseCase<T, in Params> constructor(
        private val subscriberThread: SubscriberThread,
        private val postExecutionThread: PostExecutionThread) {

    private val disposables = CompositeDisposable()

    protected abstract fun buildUseCaseObservable(params: Params? = null): Flowable<T>

    open fun execute(singleObserver: DisposableSubscriber<T>, params: Params? = null) {
        val single = this.buildUseCaseObservable(params)
                .subscribeOn(subscriberThread.scheduler)
                .observeOn(postExecutionThread.scheduler)
        addDisposable(single.subscribeWith(singleObserver))
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }

}