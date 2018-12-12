package me.mfathy.home24.android.articles.ui.start

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_start.*
import me.mfathy.home24.android.articles.R
import me.mfathy.home24.android.articles.config.AppConstants
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.injection.ViewModelFactory
import me.mfathy.home24.android.articles.ui.select.SelectActivity
import me.mfathy.home24.android.articles.ui.state.Resource
import me.mfathy.home24.android.articles.ui.state.ResourceState
import javax.inject.Inject

class StartActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var startViewModel: StartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        AndroidInjection.inject(this)

        startViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(StartViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        //  observe changes in articles data.
        startViewModel.getReviewedArticlesLiveData().observe(this,
                Observer<Resource<List<ArticleEntity>>> {
                    it?.let { resource ->
                        handleReviewedArticlesDataState(resource)
                    }
                })

        //  Fetch reviewed articles to detect if the user did not finish previous selection process.
        startViewModel.fetchReviewedArticles()
    }

    private fun handleReviewedArticlesDataState(resource: Resource<List<ArticleEntity>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                resource.data?.let { reviewedArticles ->
                    //  Show Resume Selection button to give the user the choice to resume old selection process.
                    if (reviewedArticles.count() > 0 && reviewedArticles.count() < AppConstants.ARTICLES_COUNT) {
                        buttonResumeReview.visibility = View.VISIBLE
                    }
                }
            }
            ResourceState.LOADING -> {
                buttonResumeReview.visibility = View.GONE
            }

            ResourceState.ERROR -> {
                buttonResumeReview.visibility = View.GONE
            }
        }
    }

    fun startSelectionScreen(view: View) {
        when (view.id) {
            R.id.buttonStart -> {
                //  Clear cached review process before starting new one.
                startViewModel.clearArticles()
                startActivity(SelectActivity.getStartIntent(this))
            }
        }
    }

    fun resumeSelectionScreen(view: View) {
        when (view.id) {
            R.id.buttonResumeReview -> startActivity(SelectActivity.getStartIntent(this))
        }
    }
}
