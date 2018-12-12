package me.mfathy.home24.android.articles.ui.start


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import me.mfathy.home24.android.articles.R
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.factory.ArticlesFactory
import me.mfathy.home24.android.articles.test.TestApplication
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers

/**
 * * Espresso Instrumentation test >> to test that the StartActivity works as expected.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class StartActivityTest {

    @Rule @JvmField
    val activity = ActivityTestRule<StartActivity>(StartActivity::class.java, false, false)

    @Before
    fun setUp() {
        stubArticlesRepositoryGetArticles(Observable.just(ArticlesFactory.makeArticleEntityList(5)))
        stubArticlesRepositoryGetReviewedArticles(Observable.just(ArticlesFactory.makeArticleEntityList(1)))
        stubArticlesRepositoryClearsArticles(Completable.complete())
    }

    @Test
    fun activityLaunches() {
        activity.launchActivity(null)
    }

    @Test
    fun startActivityTest() {
        activity.launchActivity(null)
        val startButton = onView(
                allOf(withId(R.id.buttonStart), withText("Start"), isDisplayed()))
        startButton.check(matches(isDisplayed()))

        val resumeButton = onView(
                allOf(withId(R.id.buttonResumeReview), withText("Resume Last Rating"), isDisplayed()))
        resumeButton.check(matches(isDisplayed()))
    }

    private fun stubArticlesRepositoryGetArticles(observable: Observable<List<ArticleEntity>>) {
        whenever(TestApplication.appComponent().articlesRepository().getArticles(ArgumentMatchers.anyInt()))
                .thenReturn(observable)
    }

    private fun stubArticlesRepositoryGetReviewedArticles(observable: Observable<List<ArticleEntity>>) {
        whenever(TestApplication.appComponent().articlesRepository().getReviewedArticles())
                .thenReturn(observable)
    }

    private fun stubArticlesRepositoryClearsArticles(completable: Completable) {
        whenever(TestApplication.appComponent().articlesRepository().clearArticles())
                .thenReturn(completable)
    }
}
