package me.mfathy.home24.android.articles.injection.modules

import android.content.Context
import dagger.Binds
import dagger.Module
import me.mfathy.home24.android.articles.Home24Application


/**
 * Dagger application module to provide app context.
 */
@Module
abstract class ApplicationModule {
    @Binds
    abstract fun bindContext(application: Home24Application): Context

}
