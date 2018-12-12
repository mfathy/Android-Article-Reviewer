package me.mfathy.home24.android.articles.data.model

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 */
data class ArticleEntity(val title: String,
                         val sku: String,
                         val imageUrl: String,
                         var isLiked: Int,
                         var isReviewed: Boolean)
