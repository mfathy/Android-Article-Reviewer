package me.mfathy.home24.android.articles.injection.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.mfathy.home24.android.articles.injection.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import me.mfathy.home24.android.articles.ui.review.ReviewViewModel
import me.mfathy.home24.android.articles.ui.select.SelectViewModel
import me.mfathy.home24.android.articles.ui.start.StartViewModel
import kotlin.reflect.KClass

/**
 * Dagger module to provide ViewModel dependencies.
 */
@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ReviewViewModel::class)
    abstract fun bindReviewViewModel(viewModel: ReviewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectViewModel::class)
    abstract fun bindSelectionViewModel(
            viewModel: SelectViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    abstract fun bindStartViewModel(
            viewModel: StartViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
