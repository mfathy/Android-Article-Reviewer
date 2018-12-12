package me.mfathy.home24.android.articles.domain.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * IoThread is a Scheduler provider, which provides IO scheduler.
 */
class IoThread @Inject constructor(): SubscriberThread {

    override val scheduler: Scheduler
        get() = Schedulers.io()
}