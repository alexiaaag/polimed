package com.apppolimedicatie.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.polimedapplication.R

@Composable
fun PaginaStart(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable {
                navController.navigate("selectare_rol") {
                    popUpTo("splash") { inclusive = true }
                }
            }
    ) {
        // Elipsa fundal
        Image(
            painter = painterResource(id = R.drawable.elipse),
            contentDescription = "Elipsă decorativă",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(4200.dp)
                .offset(y = (-100).dp)
                .align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo PoliMed
            Image(
                painter = painterResource(id = R.drawable.polimed),
                contentDescription = "Logo PoliMed",
                modifier = Modifier
                    .size(180.dp)
                    .offset(y = (-70).dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Imagine docthome
            Image(
                painter = painterResource(id = R.drawable.docthome),
                contentDescription = "Doctor Home",
                modifier = Modifier
                    .size(270.dp)
                    .offset(y = (-85).dp)
            )
        }
    }
}
