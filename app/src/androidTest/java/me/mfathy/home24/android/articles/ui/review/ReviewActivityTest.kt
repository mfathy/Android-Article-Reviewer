package me.mfathy.home24.android.articles.ui.review

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_review.*
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
 * Espresso Instrumentation test >> to test that the ReviewActivity works as expected.
 */
@RunWith(AndroidJUnit4::class)
class ReviewActivityTest {

    @Rule
    @JvmField
    val activity = ActivityTestRule<ReviewActivity>(ReviewActivity::class.java, false, false)

    @Test
    fun activityLaunches() {
        activity.launchActivity(null)
    }

    @Test
    fun startActivityShouldGetArticles() {
        val entityList = ArticlesFactory.makeArticleEntityList(3)
        stubArticlesRepositoryGetArticles(Observable.just(entityList))

        activity.launchActivity(null)

        entityList.forEachIndexed { index, articleEntity ->
            Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                    .perform(RecyclerViewActions.scrollToPosition<ReviewAdapter.ViewHolder>(index))

            Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                    .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(articleEntity.title))))
        }
    }

    private fun stubArticlesRepositoryGetArticles(observable: Observable<List<ArticleEntity>>) {
        whenever(TestApplication.appComponent().articlesRepository().getArticles(ArgumentMatchers.anyInt()))
                .thenReturn(observable)
    }
}