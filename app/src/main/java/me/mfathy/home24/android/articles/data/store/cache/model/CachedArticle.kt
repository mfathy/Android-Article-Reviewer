package me.mfathy.home24.android.articles.data.store.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.mfathy.home24.android.articles.config.AppConstants

@Entity(tableName = AppConstants.ARTICLES_TABLE_NAME)
data class CachedArticle(
        @ColumnInfo(name = AppConstants.COLUMN_TITLE)
        val title: String,
        @PrimaryKey
        @ColumnInfo(name = AppConstants.COLUMN_SKU)
        val sku: String,
        @ColumnInfo(name = AppConstants.COLUMN_IMAGE_URL)
        val imageUrl: String,
        @ColumnInfo(name = AppConstants.COLUMN_IS_LIKED)
        var isLiked: Int,
        @ColumnInfo(name = AppConstants.COLUMN_IS_REVIEWED)
        var isReviewed: Boolean)
