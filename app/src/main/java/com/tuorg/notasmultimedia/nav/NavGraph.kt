package com.tuorg.notasmultimedia.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tuorg.notasmultimedia.ui.screens.DetailScreen
import com.tuorg.notasmultimedia.ui.screens.EditScreen
import com.tuorg.notasmultimedia.ui.screens.HomeScreen

object Routes {
    const val HOME = "home"
    const val EDIT = "edit"           // crear/editar
    const val DETAIL = "detail/{id}"  // ver detalle
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) { HomeScreen(navController) }
        composable(Routes.EDIT) { EditScreen(navController) }
        composable(Routes.DETAIL) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailScreen(navController, id)
        }
    }
}
