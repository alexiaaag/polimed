package com.example.polimedapplication.ui.screens

import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.polimedapplication.R
import com.example.polimedapplication.data.BazaDateAjutor
import kotlinx.coroutines.delay

@Composable
fun AsteptareValidareScreen(navController: NavController, idUtilizator: String) {
    val context = LocalContext.current
    val dbHelper = remember { BazaDateAjutor(context) }
    val db: SQLiteDatabase = dbHelper.readableDatabase
    val segoeFont = FontFamily(Font(R.font.segoeui)) // Asigură-te că fontul este adăugat în res/font

    var esteAprobat by remember { mutableStateOf(false) }

    // Verificare dacă utilizatorul a fost aprobat
    LaunchedEffect(Unit) {
        while (!esteAprobat) {
            val cursor = db.rawQuery("SELECT aprobat FROM Utilizatori WHERE idUtilizator = ?", arrayOf(idUtilizator))
            if (cursor.moveToFirst()) {
                val aprobat = cursor.getInt(0)
                if (aprobat == 1) {
                    esteAprobat = true
                    navController.navigate("login") {
                        popUpTo("asteptare_validare/$idUtilizator") { inclusive = true }
                    }
                }
            }
            cursor.close()
            delay(3000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Fundal elipsă
        Image(
            painter = painterResource(id = R.drawable.elipse),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(4200.dp)
                .offset(y = (-100).dp)
                .align(Alignment.TopCenter)
        )

        // Card cu mesaj și buton
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)) // GRI DESCHIS
        ) {
            Column(
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Se așteaptă validarea contului.\nRevino la pagina de conectare.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        fontFamily = segoeFont
                    ),
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Buton home
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .size(64.dp)
                        .clickable {
                            navController.navigate("selectare_rol") {
                                popUpTo("asteptare_validare/$idUtilizator") { inclusive = true }
                            }
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "Home",
                        modifier = Modifier
                            //.padding(20.dp)
                            .fillMaxSize()
                    )
                }
            }
        }

        // Capsula jos stânga
        Image(
            painter = painterResource(id = R.drawable.capsule1),
            contentDescription = "Capsulă",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 16.dp, y = (-16).dp)
                .size(74.dp)
        )
    }
}
