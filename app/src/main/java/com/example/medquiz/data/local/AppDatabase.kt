package com.example.medquiz.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.medquiz.data.local.dao.CategoryDao
import com.example.medquiz.data.local.dao.QuestionDao
import com.example.medquiz.data.local.dao.StatsDao
import com.example.medquiz.data.local.entity.CategoryEntity
import com.example.medquiz.data.local.entity.DailyStatsEntity
import com.example.medquiz.data.local.entity.QuestionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(
    entities = [CategoryEntity::class, QuestionEntity::class, DailyStatsEntity::class],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun questionDao(): QuestionDao
    abstract fun statsDao(): StatsDao

    private class PrepopulateCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.categoryDao(), database.questionDao())
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "medquiz_db"
                )
                    .fallbackToDestructiveMigration() // Wipes old DB if version changes
                    .addCallback(PrepopulateCallback(scope)) // Triggers data loading
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


suspend fun populateDatabase(categoryDao: CategoryDao, questionDao: QuestionDao) {

    categoryDao.deleteAllCategories()
    questionDao.deleteAllQuestions()

    //============================
    // Part 1: General (Medicine Faculty)
    //============================
    val medId = categoryDao.insert(CategoryEntity(name = "cat_med", description = "desc_med"))

    //============================
    // Part 2: Anatomy
    //============================
    val anatomyId = categoryDao.insert(CategoryEntity(name = "cat_anatomy", description = "desc_anatomy", parentId = medId))

    val skeletalAnatomyId = categoryDao.insert(CategoryEntity(name = "cat_skeletal_anatomy", parentId = anatomyId))
    val neuroanatomyId = categoryDao.insert(CategoryEntity(name = "cat_neuroanatomy", parentId = anatomyId))
    val microanatomyId = categoryDao.insert(CategoryEntity(name = "cat_microanatomy", parentId = anatomyId))
    val muscularAnatomyId = categoryDao.insert(CategoryEntity(name = "cat_muscular_anatomy", parentId = anatomyId))
    val topographicAnatomyId = categoryDao.insert(CategoryEntity(name = "cat_topographic_anatomy", parentId = anatomyId))


    //============================
    // Part 3: Physiology
    //============================
    val physiologyId = categoryDao.insert(CategoryEntity(name = "cat_physiology", description = "desc_physiology", parentId = medId))

    val nervousPhysiologyId = categoryDao.insert(CategoryEntity(name = "cat_nervous_physiology", parentId = physiologyId))
    val musclePhysiologyId = categoryDao.insert(CategoryEntity(name = "cat_muscle_physiology", parentId = physiologyId))
    val circulatoryPhysiologyId = categoryDao.insert(CategoryEntity(name = "cat_circulatory_physiology", parentId = physiologyId))
    val respiratoryPhysiologyId = categoryDao.insert(CategoryEntity(name = "cat_respiratory_physiology", parentId = physiologyId))
    val digestivePhysiologyId = categoryDao.insert(CategoryEntity(name = "cat_digestive_physiology", parentId = physiologyId))
    val excretoryPhysiologyId = categoryDao.insert(CategoryEntity(name = "cat_excretory_physiology", parentId = physiologyId))
    val endocrinePhysiologyId = categoryDao.insert(CategoryEntity(name = "cat_endocrine_physiology", parentId = physiologyId))
    val reproductivePhysiologyId = categoryDao.insert(CategoryEntity(name = "cat_reproductive_physiology", parentId = physiologyId))
    val hematologyId = categoryDao.insert(CategoryEntity(name = "cat_hematology", parentId = physiologyId))
    val sensoryPhysiologyId = categoryDao.insert(CategoryEntity(name = "cat_sensory_physiology", parentId = physiologyId))
    val cellPhysiologyId = categoryDao.insert(CategoryEntity(name = "cat_cell_physiology", parentId = physiologyId))


    //============================
    // Part 4: Biochemistry
    //============================
    val biochemistryId = categoryDao.insert(CategoryEntity(name = "cat_biochemistry", parentId = medId))

    val metabolismId = categoryDao.insert(CategoryEntity(name = "cat_metabolism", parentId = biochemistryId))
    val enzymesId = categoryDao.insert(CategoryEntity(name = "cat_enzymes", parentId = biochemistryId))
    val vitaminsId = categoryDao.insert(CategoryEntity(name = "cat_vitamins", parentId = biochemistryId))
    val hormoneBioId = categoryDao.insert(CategoryEntity(name = "cat_hormone_bio", parentId = biochemistryId))
    val geneticBioId = categoryDao.insert(CategoryEntity(name = "cat_genetics", parentId = biochemistryId))
    val cellSignalId = categoryDao.insert(CategoryEntity(name = "cat_cell_signal", parentId = biochemistryId))
    val acidBaseId = categoryDao.insert(CategoryEntity(name = "cat_acid_base", parentId = biochemistryId))
    val clinicalBioId = categoryDao.insert(CategoryEntity(name = "cat_clinical_bio", parentId = biochemistryId))

    //============================
    // Part 5: Histology
    //============================
    val histologyId = categoryDao.insert(CategoryEntity(name = "cat_histology", parentId = medId))

    val epithelialTissueId = categoryDao.insert(CategoryEntity(name = "cat_epithelial", parentId = histologyId))
    val connectiveTissueId = categoryDao.insert(CategoryEntity(name = "cat_connective", parentId = histologyId))
    val muscleTissueId = categoryDao.insert(CategoryEntity(name = "cat_muscle_tissue", parentId = histologyId))
    val nervousTissueId = categoryDao.insert(CategoryEntity(name = "cat_nervous_tissue", parentId = histologyId))
    val bloodTissueId = categoryDao.insert(CategoryEntity(name = "cat_blood_tissue", parentId = histologyId))
    val cardiovascularHistoId = categoryDao.insert(CategoryEntity(name = "cat_cardio_histo", parentId = histologyId))
    val respiratoryHistoId = categoryDao.insert(CategoryEntity(name = "cat_respiratory_histo", parentId = histologyId))
    val digestiveHistoId = categoryDao.insert(CategoryEntity(name = "cat_digestive_histo", parentId = histologyId))
    val urogenitalHistoId = categoryDao.insert(CategoryEntity(name = "cat_urogenital_histo", parentId = histologyId))
    val endocrineHistoId = categoryDao.insert(CategoryEntity(name = "cat_endocrine_histo", parentId = histologyId))
    val skinHistoId = categoryDao.insert(CategoryEntity(name = "cat_skin_histo", parentId = histologyId))

    //============================
    // Part 6: Microbiology
    //============================
    val microbiologyId = categoryDao.insert(CategoryEntity(name = "cat_microbiology", parentId = medId))

    val bacteriologyId = categoryDao.insert(CategoryEntity(name = "cat_bacteriology", parentId = microbiologyId))
    val virologyId = categoryDao.insert(CategoryEntity(name = "cat_virology", parentId = microbiologyId))
    val mycologyId = categoryDao.insert(CategoryEntity(name = "cat_mycology", parentId = microbiologyId))
    val parasitologyId = categoryDao.insert(CategoryEntity(name = "cat_parasitology", parentId = microbiologyId))
    val immunologyId = categoryDao.insert(CategoryEntity(name = "cat_immunology", parentId = microbiologyId))
    val antibioticsId = categoryDao.insert(CategoryEntity(name = "cat_antibiotics", parentId = microbiologyId))
    val clinicalMicroId = categoryDao.insert(CategoryEntity(name = "cat_clinical_micro", parentId = microbiologyId))
}