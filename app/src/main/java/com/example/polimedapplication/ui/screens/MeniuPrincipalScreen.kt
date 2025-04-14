package com.example.polimedapplication.ui.screens

import android.database.sqlite.SQLiteDatabase
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.polimedapplication.R
import com.example.polimedapplication.data.BazaDateAjutor
import kotlinx.coroutines.launch


@Composable
fun MeniuPrincipalScreen(navController: NavController, idUtilizator: String) {
    val context = LocalContext.current
    val dbHelper = remember { BazaDateAjutor(context) }
    val db: SQLiteDatabase = dbHelper.readableDatabase

    var numeComplet by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }

    LaunchedEffect(idUtilizator) {
        val cursor = db.rawQuery(
            """
            SELECT nume, prenume 
            FROM Pacienti 
            WHERE idUtilizator = ?
            UNION
            SELECT nume, prenume 
            FROM Medici 
            WHERE idUtilizator = ?
            """.trimIndent(),
            arrayOf(idUtilizator, idUtilizator)
        )
        if (cursor.moveToFirst()) {
            val nume = cursor.getString(0)
            val prenume = cursor.getString(1)
            numeComplet = "$nume $prenume"
        }
        cursor.close()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Fundal gri cu colțuri rotunjite
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 24.dp)
                .background(color = Color(0xFFF2F2F2), shape = RoundedCornerShape(28.dp))
        )

        // Iconiță meniu
        Box(modifier = Modifier.align(Alignment.TopEnd)) {
            IconButton(
                onClick = { showMenu = !showMenu },
                modifier = Modifier
                    .padding(top = 39.dp, end = 35.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = "Meniu",
                    modifier = Modifier.size(40.dp)
                )
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
                modifier = Modifier
                    .background(
                        color = Color.White,
                    )
                    .padding(vertical = 4.dp)
                    .width(220.dp)
            ) {
                // Opțiune 1: Programează medicament
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showMenu = false
                            navController.navigate("programare_medicament/$idUtilizator")
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "Programează administrare medicament",
                        fontSize = 14.sp,
                        fontFamily = fontFamilySegoeUI,
                        color = Color.Black
                    )
                }

                Divider(color = Color(0xFFE5E5E5), thickness = 1.dp)

                // Opțiune 2: Setări
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showMenu = false
                            navController.navigate("setari/$idUtilizator")
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "Setări",
                        fontSize = 14.sp,
                        fontFamily = fontFamilySegoeUI,
                        color = Color.Black
                    )
                }
            }

        }

        // Salutul
        Column(
            modifier = Modifier
                .padding(start = 24.dp, top = 90.dp)
                .align(Alignment.TopStart)
        ) {
            Text(
                text = "Bună,",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamilySegoeUI
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "$numeComplet 👋",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = fontFamilySegoeUI
            )
        }
    }
}
