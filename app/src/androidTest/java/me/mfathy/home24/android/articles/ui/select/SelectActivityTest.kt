package me.mfathy.home24.android.articles.ui.select

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import me.mfathy.home24.android.articles.R
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.factory.ArticlesFactory
import me.mfathy.home24.android.articles.test.TestApplication
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers


/**
 * Created by Mohammed Fathy on 11/12/2018.
 * dev.mfathy@gmail.com
 *
 * Espresso Instrumentation test >> to test that the SelectActivity works as expected.
 */
@RunWith(AndroidJUnit4::class)
class SelectActivityTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule<SelectActivity>(SelectActivity::class.java, false, false)

    @Before
    fun setUp() {
        stubArticlesRepositoryGetArticles(Observable.just(ArticlesFactory.makeArticleEntityList(1)))
        stubArticlesRepositoryGetReviewedArticles(Observable.empty())
        stubArticlesRepositoryClearsArticles(Completable.complete())
        stubArticlesRepositoryLikeArticle(Completable.complete())
        stubArticlesRepositoryDisLikeArticle(Completable.complete())
    }

    @Test
    fun activityLaunches() {
        activity.launchActivity(null)
    }

    @Test
    fun startActivityShouldGetArticles() {
        activity.launchActivity(null)

        val textView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.txtViewCounter), ViewMatchers.withText("(0 / 1)"), ViewMatchers.isDisplayed()))
        textView.check(ViewAssertions.matches(ViewMatchers.withText("(0 / 1)")))
    }

    @Test
    fun startActivityLikeArticlePerformClick() {
        activity.launchActivity(null)

        val textView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.txtViewCounter), ViewMatchers.withText("(0 / 1)"), ViewMatchers.isDisplayed()))
        textView.check(ViewAssertions.matches(ViewMatchers.withText("(0 / 1)")))

        Thread.sleep(700)

        val likeButton = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.likeButton), ViewMatchers.withText("Like"), ViewMatchers.isDisplayed()))
        likeButton.perform(ViewActions.click())
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

    private fun stubArticlesRepositoryLikeArticle(completable: Completable) {
        whenever(TestApplication.appComponent().articlesRepository().likeArticle(any()))
                .thenReturn(completable)
    }

    private fun stubArticlesRepositoryDisLikeArticle(completable: Completable) {
        whenever(TestApplication.appComponent().articlesRepository().dislikeArticle(any()))
                .thenReturn(completable)
    }
}