package com.example.appimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.example.appimc.ui.theme.AppIMCTheme

//Classe principal do aplicativo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //Tema da aplicação
            AppIMCTheme {
                val navController = rememberNavController()

                // Estados para passar dados entre telas
                var nome by remember { mutableStateOf("") }
                var imc by remember { mutableStateOf(0f) }
                var categoria by remember { mutableStateOf("") }
                var imagem by remember { mutableStateOf(0) }
                var cor by remember { mutableStateOf(Color.White) }

                //Adicionar Imagem de fundo
                Box (
                    modifier = Modifier.fillMaxSize()
                ){
                    Image(
                        painter = painterResource(id = R.drawable.fundo),
                        contentDescription = "imagem de fundo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                //Navegação entre telas
                NavHost(navController = navController, startDestination = "imc") {
                    composable("imc") {

                        //Tela principal para inserir dados e calcular Índice Massa Corporal
                        IMCScreen { n, valorImc, texto, imagemRes, corResultado ->

                            //Salva dos dados calculados
                            nome = n
                            imc = valorImc
                            categoria = texto
                            imagem = imagemRes
                            cor = corResultado

                            //Navega para tela de resultado
                            navController.navigate("resultado")
                        }
                    }
                    composable("resultado") {
                        //Tela que exibe o resultado do IMC
                        ResultadoScreen(
                            nome = nome,
                            imc = imc,
                            categoria = categoria,
                            imagemResId = imagem,
                            cor = cor,
                            onVoltar = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResultadoScreen(
    nome: String,
    imc: Float,
    categoria: String,
    imagemResId: Int,
    cor: Color,
    onVoltar: () -> Unit
) {
    //Layout centralizado para exbir os dados
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Olá, $nome!",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Seu IMC é: %.2f".format(imc),
            style = MaterialTheme.typography.headlineMedium,
            color = cor
        )

        Text(
            text = "Categoria: $categoria",
            style = MaterialTheme.typography.bodyLarge,
            color = cor
        )

        Spacer(modifier = Modifier.height(24.dp))

        //Imagem de acordo com a categoria do IMC
        Image(
            painter = painterResource(id = imagemResId),
            contentDescription = "Imagem IMC",
            modifier = Modifier
                .width(150.dp)
                .height(150.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(32.dp))

        //Botão para voltar à tela anterior
        Button(onClick = onVoltar) {
            Text("Voltar")
        }
    }
}


@Composable
fun IMCScreen(onResultado: (String, Float, String, Int, Color) -> Unit) {
    var nome by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Calculadora de IMC", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        // Campo para o nome do utilizador
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            keyboardOptions = KeyboardOptions
                (keyboardType = KeyboardType.Text),
            modifier = Modifier.width(250.dp)

        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Peso
        OutlinedTextField(
            value = peso,
            onValueChange = { peso = it },
            label = { Text("Peso (kg)") },
            keyboardOptions = KeyboardOptions
                (keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier.width(250.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Altura
        OutlinedTextField(
            value = altura,
            onValueChange = { altura = it },
            label = { Text("Altura (m)") },
            keyboardOptions = KeyboardOptions
                (keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier.width(250.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botão para calcular o IMC
        Button(onClick = {
            val pesoNum = peso.toFloatOrNull()
            val alturaNum = altura.toFloatOrNull()

            if (pesoNum != null && alturaNum != null && alturaNum > 0f && nome.isNotBlank()) {
                val imc = pesoNum / (alturaNum * alturaNum)
                val categoria: String
                val cor: Color
                val imagem: Int

                //Determinar a categoria com base no valor do IMC

                when {
                    imc < 18.5 -> {
                        categoria = "Abaixo do peso"
                        cor = Color(0xFF2196F3) //Azul
                        imagem = R.drawable.abaixo_peso
                    }
                    imc < 25 -> {
                        categoria = "Peso normal"
                        cor = Color(0xFF4CAF50) //Verde
                        imagem = R.drawable.peso_normal
                    }
                    imc < 30 -> {
                        categoria = "Sobrepeso"
                        cor = Color(0xFFFFC107) //Amarelo
                        imagem = R.drawable.sobrepeso
                    }
                    else -> {
                        categoria = "Obesidade"
                        cor = Color(0xFFE53935) //Vermelho
                        imagem = R.drawable.obesidade1
                    }
                }

                onResultado(nome, imc, categoria, imagem, cor)
            } else {
                erro = "Por favor, preencha todos os campos corretamente."
            }
        }) {
            Text("Calcular")
        }

        Spacer(modifier = Modifier.height(8.dp))

        //Exibe mensagem de erro, se houver
        if (erro.isNotBlank()) {
            Text(text = erro, color = Color.Red)
        }
    }
}
