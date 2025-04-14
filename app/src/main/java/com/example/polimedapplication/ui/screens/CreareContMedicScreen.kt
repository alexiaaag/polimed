package com.example.polimedapplication.ui.screens

import android.content.ContentValues
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.polimedapplication.R
import com.example.polimedapplication.data.BazaDateAjutor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun CreareContMedicScreen(navController: NavController) {
    val context = LocalContext.current
    val dbHelper = remember { BazaDateAjutor(context) }
    val db = dbHelper.writableDatabase

    var nume by remember { mutableStateOf("") }
    var prenume by remember { mutableStateOf("") }
    var cnp by remember { mutableStateOf("") }
    var parola by remember { mutableStateOf("") }
    var telefon by remember { mutableStateOf("") }
    var specializare by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

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
                        popUpTo("creare_cont_medic") { inclusive = true }
                    }
                }
        )

        Image(
            painter = painterResource(id = R.drawable.capsule1),
            contentDescription = "capsulă",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 16.dp, y = (-16).dp)
                .size(74.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp),
            verticalArrangement = Arrangement.Top,
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
                    CustomInputField(nume, { nume = it }, "Nume")
                    CustomInputField(prenume, { prenume = it }, "Prenume")
                    CustomInputField(cnp, { cnp = it }, "CNP")
                    CustomInputField(parola, { parola = it }, "Parolă", isPassword = true)
                    CustomInputField(telefon, { telefon = it }, "Telefon")
                    CustomInputField(specializare, { specializare = it }, "Specializare (cod)")

                    Button(
                        onClick = {
                            val valid = when {
                                !nume.matches(Regex("^[a-zA-ZăîâșțĂÎÂȘȚ ]+$")) ->
                                    "Numele trebuie să conțină doar litere."
                                !prenume.matches(Regex("^[a-zA-ZăîâșțĂÎÂȘȚ ]+$")) ->
                                    "Prenumele trebuie să conțină doar litere."
                                !cnp.matches(Regex("^\\d{13}$")) ->
                                    "CNP trebuie să conțină exact 13 cifre."
                                parola.length < 4 ->
                                    "Parola este prea scurtă. Minim 4 caractere."
                                !telefon.matches(Regex("^\\d{10,15}$")) ->
                                    "Telefonul trebuie să aibă 10-15 cifre."
                                !specializare.matches(Regex("^\\d+$")) ->
                                    "Specializarea trebuie să conțină doar cifre."
                                else -> null
                            }

                            if (valid != null) {
                                Toast.makeText(context, valid, Toast.LENGTH_LONG).show()
                                return@Button
                            }

                            val cursor = db.rawQuery("SELECT COUNT(*) FROM Utilizatori WHERE rol = 'medic'", null)
                            cursor.moveToFirst()
                            val count = cursor.getInt(0) + 1
                            val idUtilizator = "M" + count.toString().padStart(3, '0')
                            cursor.close()

                            val valoriUtilizator = ContentValues().apply {
                                put("idUtilizator", idUtilizator)
                                put("cnp", cnp)
                                put("parola", parola)
                                put("rol", "medic")
                                put("aprobat", 0)
                            }

                            val utilizatorResult = db.insert("Utilizatori", null, valoriUtilizator)

                            if (utilizatorResult != -1L) {
                                val valoriMedic = ContentValues().apply {
                                    put("nume", nume)
                                    put("prenume", prenume)
                                    put("telefon", telefon)
                                    put("specializare", specializare)
                                    put("idUtilizator", idUtilizator)
                                }

                                val medicResult = db.insert("Medici", null, valoriMedic)

                                if (medicResult != -1L) {
                                    Toast.makeText(context, "Cont medic creat cu succes!", Toast.LENGTH_SHORT).show()
                                    scope.launch {
                                        delay(2000)
                                        navController.navigate("asteptare_validare/$idUtilizator")
                                    }
                                } else {
                                    Toast.makeText(context, "Eroare la salvarea medicului!", Toast.LENGTH_LONG).show()
                                }
                            } else {
                                Toast.makeText(context, "Eroare la salvarea utilizatorului!", Toast.LENGTH_LONG).show()
                            }
                        },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .shadow(4.dp, RoundedCornerShape(50))
                    ) {
                        Text("Creează cont", color = Color.White, fontFamily = fontFamilySegoeUI)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isPassword: Boolean = false
) {
    val fontFamilySegoeUI = FontFamily(Font(R.font.segoeui))
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = hint,
                color = Color(0xFFADAFB9),
                fontSize = 9.sp,
                fontFamily = fontFamilySegoeUI
            )
        },
        textStyle = TextStyle(fontSize = 12.sp, color = Color.Black, fontFamily = fontFamilySegoeUI),

        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Vizibilitate parolă",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        },
        modifier = Modifier
            .width(450.dp)
            .height(50.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFFE5E5E5),
            focusedBorderColor = Color(0xFF03A9F4),
            cursorColor = Color(0xFF03A9F4),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White
        )
    )
}
