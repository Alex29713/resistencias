@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.resistencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.resistencias.ui.theme.ResistenciasTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResistenciasTheme {
                DropDown()
            }
        }
    }
}

@Composable
fun DropDown() {
    val Orange = Color(0xFFF83C00)   // Hexadecimal para color naranja
    val Purple = Color(0xFFA80BE1)   // Hexadecimal para color púrpura
    val Brown = Color(0xFF6F4E37)    // Hexadecimal para color marrón

    val listBanda1 = listOf(
        "Negro 0", "Marrón 1", "Rojo 2", "Naranja 3", "Amarillo 4",
        "Verde 5", "Azul 6", "Violeta 7", "Gris 8", "Blanco 9"
    )

    val bandaColores = listBanda1.associate {
        val (color, valor) = it.split(" ")
        color to valor.toInt()
    }
    val listMultiplicador = listOf("x1", "x10", "x100", "x1000", "x10,000", "x100,000", "x1,000,000", "x10,000,000", "x100,000,000", "x1000,000,000","x0.1", "0.01")

    val multiplicadorValores = listMultiplicador.associate { multiplicador ->
        val valor = multiplicador.replace(",", "").replace("x", "") // Eliminamos las comas y la "x"
        multiplicador to valor.toDouble() // Convertimos a Double
    }

    val listBandaTolerancia = listOf("± 1%","± 2%","± 0.5%","± 0.25%","± 0.10%","± 0.05%","± 5%","± 10%","± 20%")

    val listBanda1Colors = listOf(
        Color.Black, Brown, Color.Red, Orange, Color.Yellow,
        Color.Green, Color.Blue, Purple, Color.Gray, Color.White
    )

    val additionalColors = listOf(Color(0xFFFFD700), Color(0xFFC0C0C0))
    val listMultiplicadorColor = listBanda1Colors.toList() + additionalColors
    val listBandaToleranciaColor = listOf(
        Color.Black, Color(0xFF6F4E37), Color.Green, Color.Blue, Color.Magenta, Color.Gray,Color(0xFFFFD700), Color(0xFFC0C0C0),Color.Transparent
    )

    var selectedBanda1 by remember { mutableStateOf(listBanda1[0]) }
    var selectedBanda2 by remember { mutableStateOf(listBanda1[0]) }
    var selectedText by remember { mutableStateOf(listMultiplicador[0]) }
    var selectedText2 by remember { mutableStateOf(listBandaTolerancia[0]) }

    var selectedColor by remember { mutableStateOf(listBanda1Colors[0]) }
    var selectedColor2 by remember { mutableStateOf(listBanda1Colors[0]) }
    var selectedColor3 by remember { mutableStateOf(listMultiplicadorColor[0]) }
    var selectedColor4 by remember { mutableStateOf(listBandaToleranciaColor[0]) }

    var isExpanded1 by remember { mutableStateOf(false) }
    var isExpanded2 by remember { mutableStateOf(false) }
    var isExpanded3 by remember { mutableStateOf(false) }
    var isExpanded4 by remember { mutableStateOf(false) }

    // Lógica de cálculo de la resistencia
    val valorBanda1 = bandaColores[selectedBanda1.split(" ")[0]] ?: 0
    val valorBanda2 = bandaColores[selectedBanda2.split(" ")[0]] ?: 0
    val valorMultiplicador = multiplicadorValores[selectedText] ?: 1.0

    val valorResistencia = (valorBanda1 * 10 + valorBanda2) * valorMultiplicador
    val valorTolerancia = selectedText2

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "Calculadora de Resistencias",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        // Primera lista desplegable centrada con marco ajustado
        ExposedDropdownMenuBox(
            expanded = isExpanded1,
            onExpandedChange = { isExpanded1 = !isExpanded1 },
            modifier = Modifier
                .widthIn(min = 150.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .background(selectedColor, shape = RoundedCornerShape(16.dp)),
                value = selectedBanda1,
                onValueChange = {},
                readOnly = true,
                label = { Text("Selecciona Banda 1",
                    color = if (selectedColor == Color.Black) Color.White else Color.Black)},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded1) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = selectedColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                expanded = isExpanded1,
                onDismissRequest = { isExpanded1 = false }
            ) {
                listBanda1.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(listBanda1Colors[index])
                            .padding(vertical = 8.dp),
                        text = {
                            Text(
                                text = text,
                                color = if (listBanda1Colors[index] == Color.Black) Color.White else Color.Black,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        onClick = {
                            selectedBanda1 = listBanda1[index]
                            selectedColor = listBanda1Colors[index]
                            isExpanded1 = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Segunda lista centrada
        ExposedDropdownMenuBox(
            expanded = isExpanded2,
            onExpandedChange = { isExpanded2 = !isExpanded2 },
            modifier = Modifier
                .widthIn(min = 150.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .background(selectedColor2, shape = RoundedCornerShape(16.dp)),
                value = selectedBanda2,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Selecciona Banda 2" ,
                        color = if (selectedColor2 == Color.Black) Color.White else Color.Black) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded2) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = selectedColor2,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                expanded = isExpanded2,
                onDismissRequest = { isExpanded2 = false }
            ) {
                listBanda1.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(listBanda1Colors[index])
                            .padding(vertical = 8.dp),
                        text = {
                            Text(
                                text = text,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        onClick = {
                            selectedBanda2 = listBanda1[index]
                            selectedColor2 = listBanda1Colors[index]
                            isExpanded2 = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista para el multiplicador centrada
        ExposedDropdownMenuBox(
            expanded = isExpanded3,
            onExpandedChange = { isExpanded3 = !isExpanded3 },
            modifier = Modifier
                .widthIn(min = 150.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .background(selectedColor3, shape = RoundedCornerShape(16.dp)),
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                label = { Text("Selecciona Multiplicador",
                    color = if (selectedColor3 == Color.Black) Color.White else Color.Black) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded3) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = selectedColor3,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                expanded = isExpanded3,
                onDismissRequest = { isExpanded3 = false }
            ) {
                listMultiplicador.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(listMultiplicadorColor[index])
                            .padding(vertical = 8.dp),
                        text = {
                            Text(
                                text = text,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        onClick = {
                            selectedText = listMultiplicador[index]
                            selectedColor3 = listMultiplicadorColor[index]
                            isExpanded3 = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista para tolerancia centrada
        ExposedDropdownMenuBox(
            expanded = isExpanded4,
            onExpandedChange = { isExpanded4 = !isExpanded4 },
            modifier = Modifier
                .widthIn(min = 150.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .background(selectedColor4, shape = RoundedCornerShape(16.dp)),
                value = selectedText2,
                onValueChange = {},
                readOnly = true,
                label = { Text("Selecciona Tolerancia",
                    color = if (selectedColor4 == Color.Black) Color.White else Color.Black) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded4) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = selectedColor4,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                expanded = isExpanded4,
                onDismissRequest = { isExpanded4 = false }
            ) {
                listBandaTolerancia.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(listBandaToleranciaColor[index])
                            .padding(vertical = 8.dp),
                        text = {
                            Text(
                                text = text,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        onClick = {
                            selectedText2 = listBandaTolerancia[index]
                            selectedColor4 = listBandaToleranciaColor[index]
                            isExpanded4 = false
                        }
                    )
                }
            }
        }

        // Mostrar el resultado del cálculo
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Valor de la Resistencia: ${valorResistencia}Ω $valorTolerancia",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}





@Preview(showBackground = true)
@Composable
fun PreviewDropDown() {
    ResistenciasTheme {
        DropDown()
    }
}