package me.mfathy.home24.android.articles.ui.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import me.mfathy.home24.android.articles.R
import me.mfathy.home24.android.articles.data.model.ArticleEntity
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 */
class ReviewAdapter @Inject constructor() : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    var articles: List<ArticleEntity> = arrayListOf()
    var isViewList = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewResource = R.layout.view_grid_item_article_review                   // Grid
        if (isViewList) viewResource = R.layout.view_list_item_article_review       // List
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(viewResource, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articles.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]

        Glide.with(holder.itemView.context)
                .load(article.imageUrl)
                .apply(RequestOptions.fitCenterTransform())
                .into(holder.imageViewArticle)

        if (article.isLiked == 1) {
            holder.imageViewLiked.setImageResource(R.drawable.ic_favorite_black_24dp)
        } else {
            holder.imageViewLiked.setImageResource(0)
        }

        holder.imageViewArticle.contentDescription = article.title
        if (isViewList) holder.textViewTitle?.text = article.title
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewTitle: TextView? = view.findViewById(R.id.textViewArticleTitle)
        var imageViewArticle: ImageView = view.findViewById(R.id.imageViewArticle)
        var imageViewLiked: ImageView = view.findViewById(R.id.imageViewLiked)
    }
}