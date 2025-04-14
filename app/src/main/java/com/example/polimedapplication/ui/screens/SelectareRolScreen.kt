package com.apppolimedicatie.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.polimedapplication.R

val fontFamilySegoeUI = FontFamily(Font(R.font.segoeui))

@Composable
fun SelectareRolScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Fundalul elipsă
        Image(
            painter = painterResource(id = R.drawable.elipse),
            contentDescription = "Fundal elipsă",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(4200.dp)
                .offset(y = (-100).dp)
                .align(Alignment.TopCenter)
        )

        // Capsula decorativă
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
                .padding(bottom = 90.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card principal
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)) // GRI DESCHIS
            ) {
                // Text albastru deasupra
                Text(
                    text = "Înregistrează-te ca:",
                    color = Color(0xFF03A9F4),
                    fontSize = 14.sp,
                    fontFamily = fontFamilySegoeUI,
                    modifier = Modifier
                        .padding(top = 24.dp, bottom = 3.dp)
                        .align(Alignment.CenterHorizontally)
                )

                // Butoane roluri
                Row(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Buton Pacient
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .size(120.dp)
                            .clickable { navController.navigate("creare_cont_pacient") }
                            .background(Color.White, shape = RoundedCornerShape(20.dp))
                            .padding(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pacient), // XML vector asset
                            contentDescription = "Pacient",
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Pacient",
                            color = Color.DarkGray,
                            fontFamily = fontFamilySegoeUI,
                            fontSize = 14.sp
                        )
                    }

                    // Buton Medic
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .size(120.dp)
                            .clickable { navController.navigate("creare_cont_medic") }
                            .background(Color.White, shape = RoundedCornerShape(20.dp))
                            .padding(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.medic), // XML vector asset
                            contentDescription = "Medic",
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Medic",
                            color = Color.DarkGray,
                            fontFamily = fontFamilySegoeUI,
                            fontSize = 14.sp
                        )
                    }
                }

                // Buton text "M-am înregistrat deja"
                Box(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(Color(0xFF03A9F4), RoundedCornerShape(50))
                        .clickable { navController.navigate("login") }
                        .padding(horizontal = 35.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = "M-am înregistrat deja.",
                        color = Color.White,
                        fontFamily = fontFamilySegoeUI,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
