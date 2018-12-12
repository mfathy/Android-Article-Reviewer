package me.mfathy.home24.android.articles.data.store.remote.model


import com.google.gson.annotations.SerializedName

data class ArticleModel(@SerializedName("_embedded")
                        val embedded: Embedded)


data class Embedded(@SerializedName("articles")
                    val articles: List<ArticleItem>)


data class ArticleItem(@SerializedName("media")
                       val media: List<MediaItem>,
                       @SerializedName("sku")
                       val sku: String = "",
                       @SerializedName("title")
                       val title: String = "")


data class MediaItem(@SerializedName("uri")
                     val uri: String = "")


