package me.mfathy.home24.android.articles.domain.executor

import io.reactivex.Scheduler

interface SubscriberThread {
    val scheduler: Scheduler
}
