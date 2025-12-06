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
    version = 2,
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
                    .fallbackToDestructiveMigration()
                    .addCallback(Prepopulate(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}



class Prepopulate(private val scope: CoroutineScope) : RoomDatabase.Callback() {
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
                // Part 1:General Lessons
                //============================
                val medId = catDao.insert(
                    CategoryEntity(
                        name = "Med",
                        description = "General Medicine Questions"
                    )
                )

                //============================
                // Part 2:Anatomy
                //============================
                val anatomyId = catDao.insert(
                    CategoryEntity(
                        name = "Anatomy",
                        description = "Science of Body Structure",
                        parentId = medId
                    )
                )

                val macroAnatomyId =
                    catDao.insert(CategoryEntity(name = "Macro Anatomy", parentId = anatomyId))
                val neuroanatomyId =
                    catDao.insert(CategoryEntity(name = "Neuroanatomy", parentId = anatomyId))
                val microanatomyId =
                    catDao.insert(CategoryEntity(name = "Microanatomy", parentId = anatomyId))
                val embryologyId =
                    catDao.insert(CategoryEntity(name = "Embryology", parentId = anatomyId))
                val topographicAnatomyId = catDao.insert(
                    CategoryEntity(
                        name = "Topographic Anatomy",
                        parentId = anatomyId
                    )
                )
                val clinicalAnatomyId =
                    catDao.insert(CategoryEntity(name = "Clinical Anatomy", parentId = anatomyId))


                //============================
                // Part 3:Physiology
                //============================
                val physiologyId = catDao.insert(
                    CategoryEntity(
                        name = "Physiology",
                        description = "Science of Function",
                        parentId = medId
                    )
                )

                val nervousPhysiologyId = catDao.insert(
                    CategoryEntity(
                        name = "Nervous System Physiology",
                        parentId = physiologyId
                    )
                )
                val musclePhysiologyId = catDao.insert(
                    CategoryEntity(
                        name = "Muscle Physiology",
                        parentId = physiologyId
                    )
                )
                val circulatoryPhysiologyId = catDao.insert(
                    CategoryEntity(
                        name = "Circulatory System Physiology",
                        parentId = physiologyId
                    )
                )
                val respiratoryPhysiologyId = catDao.insert(
                    CategoryEntity(
                        name = "Respiratory System Physiology",
                        parentId = physiologyId
                    )
                )
                val digestivePhysiologyId = catDao.insert(
                    CategoryEntity(
                        name = "Digestive System Physiology",
                        parentId = physiologyId
                    )
                )
                val excretoryPhysiologyId = catDao.insert(
                    CategoryEntity(
                        name = "Excretory System Physiology",
                        parentId = physiologyId
                    )
                )
                val endocrinePhysiologyId = catDao.insert(
                    CategoryEntity(
                        name = "Endocrine System Physiology",
                        parentId = physiologyId
                    )
                )
                val reproductivePhysiologyId = catDao.insert(
                    CategoryEntity(
                        name = "Reproductive Physiology",
                        parentId = physiologyId
                    )
                )
                val hematologyId = catDao.insert(
                    CategoryEntity(
                        name = "Hematology (Blood Physiology)",
                        parentId = physiologyId
                    )
                )
                val sensoryPhysiologyId = catDao.insert(
                    CategoryEntity(
                        name = "Sensory Organs Physiology",
                        parentId = physiologyId
                    )
                )
                val cellPhysiologyId =
                    catDao.insert(CategoryEntity(name = "Cell Physiology", parentId = physiologyId))


                //============================
                // Part 4:Biochemistry
                //============================
                val biochemistryId =
                    catDao.insert(CategoryEntity(name = "Biochemistry", parentId = medId))

                val metabolismId =
                    catDao.insert(CategoryEntity(name = "Metabolism", parentId = biochemistryId))
                val enzymesId =
                    catDao.insert(CategoryEntity(name = "Enzymes", parentId = biochemistryId))
                val vitaminsId = catDao.insert(
                    CategoryEntity(
                        name = "Vitamins and Coenzymes",
                        parentId = biochemistryId
                    )
                )
                val hormoneBioId = catDao.insert(
                    CategoryEntity(
                        name = "Hormone Biochemistry",
                        parentId = biochemistryId
                    )
                )
                val geneticBioId = catDao.insert(
                    CategoryEntity(
                        name = "Genetics and Molecular Biology",
                        parentId = biochemistryId
                    )
                )
                val cellSignalId = catDao.insert(
                    CategoryEntity(
                        name = "Intracellular Signal Pathways",
                        parentId = biochemistryId
                    )
                )
                val acidBaseId = catDao.insert(
                    CategoryEntity(
                        name = "Acid-Base Balance and Buffer Systems",
                        parentId = biochemistryId
                    )
                )
                val clinicalBioId = catDao.insert(
                    CategoryEntity(
                        name = "Clinical Biochemistry",
                        parentId = biochemistryId
                    )
                )

                //============================
                // Part 5:Histology
                //============================
                val histologyId =
                    catDao.insert(CategoryEntity(name = "Histology", parentId = medId))

                val epithelialTissueId = catDao.insert(
                    CategoryEntity(
                        name = "Epithelial Tissue",
                        parentId = histologyId
                    )
                )
                val connectiveTissueId = catDao.insert(
                    CategoryEntity(
                        name = "Connective Tissue",
                        parentId = histologyId
                    )
                )
                val muscleTissueId =
                    catDao.insert(CategoryEntity(name = "Muscle Tissue", parentId = histologyId))
                val nervousTissueId =
                    catDao.insert(CategoryEntity(name = "Nervous Tissue", parentId = histologyId))
                val bloodTissueId = catDao.insert(
                    CategoryEntity(
                        name = "Blood and Hematopoietic Tissue",
                        parentId = histologyId
                    )
                )
                val cardiovascularHistoId = catDao.insert(
                    CategoryEntity(
                        name = "Cardiovascular System Histology",
                        parentId = histologyId
                    )
                )
                val respiratoryHistoId = catDao.insert(
                    CategoryEntity(
                        name = "Respiratory System Histology",
                        parentId = histologyId
                    )
                )
                val digestiveHistoId = catDao.insert(
                    CategoryEntity(
                        name = "Digestive System Histology",
                        parentId = histologyId
                    )
                )
                val urogenitalHistoId = catDao.insert(
                    CategoryEntity(
                        name = "Urogenital System Histology",
                        parentId = histologyId
                    )
                )
                val endocrineHistoId = catDao.insert(
                    CategoryEntity(
                        name = "Endocrine System Histology",
                        parentId = histologyId
                    )
                )
                val skinHistoId = catDao.insert(
                    CategoryEntity(
                        name = "Skin and Appendages Histology",
                        parentId = histologyId
                    )
                )

                //============================
                // Part 6:Microbiology
                //============================
                val microbiologyId =
                    catDao.insert(CategoryEntity(name = "Microbiology", parentId = medId))

                val bacteriologyId =
                    catDao.insert(CategoryEntity(name = "Bacteriology", parentId = microbiologyId))
                val virologyId =
                    catDao.insert(CategoryEntity(name = "Virology", parentId = microbiologyId))
                val mycologyId = catDao.insert(
                    CategoryEntity(
                        name = "Mycology (Fungi)",
                        parentId = microbiologyId
                    )
                )
                val parasitologyId =
                    catDao.insert(CategoryEntity(name = "Parasitology", parentId = microbiologyId))
                val immunologyId =
                    catDao.insert(CategoryEntity(name = "Immunology", parentId = microbiologyId))
                val antibioticsId = catDao.insert(
                    CategoryEntity(
                        name = "Antibiotics and Resistance Mechanisms",
                        parentId = microbiologyId
                    )
                )
                val clinicalMicroId = catDao.insert(
                    CategoryEntity(
                        name = "Clinical Microbiology",
                        parentId = microbiologyId
                    )
                )

            }
        }
    }
}
