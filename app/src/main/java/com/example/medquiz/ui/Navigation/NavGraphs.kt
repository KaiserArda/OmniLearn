package com.example.medquiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.medquiz.ui.Screens.AddQuestionScreen
import com.example.medquiz.ui.Screens.CategoryListScreen
import com.example.medquiz.ui.Screens.QuestionDetailScreen
import com.example.medquiz.ui.Screens.QuestionListScreen
import com.example.medquiz.ui.Screens.WelcomeScreen

// 1. ADIM: Screen Sınıfını Basitleştirdik
sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Categories : Screen("categories")

    object Questions : Screen("questions/{categoryId}") {
        fun createRoute(categoryId: Long) = "questions/$categoryId"
    }

    object QuestionDetail : Screen("question/{questionId}") {
        fun createRoute(id: Long) = "question/$id"
    }

    // Değişiklik burada: Soru işareti (?) yok. ID göndermek zorunlu.
    object AddQuestion : Screen("addQuestion/{categoryId}") {
        fun createRoute(categoryId: Long) = "addQuestion/$categoryId"
    }
}

@Composable
fun NavGraph(
    startDestination: String = Screen.Welcome.route,
    onInit: (androidx.navigation.NavHostController) -> Unit = {}
) {
    val navController = rememberNavController()
    onInit(navController)

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // --- Welcome ---
        composable(Screen.Welcome.route) {
            WelcomeScreen(onNavigateToHome = {
                navController.navigate(Screen.Categories.route) {
                    popUpTo(Screen.Welcome.route) { inclusive = true }
                }
            })
        }

        // --- Categories ---
        composable(Screen.Categories.route) {
            CategoryListScreen(
                onNavigateToQuestions = { id ->
                    navController.navigate(Screen.Questions.createRoute(id))
                },
                onAddQuestion = {
                    // Kategori seçilmediyse -1 gönderiyoruz (ZORUNLU)
                    navController.navigate(Screen.AddQuestion.createRoute(-1L))
                }
            )
        }

        // --- Question List ---
        composable(
            route = Screen.Questions.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.LongType })
        ) { backStackEntry ->
            val catId = backStackEntry.arguments?.getLong("categoryId") ?: 0L
            QuestionListScreen(
                categoryId = catId,
                onQuestionClick = { qId ->
                    navController.navigate(Screen.QuestionDetail.createRoute(qId))
                },
                onAddQuestion = {
                    // Mevcut kategori ID'sini gönderiyoruz
                    navController.navigate(Screen.AddQuestion.createRoute(catId))
                }
            )
        }

        // --- Question Detail ---
        composable(
            route = Screen.QuestionDetail.route,
            arguments = listOf(navArgument("questionId") { type = NavType.LongType })
        ) { backStackEntry ->
            val qId = backStackEntry.arguments?.getLong("questionId") ?: 0L
            QuestionDetailScreen(questionId = qId)
        }

        composable(
            route = Screen.AddQuestion.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.LongType })
        ) { backStackEntry ->
            val catId = backStackEntry.arguments?.getLong("categoryId") ?: -1L

            // --- ViewModel Hazırlığı ---
            val context = androidx.compose.ui.platform.LocalContext.current

            // 1. Scope'u tanımla (YENİ EKLENDİ)
            // Bu satır için import eklemelisin: androidx.compose.runtime.rememberCoroutineScope
            val scope = androidx.compose.runtime.rememberCoroutineScope()

            // 2. Veritabanını çağırırken 'scope' parametresini de ekle (DÜZELTİLDİ)
            val db = com.example.medquiz.data.local.AppDatabase.getDatabase(context, scope)

            // 3. Repository oluştur
            val repo = com.example.medquiz.data.repository.QuizRepository(db.categoryDao(), db.questionDao())

            // 4. Fabrika ve ViewModel
            val factory = com.example.medquiz.vm.AddQuestionViewModelFactory(repo)
            val myViewModel: com.example.medquiz.vm.AddQuestionViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)

            // 5. Ekranı Çiz
            AddQuestionScreen(
                defaultCategoryId = if (catId != -1L) catId else null,
                viewModel = myViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}