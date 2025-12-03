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

            qDao.deleteAllQuestions()
            catDao.deleteAllCategories()

            //============================
            // Part 1:Anatomi
            //============================
            val anatomiId = catDao.insert(CategoryEntity(name = "Anatomi", description = "Vücut Yapısı Bilimi"))

            val makroId = catDao.insert(CategoryEntity(name = "Makro Anatomi", parentId = anatomiId))
            val noroAnatomiId = catDao.insert(CategoryEntity(name = "Nöroanatomi", parentId = anatomiId))
            val mikroAnatomiId = catDao.insert(CategoryEntity(name = "Mikroanatomi", parentId = anatomiId))
            val embriyolojiId = catDao.insert(CategoryEntity(name = "Embriyoloji", parentId = anatomiId))
            val topografikId = catDao.insert(CategoryEntity(name = "Topografik Anatomi", parentId = anatomiId))
            val klinikAnatomiId = catDao.insert(CategoryEntity(name = "Klinik Anatomi", parentId = anatomiId))

            qDao.insert(QuestionEntity(
                categoryId = makroId,
                questionText = "Humerus kemiği vücudun hangi bölümünde bulunur?",
                option1 = "Üst Kol", option2 = "Uyluk", option3 = "Kaval Kemiği", option4 = "Ön Kol",
                correctIndex = 0,
                explanation = "Humerus, üst ekstremitede omuz ile dirsek arasında yer alan kemiktir."
            ))

            //============================
            // Part 2:Physiology
            //============================
            val fizyoId = catDao.insert(CategoryEntity(name = "Fizyoloji", description = "İşleyiş Bilimi"))

            val sinirFizyoId = catDao.insert(CategoryEntity(name = "Sinir Sistemi Fizyolojisi", parentId = fizyoId))
            val kasFizyoId = catDao.insert(CategoryEntity(name = "Kas Fizyolojisi", parentId = fizyoId))
            val dolasimFizyoId = catDao.insert(CategoryEntity(name = "Dolaşım Sistemi Fizyolojisi", parentId = fizyoId))
            val solunumFizyoId = catDao.insert(CategoryEntity(name = "Solunum Sistemi Fizyolojisi", parentId = fizyoId))
            val sindirimFizyoId = catDao.insert(CategoryEntity(name = "Sindirim Sistemi Fizyolojisi", parentId = fizyoId))
            val bosaltimFizyoId = catDao.insert(CategoryEntity(name = "Boşaltım Sistemi Fizyolojisi", parentId = fizyoId))
            val endokrinFizyoId = catDao.insert(CategoryEntity(name = "Endokrin Sistem Fizyolojisi", parentId = fizyoId))
            val uremeFizyoId = catDao.insert(CategoryEntity(name = "Üreme Fizyolojisi", parentId = fizyoId))
            val hematolojiId = catDao.insert(CategoryEntity(name = "Hematoloji (Kan Fizyolojisi)", parentId = fizyoId))
            val duyuFizyoId = catDao.insert(CategoryEntity(name = "Duyu Organları Fizyolojisi", parentId = fizyoId))
            val hucreFizyoId = catDao.insert(CategoryEntity(name = "Hücre Fizyolojisi", parentId = fizyoId))

            qDao.insert(QuestionEntity(
                categoryId = kasFizyoId,
                questionText = "Kas kasılması sırasında kalsiyum iyonları hangisine bağlanır?",
                option1 = "Aktin", option2 = "Miyozin", option3 = "Troponin C", option4 = "Tropomiyozin",
                correctIndex = 2,
                explanation = "Ca+2 iyonları Troponin C'ye bağlanarak kasılma sürecini başlatır."
            ))

            //============================
            // Part 3:Biochremistry
            //============================
            val bioId = catDao.insert(CategoryEntity(name = "Biyokimya"))

            val metabolizmaId = catDao.insert(CategoryEntity(name = "Metabolizma", parentId = bioId))
            val enzimlerId = catDao.insert(CategoryEntity(name = "Enzimler", parentId = bioId))
            val vitaminlerId = catDao.insert(CategoryEntity(name = "Vitaminler ve Koenzimler", parentId = bioId))
            val hormonBioId = catDao.insert(CategoryEntity(name = "Hormon Biyokimyası", parentId = bioId))
            val genetikBioId = catDao.insert(CategoryEntity(name = "Genetik ve Moleküler Biyoloji", parentId = bioId))
            val hucreSinyalId = catDao.insert(CategoryEntity(name = "Hücre İçi Sinyal Yolları", parentId = bioId))
            val asitBazId = catDao.insert(CategoryEntity(name = "Asit-Baz Dengesi ve Tampon Sistemler", parentId = bioId))
            val klinikBioId = catDao.insert(CategoryEntity(name = "Klinik Biyokimya", parentId = bioId))

            //============================
            // Part 4:Histology
            //============================
            val histoId = catDao.insert(CategoryEntity(name = "Histoloji"))

            val epitelId = catDao.insert(CategoryEntity(name = "Epitel Dokusu", parentId = histoId))
            val bagDokusuId = catDao.insert(CategoryEntity(name = "Bağ Dokusu", parentId = histoId))
            val kasDokusuId = catDao.insert(CategoryEntity(name = "Kas Dokusu", parentId = histoId))
            val sinirDokusuId = catDao.insert(CategoryEntity(name = "Sinir Dokusu", parentId = histoId))
            val kanDokuId = catDao.insert(CategoryEntity(name = "Kan ve Hematopoetik Doku", parentId = histoId))
            val kardiyoHistoId = catDao.insert(CategoryEntity(name = "Kardiyovasküler Sistem Histolojisi", parentId = histoId))
            val solunumHistoId = catDao.insert(CategoryEntity(name = "Solunum Sistemi Histolojisi", parentId = histoId))
            val sindirimHistoId = catDao.insert(CategoryEntity(name = "Sindirim Sistemi Histolojisi", parentId = histoId))
            val urogenitalHistoId = catDao.insert(CategoryEntity(name = "Ürogenital Sistem Histolojisi", parentId = histoId))
            val endokrinHistoId = catDao.insert(CategoryEntity(name = "Endokrin Sistem Histolojisi", parentId = histoId))
            val deriHistoId = catDao.insert(CategoryEntity(name = "Deri ve Ekleri Histolojisi", parentId = histoId))

            //============================
            // Part 5:Mikro Biology
            //============================
            val mikroId = catDao.insert(CategoryEntity(name = "Mikrobiyoloji"))

            val bakteriyolojiId = catDao.insert(CategoryEntity(name = "Bakteriyoloji", parentId = mikroId))
            val virolojiId = catDao.insert(CategoryEntity(name = "Viroloji", parentId = mikroId))
            val mikolojiId = catDao.insert(CategoryEntity(name = "Mikoloji (Mantarlar)", parentId = mikroId))
            val parazitolojiId = catDao.insert(CategoryEntity(name = "Parazitoloji", parentId = mikroId))
            val immunolojiId = catDao.insert(CategoryEntity(name = "İmmunoloji", parentId = mikroId))
            val antibiyotikId = catDao.insert(CategoryEntity(name = "Antibiyotikler ve Direnç Mekanizmaları", parentId = mikroId))
            val klinikMikroId = catDao.insert(CategoryEntity(name = "Klinik Mikrobiyoloji", parentId = mikroId))


        }
    }
}