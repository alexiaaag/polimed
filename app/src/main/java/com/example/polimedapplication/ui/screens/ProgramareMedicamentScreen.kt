package com.example.polimedapplication.ui.screens

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.polimedapplication.R
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.draw.shadow
import com.example.polimedapplication.ui.components.CustomDatePickerDialog
import com.example.polimedapplication.ui.components.CustomTimePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon

import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Warning
import kotlinx.coroutines.delay

import androidx.compose.material3.Icon
import com.example.polimedapplication.data.BazaDateAjutor
import java.text.SimpleDateFormat
import android.util.Log
import java.util.*
/*
import com.example.polimedapplication.ui.components.CustomDatePickerDialog
*/

val AlbastruPoliMed = Color(0xFF03A9F4)
data class ZiPersonalizata(
    var numarAdministrari: Int = 0,
    var ore: MutableList<String> = mutableListOf()
)

@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("RememberReturnType", "LocalContextConfigurationRead", "DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgramareMedicamentScreen(navController: NavController, idUtilizator: String) {
    val context = LocalContext.current
    SideEffect {
        val locale = Locale("ro", "RO")
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
    }
    val fontSegoeUI = FontFamily(Font(R.font.segoeui))

    var cineIaMedicamentul by remember { mutableStateOf("Eu") }
    var cnpPacient by remember { mutableStateOf("") }
    var aliasActiv by remember { mutableStateOf(false) }
    var aliasText by remember { mutableStateOf("") }

    var numeMedicament by remember { mutableStateOf("") }
    var doza by remember { mutableStateOf("") }
    var unitate by remember { mutableStateOf("") }
    var instructiuni by remember { mutableStateOf("") }
    var frecventa by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val showDatePickerStart = remember { mutableStateOf(false) }
    val showDatePickerEnd = remember { mutableStateOf(false) }

    val dateInceput = remember { mutableStateOf("") }
    val dateFinal = remember { mutableStateOf("") }


    var oraZilnica by remember { mutableStateOf("") }
    var oreAdministrare by remember { mutableStateOf(listOf<String>()) }
    val zileSelectate = remember { mutableStateMapOf<String, ZiPersonalizata>() }
    val numarMaximAdministrari = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
            .padding(24.dp)
    ) {
        Text("Cine ia acest medicament?", fontSize = 14.sp, color = Color.Black, fontFamily = fontSegoeUI)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = cineIaMedicamentul == "Pacient",
                onClick = { cineIaMedicamentul = "Pacient" },
                colors = RadioButtonDefaults.colors(selectedColor = AlbastruPoliMed)
            )
            Text("Pacient", fontFamily = fontSegoeUI)

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = cineIaMedicamentul == "Eu",
                onClick = { cineIaMedicamentul = "Eu" },
                colors = RadioButtonDefaults.colors(selectedColor = AlbastruPoliMed)
            )
            Text("Eu", fontFamily = fontSegoeUI)
        }

        if (cineIaMedicamentul == "Pacient") {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = cnpPacient,
                onValueChange = { cnpPacient = it },
                label = { Text("CNP pacient") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AlbastruPoliMed,
                    cursorColor = AlbastruPoliMed
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = numeMedicament,
            onValueChange = { numeMedicament = it },
            label = { Text("Nume Medicament") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AlbastruPoliMed,
                cursorColor = AlbastruPoliMed
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = doza,
                onValueChange = { doza = it },
                label = { Text("Doză") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AlbastruPoliMed,
                    cursorColor = AlbastruPoliMed
                )
            )
            OutlinedTextField(
                value = unitate,
                onValueChange = { unitate = it },
                label = { Text("Unitate") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AlbastruPoliMed,
                    cursorColor = AlbastruPoliMed
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = instructiuni,
            onValueChange = { instructiuni = it },
            label = { Text("Instrucțiuni (opțional)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AlbastruPoliMed,
                cursorColor = AlbastruPoliMed
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Alias pentru notificări", modifier = Modifier.weight(1f), fontFamily = fontSegoeUI)
            Switch(
                checked = aliasActiv,
                onCheckedChange = { aliasActiv = it },
                colors = SwitchDefaults.colors(checkedThumbColor = AlbastruPoliMed,  checkedTrackColor = AlbastruPoliMed.copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color(0xFFF2F2F2))
            )
        }

        if (aliasActiv) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = aliasText,
                onValueChange = { aliasText = it },
                label = { Text("Text alias notificare") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AlbastruPoliMed,
                    cursorColor = AlbastruPoliMed
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Cât de des este administrat medicamentul?", fontFamily = fontSegoeUI)



        Spacer(modifier = Modifier.height(4.dp))

        FrecventaDropdown(
            fontSegoeUI = fontSegoeUI,
            frecventa = frecventa,
            onFrecventaSelect = { frecventa = it }
        )


        if (frecventa == "De mai multe ori pe zi") {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Perioadă tratament", fontFamily = fontSegoeUI)
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = dateInceput.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Data începerii") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showDatePickerStart.value = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AlbastruPoliMed,
                        cursorColor = AlbastruPoliMed
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = dateFinal.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Data încheierii") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showDatePickerEnd.value = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AlbastruPoliMed,
                        cursorColor = AlbastruPoliMed
                    )
                )
            }

// 📅 Calendar Compose în limba română cu accent color
            CustomDatePickerDialog(
                showDialog = showDatePickerStart.value,
                onDismiss = { showDatePickerStart.value = false },
                onDateSelected = { dateInceput.value = it },
                accentColor = AlbastruPoliMed
            )

            CustomDatePickerDialog(
                showDialog = showDatePickerEnd.value,
                onDismiss = { showDatePickerEnd.value = false },
                onDateSelected = { dateFinal.value = it },
                accentColor = AlbastruPoliMed
            )


            var nrAdministrari by remember { mutableStateOf("") }
            remember { mutableStateListOf<String>() }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Administrare", fontFamily = fontSegoeUI)
            var indexTimePickerDeschis by remember { mutableStateOf(-1) }

            OutlinedTextField(
                value = nrAdministrari,
                onValueChange = {
                    nrAdministrari = it.filter { ch -> ch.isDigit() }
                    val numar = nrAdministrari.toIntOrNull() ?: 0
                    oreAdministrare = List(numar) { "" }
                },
                label = { Text("Număr de administrări pe zi") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AlbastruPoliMed,
                    cursorColor = AlbastruPoliMed
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            val AlbastruFundal = AlbastruPoliMed.copy(alpha = 0.1f)

            oreAdministrare.forEachIndexed { index, ora ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { indexTimePickerDeschis = index },
                        colors = ButtonDefaults.buttonColors(containerColor = AlbastruPoliMed),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = "Iconiță oră",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ora administrarii numarul ${index + 1}",
                            color = Color.White,
                            maxLines = 1,
                            softWrap = false
                        )
                    }

                    AnimatedVisibility(visible = ora.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(AlbastruFundal, shape = RoundedCornerShape(8.dp))
                                .padding(vertical = 12.dp)
                                .padding(horizontal = 16.dp)
                                //.shadow(1.dp, RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = "Ora selectată: $ora",
                                color = AlbastruPoliMed,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }



            // Afișăm picker doar pentru indexul curent
            if (indexTimePickerDeschis >= 0) {
                CustomTimePickerDialog(
                    onTimeConfirmed = { hour, minute ->
                        val oraNoua = String.format("%02d:%02d", hour, minute)
                        oreAdministrare = oreAdministrare.toMutableList().also {
                            it[indexTimePickerDeschis] = oraNoua
                        }
                        Log.d("DEBUG_UI", "Ora setată la index $indexTimePickerDeschis: $oraNoua")
                        indexTimePickerDeschis = -1
                    },
                    onDismiss = { indexTimePickerDeschis = -1 },
                    albastruPoliMed = AlbastruPoliMed
                )
            }


        }

        else if (frecventa == "Zilnic") {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Perioadă tratament", fontFamily = fontSegoeUI)
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = dateInceput.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Data începerii") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showDatePickerStart.value = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AlbastruPoliMed,
                        cursorColor = AlbastruPoliMed
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = dateFinal.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Data încheierii") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showDatePickerEnd.value = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AlbastruPoliMed,
                        cursorColor = AlbastruPoliMed
                    )
                )
            }

            // Calendar (folosești deja componentele tale)
            CustomDatePickerDialog(
                showDialog = showDatePickerStart.value,
                onDismiss = { showDatePickerStart.value = false },
                onDateSelected = { dateInceput.value = it },
                accentColor = AlbastruPoliMed
            )

            CustomDatePickerDialog(
                showDialog = showDatePickerEnd.value,
                onDismiss = { showDatePickerEnd.value = false },
                onDateSelected = { dateFinal.value = it },
                accentColor = AlbastruPoliMed
            )

            // 👇 Selectarea unei singure ore
            var pickerZilnicDeschis by remember { mutableStateOf(false) }
            val AlbastruFundal = AlbastruPoliMed.copy(alpha = 0.1f)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Ora administrare zilnică", fontFamily = fontSegoeUI)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { pickerZilnicDeschis = true },
                    colors = ButtonDefaults.buttonColors(containerColor = AlbastruPoliMed),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Icon oră",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Selectează ora",
                        color = Color.White,
                        maxLines = 1,
                        softWrap = false
                    )
                }

                AnimatedVisibility(visible = oraZilnica.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(AlbastruFundal, shape = RoundedCornerShape(8.dp))
                            .padding(vertical = 12.dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Ora selectată: $oraZilnica",
                            color = AlbastruPoliMed,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            if (pickerZilnicDeschis) {
                CustomTimePickerDialog(
                    onTimeConfirmed = { hour, minute ->
                        oraZilnica = String.format("%02d:%02d", hour, minute)
                        pickerZilnicDeschis = false
                    },
                    onDismiss = { pickerZilnicDeschis = false },
                    albastruPoliMed = AlbastruPoliMed
                )
            }
        }
        else if (frecventa == "Frecvență personalizată") {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Perioadă tratament", fontFamily = fontSegoeUI)

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = dateInceput.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Data începerii") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showDatePickerStart.value = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AlbastruPoliMed,
                        cursorColor = AlbastruPoliMed
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = dateFinal.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Data încheierii") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showDatePickerEnd.value = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AlbastruPoliMed,
                        cursorColor = AlbastruPoliMed
                    )
                )
            }

            CustomDatePickerDialog(
                showDialog = showDatePickerStart.value,
                onDismiss = { showDatePickerStart.value = false },
                onDateSelected = { dateInceput.value = it },
                accentColor = AlbastruPoliMed
            )

            CustomDatePickerDialog(
                showDialog = showDatePickerEnd.value,
                onDismiss = { showDatePickerEnd.value = false },
                onDateSelected = { dateFinal.value = it },
                accentColor = AlbastruPoliMed
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text("Selectează zilele de administrare:", fontFamily = fontSegoeUI)

            val zileSaptamana = listOf("Luni", "Marți", "Miercuri", "Joi", "Vineri", "Sâmbătă", "Duminică")
            val ziDeschisa = remember { mutableStateOf<Pair<String, Int>?>(null) }
            val AlbastruFundal = AlbastruPoliMed.copy(alpha = 0.1f)

            Column {
                zileSaptamana.forEach { zi ->
                    val esteSelectata = zileSelectate.containsKey(zi)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (esteSelectata) zileSelectate.remove(zi)
                                else zileSelectate[zi] = ZiPersonalizata()
                            }
                            .padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = esteSelectata,
                            onCheckedChange = {
                                if (it) zileSelectate[zi] = ZiPersonalizata()
                                else zileSelectate.remove(zi)
                            },
                            colors = CheckboxDefaults.colors(checkedColor = AlbastruPoliMed)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(zi, fontFamily = fontSegoeUI)
                    }

                    if (esteSelectata) {
                        Column(modifier = Modifier.padding(start = 32.dp)) {
                            val valoareTextNumar = zileSelectate[zi]?.numarAdministrari?.takeIf { it > 0 }?.toString() ?: ""

                            OutlinedTextField(
                                value = valoareTextNumar,
                                onValueChange = { nouText ->
                                    val numarNou = nouText.filter { it.isDigit() }.toIntOrNull() ?: 0
                                    val ziData = zileSelectate[zi] ?: ZiPersonalizata()

                                    zileSelectate[zi] = ziData.copy(
                                        numarAdministrari = numarNou,
                                        ore = MutableList(numarNou) { index ->
                                            ziData.ore.getOrNull(index) ?: ""
                                        }
                                    )
                                },
                                label = { Text("Număr administrări") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = AlbastruPoliMed,
                                    cursorColor = AlbastruPoliMed
                                )
                            )


                            Spacer(modifier = Modifier.height(8.dp))

                            zileSelectate[zi]?.ore?.forEachIndexed { index, ora ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp)
                                ) {
                                    Button(
                                        onClick = { ziDeschisa.value = zi to index },
                                        colors = ButtonDefaults.buttonColors(containerColor = AlbastruPoliMed),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp)
                                            .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
                                        shape = RoundedCornerShape(12.dp),
                                        contentPadding = PaddingValues(horizontal = 12.dp)
                                    ) {
                                        Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color.White)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Ora administrării numărul ${index + 1}", color = Color.White)
                                    }

                                    AnimatedVisibility(visible = ora.isNotEmpty()) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(AlbastruFundal, shape = RoundedCornerShape(8.dp))
                                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                        ) {
                                            Text("Ora selectată: $ora", color = AlbastruPoliMed, fontSize = 14.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            ziDeschisa.value?.let { (zi, index) ->
                CustomTimePickerDialog(
                    onTimeConfirmed = { hour, minute ->
                        val oraNoua = String.format("%02d:%02d", hour, minute)
                        zileSelectate[zi]?.ore?.set(index, oraNoua)
                        ziDeschisa.value = null
                    },
                    onDismiss = { ziDeschisa.value = null },
                    albastruPoliMed = AlbastruPoliMed
                )
            }
        }
        else if (frecventa == "La nevoie") {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Perioadă tratament", fontFamily = fontSegoeUI)

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = dateInceput.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Data începerii") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showDatePickerStart.value = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AlbastruPoliMed,
                        cursorColor = AlbastruPoliMed
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = dateFinal.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Data încheierii") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showDatePickerEnd.value = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AlbastruPoliMed,
                        cursorColor = AlbastruPoliMed
                    )
                )
            }

            CustomDatePickerDialog(
                showDialog = showDatePickerStart.value,
                onDismiss = { showDatePickerStart.value = false },
                onDateSelected = { dateInceput.value = it },
                accentColor = AlbastruPoliMed
            )

            CustomDatePickerDialog(
                showDialog = showDatePickerEnd.value,
                onDismiss = { showDatePickerEnd.value = false },
                onDateSelected = { dateFinal.value = it },
                accentColor = AlbastruPoliMed
            )

            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AlbastruPoliMed.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Acest medicament va fi administrat doar atunci când este necesar.",
                    color = AlbastruPoliMed,
                    fontSize = 14.sp,
                    fontFamily = fontSegoeUI
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Atenție",
                    tint = Color(0xFFFFC107), // Galben warning
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Atenție: specifică un număr maxim de administrări pe zi",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    fontFamily = fontSegoeUI
                )
            }

            OutlinedTextField(
                value = numarMaximAdministrari.value,
                onValueChange = { input ->
                    numarMaximAdministrari.value = input.filter { it.isDigit() }
                },
                label = { Text("Număr maxim de administrări pe zi") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AlbastruPoliMed,
                    cursorColor = AlbastruPoliMed
                )
            )

        }



        Spacer(modifier = Modifier.height(24.dp))
        remember { mutableStateOf(false) }
        remember { mutableStateOf(false) }
        val isSaving = remember { mutableStateOf(false) }
        val isSaved = remember { mutableStateOf(false) }

