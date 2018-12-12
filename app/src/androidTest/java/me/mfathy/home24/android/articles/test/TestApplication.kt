package me.mfathy.home24.android.articles.test

import android.app.Activity
import android.app.Application
import androidx.test.InstrumentationRegistry

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import me.mfathy.home24.android.articles.injection.DaggerTestApplicationComponent
import me.mfathy.home24.android.articles.injection.TestApplicationComponent
import javax.inject.Inject

/**
 * Testing application class used to provide context and dagger modules for tests.
 */
class TestApplication: Application(), HasActivityInjector {

    @Inject lateinit var injector: DispatchingAndroidInjector<Activity>
    private lateinit var appComponent: TestApplicationComponent

    companion object {
        fun appComponent(): TestApplicationComponent {
            return (InstrumentationRegistry.getTargetContext().applicationContext
                    as TestApplication).appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestApplicationComponent.builder().application(this).build()
        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return injector
    }
}