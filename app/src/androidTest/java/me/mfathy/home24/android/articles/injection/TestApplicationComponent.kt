package me.mfathy.home24.android.articles.injection

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import me.mfathy.home24.android.articles.test.TestApplication
import me.mfathy.home24.android.articles.data.repository.ArticlesRepository
import me.mfathy.home24.android.articles.injection.modules.UiModule
import me.mfathy.home24.android.articles.injection.modules.ViewModelsModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    TestApplicationModule::class,
    TestCacheModule::class,
    TestDataModule::class,
    ViewModelsModule::class,
    UiModule::class,
    TestRemoteModule::class])
interface TestApplicationComponent {

    fun articlesRepository(): ArticlesRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): TestApplicationComponent.Builder

        fun build(): TestApplicationComponent
    }

    fun inject(application: TestApplication)

}