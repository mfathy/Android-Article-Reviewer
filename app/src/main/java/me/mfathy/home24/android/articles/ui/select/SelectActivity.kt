package me.mfathy.home24.android.articles.ui.select

import android.animation.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.bottom_sheet_start_reviewing.*
import kotlinx.android.synthetic.main.view_content_selection.*
import me.mfathy.home24.android.articles.R
import me.mfathy.home24.android.articles.config.AppConstants
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import me.mfathy.home24.android.articles.injection.ViewModelFactory
import me.mfathy.home24.android.articles.ui.review.ReviewActivity
import me.mfathy.home24.android.articles.ui.state.Resource
import me.mfathy.home24.android.articles.ui.state.ResourceState
import javax.inject.Inject


class SelectActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var selectViewModel: SelectViewModel

    private lateinit var articles: List<ArticleEntity>
    private lateinit var reviewedArticles: List<ArticleEntity>

    private lateinit var sheetBehavior: BottomSheetBehavior<*>

    //  like animation variables
    private val decelerateInterpolator = DecelerateInterpolator()
    private val accelerateInterpolator = AccelerateInterpolator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        AndroidInjection.inject(this)

        setupReviewBottomSheet()

        selectViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SelectViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.label_activity_rating)

        likeButton.setOnClickListener {
            if (articles.isEmpty() || reviewedArticles.count() == articles.count()) return@setOnClickListener
            animatePhotoLike(imageViewLikeEffect)
            val articleEntity = prepareArticleForAction(LIKED_ARTICLE_VALUE)
            selectViewModel.likeArticle(articleEntity)
        }

        dislikeButton.setOnClickListener {
            if (articles.isEmpty() || reviewedArticles.count() == articles.count()) return@setOnClickListener
            val articleEntity = prepareArticleForAction(DISLIKED_ARTICLE_VALUE)
            selectViewModel.dislikeArticle(articleEntity)
        }
    }

    override fun onStart() {
        super.onStart()

        //  observe changes in articles data.
        selectViewModel.getArticlesLiveData().observe(this,
                Observer<Resource<List<ArticleEntity>>> {
                    it?.let { resource ->
                        handleArticlesDataState(resource)
                    }
                })

        //  observe changes in reviewed articles data.
        selectViewModel.getReviewedArticlesLiveData().observe(this,
                Observer<Resource<List<ArticleEntity>>> {
                    it?.let { resource ->
                        handleReviewedArticlesDataState(resource)
                    }
                })

        //  observe like event.
        selectViewModel.getLikedLiveData().observe(this,
                Observer<Resource<Boolean>> {
                    it?.let { resource ->
                        handleLikeArticleEvent(resource)
                    }
                })

        //  observe dislike event.
        selectViewModel.getDisLikedLiveData().observe(this,
                Observer<Resource<Boolean>> {
                    it?.let { resource ->
                        handleDisLikeArticleEvent(resource)
                    }
                })

        //  fetch data from data sources
        reviewedArticles = emptyList()
        articles = emptyList()
        selectViewModel.fetchReviewedArticles()
        selectViewModel.fetchArticles()

    }

    /**
     * Setups and initializes the review bottom sheet.
     */
    private fun setupReviewBottomSheet() {
        sheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        sheetBehavior.isHideable = true
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        buttonToReview.setOnClickListener { startActivity(ReviewActivity.getStartIntent(this)) }
    }

    /**
     * Handles GetArticles data changes >> updates UI.
     */
    private fun handleArticlesDataState(resource: Resource<List<ArticleEntity>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                resource.data?.let {
                    articles = it
                    var startPosition = reviewedArticles.count()
                    if (reviewedArticles.count() == articles.count()) { // in case user has review all articles.
                        startPosition = articles.count() - 1
                        showReviewBottomSheet()
                    }
                    txtViewCounter.text = String.format(getString(R.string.label_counter), reviewedArticles.count(), it.count())
                    loadingProgressBar.visibility = GONE
                    imgViewArticleCardView.visibility = VISIBLE
                    Glide.with(this)
                            .load(articles[startPosition].imageUrl)
                            .apply(RequestOptions.fitCenterTransform())
                            .transition(DrawableTransitionOptions.withCrossFade(750))
                            .into(imgViewArticle)
                    imgViewArticle.contentDescription = articles[startPosition].title
                }
            }
            ResourceState.LOADING -> {
                loadingProgressBar.visibility = VISIBLE
                imgViewArticleCardView.visibility = INVISIBLE
            }

            ResourceState.ERROR -> {
                Snackbar.make(layoutContainer, getString(R.string.error_no_articles), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Handles GetReviewedArticles data changes >> updates UI.
     */
    private fun handleReviewedArticlesDataState(resource: Resource<List<ArticleEntity>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                resource.data?.let { updatedReviewedArticles ->
                    reviewedArticles = updatedReviewedArticles
                    reviewedArticles.filter { articleEntity -> articleEntity.isLiked == 1 }.toList()
                    txtViewCounter.text = String.format(getString(R.string.label_counter),
                            reviewedArticles.filter { articleEntity -> articleEntity.isLiked == 1 }.toList().count(),
                            articles.count())
                    likeButton.isEnabled = true
                    dislikeButton.isEnabled = true

                    if (!articles.isEmpty()) {
                        if (reviewedArticles.count() != articles.count()) {
                            Glide.with(this)
                                    .load(articles[reviewedArticles.count()].imageUrl)
                                    .apply(RequestOptions.fitCenterTransform())
                                    .transition(DrawableTransitionOptions.withCrossFade(750))
                                    .into(imgViewArticle)
                            imgViewArticle.contentDescription = articles[reviewedArticles.count()].title
                        } else {
                            //  User finished the selection process and ready to review his choices.
                            likeButton.isEnabled = false
                            dislikeButton.isEnabled = false
                            showReviewBottomSheet()
                        }
                    }
                }
            }
            ResourceState.LOADING -> {
            }

            ResourceState.ERROR -> {
            }
        }
    }

    /**
     * Handles likeArticle event.
     */
    private fun handleLikeArticleEvent(resource: Resource<Boolean>) {
        when (resource.status) {
            ResourceState.LOADING -> {
            }
            ResourceState.SUCCESS -> {
                resource.data?.let {
                    if (it) {

                    } else {
                        Snackbar.make(layoutContainer, getString(R.string.error_liking_article), Snackbar.LENGTH_LONG).show()
                    }
                }
            }
            ResourceState.ERROR -> {
                Snackbar.make(layoutContainer, getString(R.string.error_like_article_generic), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun handleDisLikeArticleEvent(resource: Resource<Boolean>) {
        when (resource.status) {
            ResourceState.LOADING -> {
            }
            ResourceState.SUCCESS -> {
                resource.data?.let {
                    if (it) {

                    } else {
                        Snackbar.make(layoutContainer, getString(R.string.error_disliking_article), Snackbar.LENGTH_LONG).show()
                    }
                }
            }
            ResourceState.ERROR -> {
                Snackbar.make(layoutContainer, getString(R.string.error_dislike_article_generic), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun showReviewBottomSheet() {
        animateBottomSheet()
    }

    /**
     * Prepares the article entity for action like / dislike.
     */
    private fun prepareArticleForAction(likeness: Int): ArticleEntity {
        likeButton.isEnabled = false
        dislikeButton.isEnabled = false
        val articleEntity = articles[reviewedArticles.count()]
        articleEntity.isLiked = likeness
        articleEntity.isReviewed = true
        return articleEntity
    }

    /**
     * Animated bottom sheet start up >> Slide from bottom.
     */
    private fun animateBottomSheet() {
        val anim = ValueAnimator.ofInt(sheetBehavior.peekHeight, 650)
        anim.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            sheetBehavior.setPeekHeight(`val`)
        }
        anim.duration = 500
        anim.start()

        sheetBehavior.isHideable = false
    }

    /**
     * Shows a heart to indicate the like event.
     */
    private fun animatePhotoLike(imageView: ImageView) {
        imageView.visibility = View.VISIBLE

        imageView.scaleY = 0.1f
        imageView.scaleX = 0.1f

        val animatorSet = AnimatorSet()

        val imgScaleUpYAnim = ObjectAnimator.ofFloat(imageView, "scaleY", 0.1f, 1f)
        imgScaleUpYAnim.duration = 250
        imgScaleUpYAnim.interpolator = decelerateInterpolator
        val imgScaleUpXAnim = ObjectAnimator.ofFloat(imageView, "scaleX", 0.1f, 1f)
        imgScaleUpXAnim.duration = 250
        imgScaleUpXAnim.interpolator = decelerateInterpolator

        val imgScaleDownYAnim = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0f)
        imgScaleDownYAnim.duration = 250
        imgScaleDownYAnim.interpolator = accelerateInterpolator
        val imgScaleDownXAnim = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0f)
        imgScaleDownXAnim.duration = 250
        imgScaleDownXAnim.interpolator = accelerateInterpolator

        animatorSet.playTogether(imgScaleUpYAnim, imgScaleUpXAnim)
        animatorSet.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim)

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                imageView.visibility = View.INVISIBLE
            }
        })
        animatorSet.start()
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SelectActivity::class.java)
        }

        private val LIKED_ARTICLE_VALUE = 1
        private val DISLIKED_ARTICLE_VALUE = 0
    }
}
