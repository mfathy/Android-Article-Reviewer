package me.mfathy.home24.android.articles.injection

import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import me.mfathy.home24.android.articles.data.repository.ArticlesRepository
import javax.inject.Singleton

@Module
object TestDataModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideDataRepository(): ArticlesRepository {
        return mock()
    }

}