// 👉 efectul care rulează când isSaving devine true
        LaunchedEffect(isSaving.value) {
            if (isSaving.value) {
                // Simulezi salvarea (sau o înlocuiești cu logica reală)
                delay(1500)
                isSaving.value = false
                isSaved.value = true
                delay(2000)
                isSaved.value = false
            }
        }

        Button(
            onClick = {
                val numarMaximAdministrariFinal = if (frecventa == "La nevoie") {
                    numarMaximAdministrari.value.toIntOrNull()
                } else null

                isSaving.value = true
                isSaved.value = false
                Log.d("DEBUG_DB", "Ore trimise: $oreAdministrare")
                Log.d("DEBUG_DB", "Zile personalizate: $zileSelectate")
                Log.d("DEBUG_UI", "Ore colectate pentru salvare: $oreAdministrare") // 🔍 AICI VEZI LISTA FINALĂ
                val listaOreFinala = when (frecventa) {
                    "Zilnic" -> listOf(oraZilnica)
                    else -> oreAdministrare
                }
                Log.d("DEBUG_DB", "📋 zileSelectate finale: $zileSelectate")
                Log.d("DEBUG_DB", "🎯 ore extrase: ${zileSelectate.mapValues { it.value.ore }}")
                val zilePersonalizateFinal = zileSelectate.map { (zi, ziData) ->
                    zi to ziData.ore.filter { it.isNotEmpty() }
                }.toMap()

                Log.d("DEBUG_UI", "📋 zileSelectate finale: $zileSelectate")
                Log.d("DEBUG_UI", "🎯 zilePersonalizateFinal pentru salvare: $zilePersonalizateFinal")

                salveazaMedicamentSiAdministrari(
                    context = context,
                    idUtilizator = idUtilizator,
                    cnpPacient = cnpPacient,
                    numeMedicament = numeMedicament,
                    doza = doza,
                    unitate = unitate,
                    instructiuni = instructiuni,
                    frecventa = frecventa,
                    alias = if (aliasActiv) aliasText else null,
                    dataInceput = dateInceput.value,
                    dataSfarsit = dateFinal.value,
                    ore = listaOreFinala,
                    zilePersonalizate = zilePersonalizateFinal,
                    numarMaximPeZi = numarMaximAdministrariFinal
                )



            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .shadow(6.dp, shape = RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AlbastruPoliMed),
            contentPadding = PaddingValues(horizontal = 20.dp),
            enabled = !isSaving.value
        ) {
            when {
                isSaving.value -> {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Se salvează...", color = Color.White)
                }

                isSaved.value -> {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Salvat",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Salvat!", color = Color.White)
                }

                else -> {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Salvează",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Salvează", color = Color.White)
                }
            }
        }






    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrecventaDropdown(
    fontSegoeUI: FontFamily,
    frecventa: String,
    onFrecventaSelect: (String) -> Unit
) {
    val optiuniFrecventa = listOf(
        "De mai multe ori pe zi",
        "Zilnic",
        "Frecvență personalizată",
        "La nevoie"
    )

    var expanded by remember { mutableStateOf(false) }



    // 🔽 UI-ul
    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = frecventa,
                onValueChange = {},
                readOnly = true,
                label = { Text("Frecvență", fontFamily = fontSegoeUI) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AlbastruPoliMed,
                    cursorColor = AlbastruPoliMed
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color(0xFFF2F2F2))
            ) {
                optiuniFrecventa.forEach { optiune ->
                    DropdownMenuItem(
                        text = { Text(optiune, fontFamily = fontSegoeUI) },
                        onClick = {
                            onFrecventaSelect(optiune)
                            expanded = false
                        }
                    )
                }
            }
        }

    }
}
fun getIdMedic(context: Context, idUtilizator: String): Int? {
    val db = BazaDateAjutor(context).readableDatabase
    val cursor = db.rawQuery(
        "SELECT idMedic FROM Medici WHERE idUtilizator = ?",
        arrayOf(idUtilizator)
    )

    var idMedic: Int? = null
    if (cursor.moveToFirst()) {
        idMedic = cursor.getInt(cursor.getColumnIndexOrThrow("idMedic"))
    }

    cursor.close()
    db.close()
    return idMedic
}

