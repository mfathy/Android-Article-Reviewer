package me.mfathy.home24.android.articles.ui.review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_review.*
import me.mfathy.home24.android.articles.R
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.injection.ViewModelFactory
import me.mfathy.home24.android.articles.ui.state.Resource
import me.mfathy.home24.android.articles.ui.state.ResourceState
import javax.inject.Inject
import androidx.recyclerview.widget.RecyclerView


/**
 * ReviewActivity present all the articles so the user can review as a collection of items.
 */
class ReviewActivity : AppCompatActivity() {

    @Inject lateinit var mReviewAdapter: ReviewAdapter
    @Inject lateinit var viewModelFactory: ViewModelFactory
    lateinit var reviewViewModel: ReviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        AndroidInjection.inject(this)

        reviewViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ReviewViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.label_activity_review)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        reviewViewModel.getArticlesLiveData().observe(this,
                Observer<Resource<List<ArticleEntity>>> {
                    it?.let { resource ->
                        handleDataState(resource)
                    }
                })
        reviewViewModel.fetchArticles()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_review, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_show_list -> {
                showRecyclerViewAsList()
                true
            }
            R.id.action_show_grid -> {
                showRecyclerViewAsGrid()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Shows the articles as list.
     */
    private fun showRecyclerViewAsList() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        mReviewAdapter.isViewList = true
        recyclerView.adapter = mReviewAdapter
        runLayoutAnimation(recyclerView, R.anim.layout_animation_slide_from_bottom)
    }

    /**
     * Show the articles as grid.
     */
    private fun showRecyclerViewAsGrid() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        mReviewAdapter.isViewList = false
        recyclerView.adapter = mReviewAdapter
        runLayoutAnimation(recyclerView, R.anim.layout_animation_fall_down)
    }

    /**
     * dispatch layout changes with animation.
     */
    private fun runLayoutAnimation(recyclerView: RecyclerView, resId: Int) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, resId)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

    /**
     * Setups the recycler view.
     */
    private fun setupRecyclerView() {
        showRecyclerViewAsList()
    }

    /**
     * Handles data changes and updates UI.
     */
    private fun handleDataState(resource: Resource<List<ArticleEntity>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                textViewError.visibility = View.GONE
                progressBarLoading.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                resource.data?.let {
                    mReviewAdapter.articles = it
                    mReviewAdapter.notifyDataSetChanged()
                }
            }
            ResourceState.LOADING -> {
                progressBarLoading.visibility = View.VISIBLE
                textViewError.visibility = View.GONE
                recyclerView.visibility = View.GONE
            }

            ResourceState.ERROR -> {
                progressBarLoading.visibility = View.GONE
                recyclerView.visibility = View.GONE
                textViewError.visibility = View.VISIBLE
                textViewError.text = getString(R.string.error_no_articles)
            }
        }
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ReviewActivity::class.java)
        }
    }
}
