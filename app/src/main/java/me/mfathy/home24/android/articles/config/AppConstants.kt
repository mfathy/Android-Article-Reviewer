package me.mfathy.home24.android.articles.config

/**
 * Created by Mohammed Fathy on 11/12/2018.
 * dev.mfathy@gmail.com
 *
 * AppConstants holds all application constant variables.
 */
object AppConstants {

    /**
     * Articles table Constants
     */
    const val ARTICLES_TABLE_NAME = "articles"
    const val COLUMN_SKU = "sku"
    const val COLUMN_IS_LIKED = "is_liked"
    const val COLUMN_TITLE = "title"
    const val COLUMN_IMAGE_URL = "image_url"
    const val COLUMN_IS_REVIEWED = "is_reviewed"

    const val QUERY_ARTICLES = "SELECT * FROM $ARTICLES_TABLE_NAME"
    const val DELETE_ARTICLES = "DELETE FROM $ARTICLES_TABLE_NAME"
    const val QUERY_REVIEWED_ARTICLES = "SELECT * FROM $ARTICLES_TABLE_NAME " +
            "WHERE $COLUMN_IS_REVIEWED = 1"


    /**
     * Remote Constants
     */

    const val BASE_URL = "https://api-mobile.home24.com/api/v1/"
    const val APP_DOMAIN = 1
    const val APP_LOCAL = "de_DE"
    const val ARTICLES_COUNT = 2

}
