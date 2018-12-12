package me.mfathy.home24.android.articles.injection.modules

import dagger.Binds
import dagger.Module
import me.mfathy.home24.android.articles.data.repository.ArticlesDataRepository
import me.mfathy.home24.android.articles.data.repository.ArticlesRepository

/**
 * Dagger module to provide data repository dependencies.
 */
@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: ArticlesDataRepository): ArticlesRepository

}