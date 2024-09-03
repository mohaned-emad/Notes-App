package com.mohaned.notesapp.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mohaned.notesapp.routes.Route.ADD_NOTE
import com.mohaned.notesapp.routes.Route.EDIT_NOTE
import com.mohaned.notesapp.routes.Route.HOME
import com.mohaned.notesapp.ui.screens.AddNoteScreen
import com.mohaned.notesapp.ui.screens.EditNoteScreen
import com.mohaned.notesapp.ui.screens.HomeScreen

object Route {
    const val HOME = "home"
    const val ADD_NOTE = "add_note"
    const val EDIT_NOTE = "edit_note"
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HOME) {
        composable(route = HOME) {
            HomeScreen(navController = navController)
        }
        composable(route = ADD_NOTE) {
            AddNoteScreen(navController = navController)
        }
        composable(
            route = "$EDIT_NOTE/{id}/{title}/{details}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("title") {
                    type = NavType.StringType
                    nullable = true },
                navArgument("details") { type = NavType.StringType }
            )
        )
        {
            val id = it.arguments?.getInt("id") ?: 0
            val title = it.arguments?.getString("title") ?: ""
            val details = it.arguments?.getString("details") ?: ""
            EditNoteScreen(id, title, details, navController = navController)
        }
        composable(
            route = "$EDIT_NOTE/{id}/{details}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("details") { type = NavType.StringType }
            )
        ) {
            val id = it.arguments?.getInt("id") ?: 0
            val details = it.arguments?.getString("details") ?: ""
            EditNoteScreen(id, "", details, navController = navController)
        }
    }
}