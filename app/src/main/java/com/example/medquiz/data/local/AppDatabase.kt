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
    // I make DAOs public
    abstract fun categoryDao(): CategoryDao
    abstract fun questionDao(): QuestionDao

    companion object {

        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "medquiz_db"
                )

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


        val database = AppDatabase.INSTANCE ?: return
        val catDao = database.categoryDao()
        val qDao = database.questionDao()

        scope.launch {
            // Category place
            val brainId = catDao.insert(CategoryEntity(name = "Beyin"))
            val muscleId = catDao.insert(CategoryEntity(name = "Kas"))
            val kidneyId = catDao.insert(CategoryEntity(name = "Böbrek"))
            val fibersId = catDao.insert(CategoryEntity(name = "Lifler"))

            // Question place
            qDao.insert(
                QuestionEntity(
                    categoryId = brainId,
                    questionText = "Beyindeki nöronların ana iletimi hangi iyon akışına bağlıdır?",
                    option1 = "Sodyum (Na+)",
                    option2 = "Kalsiyum (Ca2+)",
                    option3 = "Potasyum (K+)",
                    option4 = "Klor (Cl-)",
                    correctIndex = 0, // 0 -> A şıkkı
                    explanation = "Aksiyon potansiyelleri nöronlarda Na+ girişine bağlı olarak başlatılır."
                )
            )

           // When you find some question ,put here
        }
    }
}