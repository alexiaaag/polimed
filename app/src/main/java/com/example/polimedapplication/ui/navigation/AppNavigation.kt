package com.example.polimedapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.apppolimedicatie.ui.screens.*
import com.example.polimedapplication.ui.screens.AsteptareValidareScreen
import com.example.polimedapplication.ui.screens.CreareContMedicScreen
import com.example.polimedapplication.ui.screens.CreareContPacientScreen
import com.example.polimedapplication.ui.screens.LoginScreen
import com.example.polimedapplication.ui.screens.MeniuPrincipalScreen
import com.example.polimedapplication.ui.screens.ProgramareMedicamentScreen
import com.example.polimedapplication.ui.screens.SetariScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("selectare_rol") { SelectareRolScreen(navController) }
        composable("creare_cont_medic") { CreareContMedicScreen(navController) }
        composable("creare_cont_pacient") { CreareContPacientScreen(navController) }
        composable("login") {
            LoginScreen(navController)
        }

        composable("meniu_principal/{idUtilizator}") { backStackEntry ->
            val idUtilizator = backStackEntry.arguments?.getString("idUtilizator") ?: ""
            MeniuPrincipalScreen(navController, idUtilizator)
        }

        composable("splash") { PaginaStart(navController) }
        composable("asteptare_validare/{idUtilizator}") { backStackEntry ->
            val idUtilizator = backStackEntry.arguments?.getString("idUtilizator") ?: ""
            AsteptareValidareScreen(navController, idUtilizator)
        }
        composable("programare_medicament/{idUtilizator}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("idUtilizator") ?: ""
            ProgramareMedicamentScreen(navController, id)
        }

        composable("setari/{idUtilizator}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("idUtilizator") ?: ""
            SetariScreen(navController, id)
        }


    }
}
