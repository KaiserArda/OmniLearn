package com.example.medquiz.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.medquiz.data.local.dao.CategoryDao
import com.example.medquiz.data.local.dao.QuestionDao
import com.example.medquiz.data.local.entity.CategoryEntity
import com.example.medquiz.data.local.entity.QuestionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [CategoryEntity::class, QuestionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // DAO'ları dışarıya açıyorum
    abstract fun categoryDao(): CategoryDao
    abstract fun questionDao(): QuestionDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "medquiz_db"
                )
                    // Veritabanı ilk oluştuğunda çalışacak callback (Soru yükleme işlemi)
                    .addCallback(Prepopulate(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class Prepopulate(private val scope: CoroutineScope) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

    }
}