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
import com.example.medquiz.ui.Screens.StatisticsScreen // <-- Bu import önemli
import com.example.medquiz.ui.Screens.WelcomeScreen

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Categories : Screen("categories")
    object Statistics : Screen("statistics") // <-- EKLENDİ

    object Questions : Screen("questions/{categoryId}") {
        fun createRoute(categoryId: Long) = "questions/$categoryId"
    }

    object QuestionDetail : Screen("question/{questionId}") {
        fun createRoute(id: Long) = "question/$id"
    }

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
                // --- HATAYI ÇÖZEN KISIM BURASI ---
                onNavigateToStats = {
                    navController.navigate(Screen.Statistics.route)
                },
                // ---------------------------------
                onAddQuestion = { categoryId ->
                    navController.navigate(Screen.AddQuestion.createRoute(categoryId))
                }
            )
        }

        // --- Statistics (İstatistik Ekranı Rotası) ---
        composable(Screen.Statistics.route) {
            StatisticsScreen(
                onBack = { navController.popBackStack() }
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
                    navController.navigate(Screen.AddQuestion.createRoute(catId))
                }
            )
        }

        // --- Question Detail ---
        // --- Question Detail ---
        composable(
            route = Screen.QuestionDetail.route,
            arguments = listOf(navArgument("questionId") { type = NavType.LongType })
        ) { backStackEntry ->
            val qId = backStackEntry.arguments?.getLong("questionId") ?: 0L

            QuestionDetailScreen(
                questionId = qId,
                onBack = { navController.popBackStack() },
                onNavigateToQuestion = { nextQuestionId ->
                    navController.navigate(Screen.QuestionDetail.createRoute(nextQuestionId))
                }
            )
        }

        // --- Add Question ---
        composable(
            route = Screen.AddQuestion.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.LongType })
        ) { backStackEntry ->
            val catId = backStackEntry.arguments?.getLong("categoryId") ?: -1L

            val context = androidx.compose.ui.platform.LocalContext.current
            val scope = androidx.compose.runtime.rememberCoroutineScope()

            val db = com.example.medquiz.data.local.AppDatabase.getDatabase(context, scope)

            val repo = com.example.medquiz.data.repository.QuizRepository(
                db.categoryDao(),
                db.questionDao(),
                db.statsDao()
            )

            val factory = com.example.medquiz.vm.AddQuestionViewModelFactory(repo)
            val myViewModel: com.example.medquiz.vm.AddQuestionViewModel =
                androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)

            AddQuestionScreen(
                navController = navController,
                viewModel = myViewModel,
                defaultCategoryId = if (catId != -1L) catId else null

            )
        }

    }
}