fun salveazaMedicamentSiAdministrari(
    context: Context,
    idUtilizator: String,
    cnpPacient: String,
    numeMedicament: String,
    doza: String,
    unitate: String,
    instructiuni: String,
    frecventa: String,
    alias: String?,
    dataInceput: String,
    dataSfarsit: String,
    ore: List<String>,
    zilePersonalizate: Map<String, List<String>> = emptyMap(),
    numarMaximPeZi: Int? = null
) {
    Log.d("DEBUG_DB", "Ore primite pentru inserare: $ore")

    val db = BazaDateAjutor(context).writableDatabase

    // 🔍 Obține id-ul medicului
    val idMedic = getIdMedic(context, idUtilizator)

    // === 1. Salvăm în tabelul Medicamente ===
    val values = ContentValues().apply {
        put("cnpPacient", cnpPacient)
        put("idMedic", idMedic)
        put("numeMedicament", numeMedicament)
        put("doza", doza.toDoubleOrNull())
        put("unitate", unitate)
        put("frecventa", frecventa)
        put("alias", alias)
        put("instructiuni", instructiuni)
        put("dataIncepere", dataInceput)
        put("dataIncheiere", dataSfarsit)
        put("numarMaximAdministrari", numarMaximPeZi)
    }

    val idMedicament = db.insert("Medicamente", null, values)
    if (idMedicament == -1L) {
        Log.e("DEBUG_DB", "❌ Inserare în tabelul Medicamente a eșuat")
    }
    // === 2. Generăm administrările ===
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val cal = Calendar.getInstance()

    try {
        val start = format.parse(dataInceput)!!
        val end = format.parse(dataSfarsit)!!
        Log.d("DEBUG_DB", "🧪 Data start brut: $dataInceput | Data end brut: $dataSfarsit")
        Log.d("DEBUG_DB", "🧪 Parsed start = $start | Parsed end = $end")
        Log.d("DEBUG_DB", "🧠 Start parsed = $start | End parsed = $end")
        cal.time = start

        while (!cal.time.after(end)) {
            Log.d("DEBUG_DB", "⏳ Începem generarea administrărilor: $dataInceput - $dataSfarsit")

            val dataStr = format.format(cal.time)
            val ziua = normalizeZiua(
                SimpleDateFormat("EEEE", Locale("ro")).format(cal.time)
            )
                .replaceFirstChar { it.uppercase() }
            Log.d("DEBUG_DB", "📅 ziua actuală: $ziua | Chei disponibile: ${zilePersonalizate.keys}")
            Log.d("DEBUG_DB", "➡️ nu am intrat in loop inca cu $frecventa" )

            when (frecventa) {
                "Zilnic", "De mai multe ori pe zi" -> {
                    ore.forEach { ora ->
                        inserareAdministrare(db, idMedicament, dataStr, ora, ziua)
                    }
                }

                "Frecvență personalizată" -> {
                    val oreZi = zilePersonalizate[ziua]
                    oreZi?.forEach { ora ->
                        inserareAdministrare(db, idMedicament, dataStr, ora, ziua)
                    }
                }

                "La nevoie" -> {
                    // 💡 Nu avem ora, doar ziua și data
                    inserareAdministrare(db, idMedicament, dataStr, null.toString(), ziua)
                    Log.d("DEBUG_DB", "🆘 Inserare la nevoie: $dataStr fără oră ($ziua)")
                }
            }


            cal.add(Calendar.DAY_OF_MONTH, 1)
        }
    } catch (e: Exception) {
        Log.e("DEBUG_DB", "❌ EROARE PARSARE: ${e.message}")
        e.printStackTrace()
    }

    db.close()
}

fun inserareAdministrare(
    db: SQLiteDatabase,
    idMedicament: Long,
    data: String,
    ora: String?, // <-- poate fi null
    ziSaptamana: String
) {
    val valori = ContentValues().apply {
        put("idMedicament", idMedicament)
        put("dataAdministrare", data)
        put("ziSaptamana", ziSaptamana)
        if (!ora.isNullOrEmpty()) {
            put("oraAdministrare", ora)
        }
    }

    val result = db.insert("AdministrariProgramate", null, valori)

    if (result == -1L) {
        Log.e("DEBUG_DB", "❌ EROARE la inserare administrare: $data - ${ora ?: "fără oră"}")
    } else {
        Log.d("DEBUG_DB", "✅ Inserare reușită: $data - ${ora ?: "fără oră"} (ID = $result)")
    }
}


fun normalizeZiua(zi: String): String {
    return zi.lowercase()
        .replace("ă", "a")
        .replace("â", "a")
        .replace("î", "i")
        .replace("ș", "s")
        .replace("ş", "s")
        .replace("ț", "t")
        .replace("ţ", "t")
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}
