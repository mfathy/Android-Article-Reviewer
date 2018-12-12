package me.mfathy.home24.android.articles.data.store.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.mfathy.home24.android.articles.data.store.cache.dao.CachedArticlesDao
import me.mfathy.home24.android.articles.data.store.cache.model.CachedArticle

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * This class is the Database helper singleton which keeps one instance of room database in memory.
 */
@Database(entities = [CachedArticle::class], version = 2, exportSchema = false)
abstract class ArticlesDatabase: RoomDatabase() {

    abstract fun cachedArticlesDao(): CachedArticlesDao

    companion object {

        private var INSTANCE: ArticlesDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): ArticlesDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                ArticlesDatabase::class.java, "articles.db")
                                .build()
                    }
                    return INSTANCE as ArticlesDatabase
                }
            }
            return INSTANCE as ArticlesDatabase
        }
    }

}