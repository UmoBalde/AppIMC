package com.example.appimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.appimc.ui.theme.AppIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppIMCTheme {
                IMCScreen()

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
//Criar a interface IMCScreen
@Composable
fun IMCScreen() {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }


    Box (
        modifier = Modifier.fillMaxSize()
    ){
        //Imagem de fundo
        Image(
            painter = painterResource(id = R.drawable.fundo),
            contentDescription = "Fundo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop

        )
    }
    //Organiza os elementos verticalmemte
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)

    ){
        //Exibe um texto na tela
        Text(text = "Calculadora de IMC", style
        = MaterialTheme
            .typography.headlineMedium
        )

        //Saídas para o utilizador inserir peso
        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType
            = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(24.dp),
            colors = with(TextFieldDefaults) {
                outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1976D2), // azul forte
                        unfocusedBorderColor = Color.LightGray

                    )
            }

        )

        //Saídas para o utilizador inserir altura
        OutlinedTextField(
            value = altura,
            onValueChange = { altura = it },
            label = { Text("Altura (m)") },
            keyboardOptions = KeyboardOptions(keyboardType
            = KeyboardType.Number ),
            singleLine = true,
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(24.dp),
            colors = with(TextFieldDefaults) {
                outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF1976D2), // azul forte
                    unfocusedBorderColor = Color.LightGray

                )
            }


        )

    }

}




