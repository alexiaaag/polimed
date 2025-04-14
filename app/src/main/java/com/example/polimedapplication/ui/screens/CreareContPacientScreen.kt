package com.example.polimedapplication.ui.screens

import android.content.ContentValues
import android.os.Handler
import android.os.Looper
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
import androidx.lint.kotlin.metadata.Visibility
import androidx.navigation.NavController
import com.example.polimedapplication.R
import com.example.polimedapplication.data.BazaDateAjutor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val fontFamilySegoeUI = FontFamily(Font(R.font.segoeui))

@Composable
fun CreareContPacientScreen(navController: NavController) {
    val context = LocalContext.current
    val dbHelper = remember { BazaDateAjutor(context) }
    val db = dbHelper.writableDatabase

    var nume by remember { mutableStateOf("") }
    var prenume by remember { mutableStateOf("") }
    var cnp by remember { mutableStateOf("") }
    var parola by remember { mutableStateOf("") }
    var telefon by remember { mutableStateOf("") }
    var adresa by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Image(
            painter = painterResource(id = R.drawable.elipse),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(4200.dp).offset(y = (-100).dp).align(Alignment.TopCenter)
        )
        Image(
            painter = painterResource(id = R.drawable.back_white),
            contentDescription = "Back",
            modifier = Modifier.size(50.dp).align(Alignment.TopStart).padding(start = 5.dp, top = 32.dp)
                .clickable {
                    navController.navigate("selectare_rol") {
                        popUpTo("creare_cont_pacient") { inclusive = true }
                    }
                }
        )
        Image(
            painter = painterResource(id = R.drawable.capsule1),
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomStart).offset(x = 16.dp, y = (-16).dp).size(74.dp)
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 70.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.width(320.dp).shadow(8.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomInputField(nume, { nume = it }, "Nume")
                    CustomInputField(prenume, { prenume = it }, "Prenume")
                    CustomInputField(cnp, { cnp = it }, "CNP", isNumber = true)
                    CustomInputField(
                        parola, { parola = it }, "Parolă",
                        isPassword = true, showPassword = showPassword,
                        onTogglePassword = { showPassword = !showPassword }
                    )
                    CustomInputField(telefon, { telefon = it }, "Telefon", isNumber = true)
                    CustomInputField(adresa, { adresa = it }, "Adresă")

                    Spacer(modifier = Modifier.height(3.dp))

                    Button(
                        onClick = {
                            if (!nume.matches(Regex("^[A-Za-zăîâșțĂÎÂȘȚ ]+$"))) {
                                Toast.makeText(context, "Numele nu trebuie să conțină cifre sau caractere speciale.", Toast.LENGTH_SHORT).show(); return@Button
                            }
                            if (!prenume.matches(Regex("^[A-Za-zăîâșțĂÎÂȘȚ ]+$"))) {
                                Toast.makeText(context, "Prenumele nu trebuie să conțină cifre sau caractere speciale.", Toast.LENGTH_SHORT).show(); return@Button
                            }
                            if (!cnp.matches(Regex("^\\d{13}$"))) {
                                Toast.makeText(context, "CNP trebuie să conțină exact 13 cifre.", Toast.LENGTH_SHORT).show(); return@Button
                            }
                            if (!telefon.matches(Regex("^\\d{10,15}$"))) {
                                Toast.makeText(context, "Telefonul trebuie să conțină doar cifre.", Toast.LENGTH_SHORT).show(); return@Button
                            }

                            val cursor = db.rawQuery("SELECT COUNT(*) FROM Utilizatori WHERE rol = 'pacient'", null)
                            cursor.moveToFirst()
                            val count = cursor.getInt(0) + 1
                            val idUtilizator = "P" + count.toString().padStart(3, '0')
                            cursor.close()

                            val valoriUtilizator = ContentValues().apply {
                                put("idUtilizator", idUtilizator)
                                put("cnp", cnp)
                                put("parola", parola)
                                put("rol", "pacient")
                                put("aprobat", 0)
                            }

                            val utilizatorResult = db.insert("Utilizatori", null, valoriUtilizator)
                            if (utilizatorResult != -1L) {
                                val valoriPacient = ContentValues().apply {
                                    put("nume", nume)
                                    put("prenume", prenume)
                                    put("telefon", telefon)
                                    put("adresa", adresa)
                                    put("idUtilizator", idUtilizator)
                                }
                                val pacientResult = db.insert("Pacienti", null, valoriPacient)

                                if (pacientResult != -1L) {
                                    Toast.makeText(context, "Cont creat cu succes!", Toast.LENGTH_SHORT).show()
                                    scope.launch {
                                        delay(2000)
                                        navController.navigate("asteptare_validare/$idUtilizator")
                                    }
                                } else {
                                    Toast.makeText(context, "Eroare la salvarea pacientului!", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Eroare la salvarea utilizatorului!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)),
                        modifier = Modifier.fillMaxWidth().height(44.dp).shadow(4.dp, RoundedCornerShape(50))
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
    isNumber: Boolean = false,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onTogglePassword: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                Text(
                    text = hint,
                    color = Color(0xFFADAFB9),
                    fontSize = 9.sp,
                    fontFamily = fontFamilySegoeUI,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        },
        textStyle = TextStyle(fontSize = 12.sp, color = Color.Black, fontFamily = fontFamilySegoeUI),
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        modifier = Modifier.width(450.dp).height(50.dp),
        visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (isPassword && onTogglePassword != null) {
            {
                IconButton(onClick = onTogglePassword,modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle Password",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        } else null,
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
