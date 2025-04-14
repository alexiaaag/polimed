package com.example.polimedapplication.ui.screens

import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.polimedapplication.R
import com.example.polimedapplication.data.BazaDateAjutor


@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val dbHelper = remember { BazaDateAjutor(context) }
    val db: SQLiteDatabase = dbHelper.readableDatabase

    var cnp by remember { mutableStateOf("") }
    var parola by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.elipse),
            contentDescription = "Elipsă",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(4200.dp)
                .offset(y = (-100).dp)
                .align(Alignment.TopCenter)
        )

        Image(
            painter = painterResource(id = R.drawable.back_white),
            contentDescription = "Back",
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.TopStart)
                .padding(start = 5.dp, top = 32.dp)
                .clickable {
                    navController.navigate("selectare_rol") {
                        popUpTo("login") { inclusive = true }
                    }
                }
        )

        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Iconiță utilizator",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.capsule1),
            contentDescription = "capsule",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 16.dp, y = (-16).dp)
                .size(74.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 240.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .width(320.dp)
                    .shadow(8.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomInputField(value = cnp, onValueChange = { cnp = it }, hint = "CNP")
                    CustomInputField(
                        value = parola,
                        onValueChange = { parola = it },
                        hint = "Parolă",
                        isPassword = true
                    )

                    Button(
                        onClick = {
                            val cursor = db.rawQuery(
                                "SELECT idUtilizator, aprobat FROM Utilizatori WHERE cnp = ? AND parola = ?",
                                arrayOf(cnp, parola)
                            )

                            if (cursor.moveToFirst()) {
                                val idUtilizator = cursor.getString(0)
                                val aprobat = cursor.getInt(1)
                                cursor.close()

                                if (aprobat == 1) {
                                    Toast.makeText(context, "Autentificare reușită!", Toast.LENGTH_SHORT).show()
                                    navController.navigate("meniu_principal/$idUtilizator")

                                } else {
                                    Toast.makeText(
                                        context,
                                        "Înregistrarea nu a fost aprobată momentan de către administrator.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                cursor.close()
                                Toast.makeText(
                                    context,
                                    "CNP sau parolă incorecte!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .shadow(4.dp, RoundedCornerShape(50))
                    ) {
                        Text("Autentificare", color = Color.White, fontFamily = fontFamilySegoeUI)
                    }
                }
            }
        }
    }
}
