package me.mfathy.home24.android.articles.data.store.remote.service

import io.reactivex.Single
import me.mfathy.home24.android.articles.data.store.remote.model.ArticleModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * RemoteService retrofit end point to access home24 backend API.
 */
interface RemoteService {

    @GET("articles")
    fun getArticles(@Query("appDomain") appDomain: Int,
                    @Query("locale") local: String,
                    @Query("limit") limit: Int)
            : Single<ArticleModel>
}