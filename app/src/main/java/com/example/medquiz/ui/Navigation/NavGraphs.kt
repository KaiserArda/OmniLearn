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

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Categories : Screen("categories")
    object Questions : Screen("questions/{categoryId}") {
        fun createRoute(categoryId: Long) = "questions/$categoryId"
    }
    object QuestionDetail : Screen("question/{questionId}") {
        fun createRoute(id: Long) = "question/$id"
    }
    object AddQuestion : Screen("addQuestion/{categoryId?}") {
        fun createRoute(categoryId: Long?) = "addQuestion/${categoryId ?: ""}"
    }
}

@Composable
fun NavGraph(
    startDestination: String = Screen.Welcome.route, // Starts with Welcome Screen
    onInit: (androidx.navigation.NavHostController) -> Unit = {}
) {
    val navController = rememberNavController()

    // Expose navController if needed
    onInit(navController)

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 1. Welcome
        composable(Screen.Welcome.route) {
            WelcomeScreen(onNavigateToHome = {
                // Navigate to Categories and remove Welcome from back stack
                navController.navigate(Screen.Categories.route) {
                    popUpTo(Screen.Welcome.route) { inclusive = true }
                }
            })
        }

        // 2. Categories
        composable(Screen.Categories.route) {
            CategoryListScreen(
                onCategoryClick = { id ->
                    navController.navigate(Screen.Questions.createRoute(id))
                },
                onAddQuestion = {
                    navController.navigate(Screen.AddQuestion.createRoute(null))
                }
            )
        }

        // 3. Question List
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

        // 4. Question Detail
        composable(
            route = Screen.QuestionDetail.route,
            arguments = listOf(navArgument("questionId") { type = NavType.LongType })
        ) { backStackEntry ->
            val qId = backStackEntry.arguments?.getLong("questionId") ?: 0L
            QuestionDetailScreen(questionId = qId)
        }

        // 5. Add Question Screen
        composable(
            route = Screen.AddQuestion.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.LongType; defaultValue = -1L })
        ) { backStackEntry ->
            val catId = backStackEntry.arguments?.getLong("categoryId") ?: -1L
            AddQuestionScreen(
                defaultCategoryId = if (catId >= 0) catId else null,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}