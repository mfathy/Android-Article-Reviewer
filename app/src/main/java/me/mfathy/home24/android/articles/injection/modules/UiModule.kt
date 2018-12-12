package me.mfathy.home24.android.articles.injection.modules

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.mfathy.home24.android.articles.domain.executor.IoThread
import me.mfathy.home24.android.articles.domain.executor.PostExecutionThread
import me.mfathy.home24.android.articles.domain.executor.SubscriberThread
import me.mfathy.home24.android.articles.domain.executor.UiThread
import me.mfathy.home24.android.articles.ui.review.ReviewActivity
import me.mfathy.home24.android.articles.ui.select.SelectActivity
import me.mfathy.home24.android.articles.ui.start.StartActivity

/**
 * Dagger module to provide UI and activities dependencies.
 */
@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @Binds
    abstract fun bindSubscriberThread(ioThread: IoThread): SubscriberThread

    @ContributesAndroidInjector
    abstract fun contributesReviewActivity(): ReviewActivity

    @ContributesAndroidInjector
    abstract fun contributesSelectActivity(): SelectActivity

    @ContributesAndroidInjector
    abstract fun contributesStartActivity(): StartActivity
}