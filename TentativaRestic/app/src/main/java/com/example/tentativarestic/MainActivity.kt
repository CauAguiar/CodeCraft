package com.example.tentativarestic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tentativarestic.ui.theme.TentativaResticTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.toBrazilianDateFormat(
    pattern: String = "dd/MM/yyyy"
): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(
        pattern, Locale("pt-br")
    ).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(date)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TentativaResticTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    // Controlador de navegação
    val navController = rememberNavController()

    // Definir o NavHost e as rotas
    NavHost(navController = navController, startDestination = "introducao") {
        composable("introducao") {
            IntroducaoApp(onFinish = {
                navController.navigate("telaInicial")
            })
        }
        composable("telaInicial") {
            TelaInicial(
                onLoginClick = { navController.navigate("telaLogin") },
                onCadastroClick = { navController.navigate("telaCadastro") }
            )
        }
        composable("telaLogin") {
            TelaLogin(navController, onNextClick = {navController.navigate("telaPrincipal")})
        }
        composable("telaCadastro") {
            TelaCadastro(navController, onNextClick = {navController.navigate("verificacaoTelefone")})
        }
        
        composable("verificacaoTelefone") {
            TelaVerificacaoTelefone(navController, onConfirmClick = {navController.navigate("digitarSenha")})
        }
        
        composable("digitarSenha") {
            TelaDigitarSenha(navController, onConfirmClick = {navController.navigate("telaPrincipal")})
        }

        composable("telaPrincipal") {
            TelaPrincipal(navController)
        }


    }
}

data class LanguageItem(val name: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPrincipal(navController: NavHostController) {
    var searchText by remember { mutableStateOf("") }

    val languages = listOf(
        LanguageItem("Python", /*R.drawable.python*/Icons.Filled.Home),
        LanguageItem("JavaScript", /*R.drawable.javascript*/Icons.Filled.Home),
        LanguageItem("UI/UX", /*R.drawable.uiux*/Icons.Filled.Home),
        LanguageItem("Java", /*R.drawable.java*/Icons.Filled.Home),
        LanguageItem("C e C++", /*R.drawable.cpp*/Icons.Filled.Home),
        LanguageItem("Algoritmos", /*R.drawable.algorithms*/Icons.Filled.Home),
        LanguageItem("SQL", /*R.drawable.sql*/Icons.Filled.Home),
        LanguageItem("CSS", /*R.drawable.css*/Icons.Filled.Home),
        LanguageItem("C#", /*R.drawable.csharp*/Icons.Filled.Home),
        LanguageItem("Kotlin", /*R.drawable.kotlin*/Icons.Filled.Home),
        LanguageItem("Haskell", /*R.drawable.haskell*/Icons.Filled.Home),
        LanguageItem("HTML", /*R.drawable.html*/Icons.Filled.Home),
        LanguageItem("PHP", /*R.drawable.php*/Icons.Filled.Home),
        LanguageItem("Ruby", /*R.drawable.ruby*/Icons.Filled.Home),
        LanguageItem("Swift", /*R.drawable.swift*/Icons.Filled.Home),
        LanguageItem("TypeScript", /*R.drawable.typescript*/Icons.Filled.Home),
        LanguageItem("R", /*R.drawable.r*/Icons.Filled.Home),
        LanguageItem("Go", /*R.drawable.go*/Icons.Filled.Home),
        LanguageItem("Dart", /*R.drawable.dart*/Icons.Filled.Home),
        LanguageItem("Scala", /*R.drawable.scala*/Icons.Filled.Home),
        LanguageItem("Lua", /*R.drawable.lua*/Icons.Filled.Home)
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(title = { Text("Bom dia Gustavo!")}, modifier = Modifier.offset(y = 14.dp), actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Notifications, contentDescription = "Notification")
                }
            })
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp), // Para dar espaço na parte inferior, se necessário
                contentAlignment = Alignment.BottomCenter // Centraliza horizontalmente no fundo
            ) {
                    NavigationBar(
                        modifier = Modifier
                            .shadow(4.dp, shape = RoundedCornerShape(80.dp))

                            .clip(
                            RoundedCornerShape(
                                topStart = 80.dp,
                                topEnd = 80.dp,
                                bottomStart = 80.dp,
                                bottomEnd = 80.dp
                            )
                        )

                            .background(Color.Transparent)
                            .fillMaxWidth(0.9f),
                        containerColor = Color.White


                        // Definindo bordas arredondadas para o NavigationBar

                    ) {
                        NavigationBarItem(icon = {
                            Box(
                                modifier = Modifier
                                    .height(42.dp)
                                    .width(15.dp)
                                    .background(
                                        color = Color(0xFFFFA500), // Cor do círculo (laranja)
                                        shape = CircleShape // Forma circular
                                    )
                            ) {
                                Icon(
                                    Icons.Filled.Home,
                                    contentDescription = "Home",
                                    modifier = Modifier
                                        .fillMaxSize(1.0f)
                                        .scale(2.0f),// Faz o ícone ocupar todo o espaço disponível
                                )
                            }
                        },
                            selected = true, onClick = { /*TODO*/ },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White, // Cor do ícone quando selecionado
                                unselectedIconColor = Color.Gray, // Cor do ícone quando não selecionado
                                indicatorColor = Color.Transparent // Indicador circular laranja quando selecionado
                            ),
                            modifier = Modifier
                                .padding(4.dp) // Espaçamento ao redor do indicador
                                .size(56.dp) // Tamanho do círculo
                                .background(
                                    color = if (true) Color(0xFFFFA500) else Color.Transparent,
                                    shape = CircleShape
                                )
                        )
                        NavigationBarItem(icon = {
                            Icon(
                                Icons.Filled.Info,
                                contentDescription = "History"
                            )
                        },
                            selected = false, onClick = { /*TODO*/ })
                        NavigationBarItem(icon = {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search"
                            )
                        },
                            selected = false, onClick = { /*TODO*/ })
                        NavigationBarItem(icon = {
                            Icon(
                                Icons.Filled.Email,
                                contentDescription = "Chat"
                            )
                        },
                            selected = false, onClick = { /*TODO*/ })
                        NavigationBarItem(icon = {
                            Icon(
                                Icons.Filled.Person,
                                contentDescription = "Profile"
                            )
                        },
                            selected = false, onClick = { /*TODO*/ },
                            modifier = Modifier
                                .padding(4.dp) // Espaçamento ao redor do indicador
                                .size(56.dp) // Tamanho do círculo
                                .apply {
                                    if (true) { // Condição para aplicar o fundo circular apenas se selecionado
                                        background(
                                            color = Color(0xFFFFA500), // Cor de fundo laranja se selecionado
                                            shape = CircleShape // Define o formato circular
                                        )
                                    }
                                }
                        )
                    }

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Procure sua trilha",
                modifier = Modifier.padding(start = 16.dp).padding(bottom = 1.dp)
                    .padding(top = 16.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Procurar") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { /* TODO: Handle filter click */ }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Filter")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .padding(bottom = 1.dp)
                    .padding(top = 16.dp)
                    .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Linguagens",
                    fontSize = 24.sp
                )

                TextButton(
                    onClick = { /*TODO: Handle click on see all*/ },
                ) {
                    Text("Ver tudo", fontSize = 18.sp)
                }
            }
            Box(
                modifier = Modifier.fillMaxSize()
            )
            {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .zIndex(-1f)
                        .padding(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(languages) { language ->
                        LanguageCard(language)
                    }
                }
            }
        }
    }
}


@Composable
fun LanguageCard(language: LanguageItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenWidthDp.dp / 3 - 16.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { /*TODO: Handle click on language*/ },
        colors = CardDefaults.cardColors(Color.White)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Image(painter = painterResource(id = language.icon), contentDescription = language.name, modifier = Modifier.size(48.dp))
            Icon(
                imageVector = language.icon,  // Directly use the ImageVector here
                contentDescription = language.name,
                modifier = Modifier.size(48.dp)  // Resize the icon to 48dp
            )
            Text(language.name, textAlign = TextAlign.Center)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDigitarSenha(navController: NavHostController, onConfirmClick: () -> Unit) {
    // Criação dos FocusRequesters para os campos de OTP
    val (item1, item2, item3, item4, item5, item6) = FocusRequester.createRefs()
    val (item1_confirm, item2_confirm, item3_confirm, item4_confirm, item5_confirm, item6_confirm) = FocusRequester.createRefs()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}, modifier = Modifier.offset(x = 3.dp, y = 14.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.seta), // Nome da sua imagem PNG
                            contentDescription = "Seta",
                            colorFilter = ColorFilter.tint(Color.Black),
                            modifier = Modifier
                                .graphicsLayer(
                                    scaleX = -3.5f,
                                    scaleY = 3.5f
                                )
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Card(
                shape = RoundedCornerShape(16.dp), // Borda arredondada do Card
                // Sombra para o Card
                modifier = Modifier
                    .width(380.dp)
                    .height(350.dp)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.intro_image_1), // Substitua pelo ID da sua imagem
                    contentDescription = "Imagem de introdução",
                    modifier = Modifier.fillMaxSize(), // Preenche o tamanho do Car
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "DEFINIR A SENHA",
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Digite sua Senha",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp),
                textAlign = TextAlign.Center
            )

            // Primeira Row para os campos OTP
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item1)
                        .focusProperties {
                            next = item2
                            previous = item1
                        }
                        .padding(start = 10.dp)
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item2)
                        .focusProperties {
                            next = item3
                            previous = item1
                        }
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item3)
                        .focusProperties {
                            next = item4
                            previous = item2
                        }
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item4)
                        .focusProperties {
                            previous = item3
                            next = item5
                        }
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item5)
                        .focusProperties {
                            previous = item4
                            next = item6
                        }
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item6)
                        .focusProperties {
                            previous = item5
                            next = item6
                        }
                        .padding(end = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // Espaçamento entre as duas linhas

            Text(
                text = "Confirme sua Senha",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp),
                textAlign = TextAlign.Center
            )

            // Segunda Row para os campos de confirmação de OTP
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item1_confirm)
                        .focusProperties {
                            next = item2_confirm
                            previous = item1_confirm
                        }
                        .padding(start = 10.dp)
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item2_confirm)
                        .focusProperties {
                            next = item3_confirm
                            previous = item1_confirm
                        }
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item3_confirm)
                        .focusProperties {
                            next = item4_confirm
                            previous = item2_confirm
                        }
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item4_confirm)
                        .focusProperties {
                            previous = item3_confirm
                            next = item5_confirm
                        }
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item5_confirm)
                        .focusProperties {
                            previous = item4_confirm
                            next = item6_confirm
                        }
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item6_confirm)
                        .focusProperties {
                            previous = item5_confirm
                            next = item6_confirm
                        }
                        .padding(end = 10.dp)
                )
            }

            Button(
                onClick = onConfirmClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp)
                    .height(48.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF59C4FF), Color(0xFF0F59FF)),
                            start = Offset(0f, 1f),
                            end = Offset(0f, 180f) // Direção vertical
                        ),
                        shape = CircleShape // Mantém a forma circular do botão
                    )
                    .size(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            )
            {
                Text("Criar Conta")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaVerificacaoTelefone(navController: NavHostController, onConfirmClick: () -> Unit) {
    val (item1, item2, item3, item4, item5, item6) = FocusRequester.createRefs()

    var remainingTime by remember { mutableStateOf(60) } // 1 minuto (60 segundos)

    // Função para formatar o tempo
    fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val sec = seconds % 60
        return String.format("%02d:%02d", minutes, sec)
    }

    // LaunchedEffect para iniciar o timer
    LaunchedEffect(remainingTime) {
        if (remainingTime > 0) {
            delay(1000L)  // Espera 1 segundo
            remainingTime -= 1  // Decrementa o tempo
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}, modifier = Modifier.offset(x = 3.dp, y = 14.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.seta), // Nome da sua imagem PNG
                            contentDescription = "Seta",
                            colorFilter = ColorFilter.tint(Color.Black),
                            modifier = Modifier
                                .graphicsLayer(
                                    scaleX = -3.5f,
                                    scaleY = 3.5f
                                )
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Card(
                shape = RoundedCornerShape(16.dp), // Borda arredondada do Card
                // Sombra para o Card
                modifier = Modifier
                    .width(380.dp)
                    .height(400.dp)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.intro_image_1), // Substitua pelo ID da sua imagem
                    contentDescription = "Imagem de introdução",
                    modifier = Modifier.fillMaxSize(), // Preenche o tamanho do Car
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "REGISTRAR-SE",
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Insira seus dados para o cadastro.",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item1)
                        .focusProperties {
                            next = item2
                            previous = item1
                        }
                        .padding(start = 10.dp)
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item2)
                        .focusProperties {
                            next = item3
                            previous = item1
                        }
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item3)
                        .focusProperties {
                            next = item4
                            previous = item2
                        }
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item4)
                        .focusProperties {
                            previous = item3
                            next = item5
                        }
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item5)
                        .focusProperties {
                            previous = item4
                            next = item6
                        }
                )
                OtpChar(
                    modifier = Modifier
                        .focusRequester(item6)
                        .focusProperties {
                            previous = item5
                            next = item6
                        }
                        .padding(end = 10.dp)
                )

                //....
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Solicitar novo código em ${formatTime(remainingTime)}",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF05030D),
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Botão Confirmar
            Button(
                onClick = onConfirmClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 90.dp)
                    .height(48.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF59C4FF), Color(0xFF0F59FF)),
                            start = Offset(0f, 1f),
                            end = Offset(0f, 180f) // Direção vertical
                        ),
                        shape = CircleShape // Mantém a forma circular do botão
                    )
                    .size(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            )


            {
                Text("Confirmar")
            }


        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastro(navController: NavController, onNextClick: () -> Unit) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    var dataNascimento by remember { mutableStateOf("") }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val focusManager: FocusManager = LocalFocusManager.current

    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            dataNascimento = millis.toBrazilianDateFormat()
                        }
                        showDatePickerDialog = false
                        focusManager.clearFocus()
                    }
                ) {
                    Text("Escolher data")
                }
            },
            dismissButton = {
                Button(onClick = { showDatePickerDialog = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}, modifier = Modifier.offset(x = 3.dp, y = 14.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.seta), // Nome da sua imagem PNG
                            contentDescription = "Seta",
                            colorFilter = ColorFilter.tint(Color.Black),
                            modifier = Modifier
                                .graphicsLayer(
                                    scaleX = -3.5f,
                                    scaleY = 3.5f
                                )
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(90.dp))
            Text(
                text = "REGISTRAR-SE",
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Insira seus dados para o cadastro.",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Nome
            Text(
                text = "Seu Nome",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 18.dp)
            )
            TextField(
                value = nome,
                onValueChange = { nome = it },
                placeholder = {
                    Text(
                        text = "Digite o seu nome",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                ),

                )
            Spacer(modifier = Modifier.height(16.dp))

            // Email
            Text(
                text = "Email",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 18.dp)
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text(
                        text = "Digite o seu email",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                ),
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Data de Nascimento
            Text(
                text = "Data de Nascimento",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 18.dp)
            )
            TextField(
                value = dataNascimento,
                onValueChange = { dataNascimento = it}, // O campo é somente leitura
                placeholder = {
                    Text(
                        text = "DD     /     MM     /    YYYY",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .onFocusChanged {
                        if (it.isFocused) {
                            showDatePickerDialog = true
                        }
                    },
                textStyle = TextStyle(
                    textAlign = TextAlign.Center
                ),
                readOnly = true,
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )// Impede que o usuário digite manualmente
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Telefone
            Text(
                text = "Telefone Celular",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 18.dp)
            )
            TextField(
                value = telefone,
                onValueChange = { telefone = it },
                placeholder = {
                    Text(
                        text = "Digite o seu telefone celular",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                textStyle = TextStyle(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Botão Próximo
            Button(
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 90.dp)
                    .height(48.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF59C4FF), Color(0xFF0F59FF)),
                            start = Offset(0f, 1f),
                            end = Offset(0f, 180f) // Direção vertical
                        ),
                        shape = CircleShape // Mantém a forma circular do botão
                    )
                    .size(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            )


            {
                Text("Próximo")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onClick: (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder, color = Color.Gray) },
            readOnly = readOnly,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = readOnly && onClick != null) { onClick?.invoke() },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLogin(navController: NavController, onNextClick: () -> Unit) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    var dataNascimento by remember { mutableStateOf("") }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val focusManager: FocusManager = LocalFocusManager.current

    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            dataNascimento = millis.toBrazilianDateFormat()
                        }
                        showDatePickerDialog = false
                        focusManager.clearFocus()
                    }
                ) {
                    Text("Escolher data")
                }
            },
            dismissButton = {
                Button(onClick = { showDatePickerDialog = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}, modifier = Modifier.offset(x = 3.dp, y = 14.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.seta), // Nome da sua imagem PNG
                            contentDescription = "Seta",
                            colorFilter = ColorFilter.tint(Color.Black),
                            modifier = Modifier
                                .graphicsLayer(
                                    scaleX = -3.5f,
                                    scaleY = 3.5f
                                )
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(90.dp))
            Text(
                text = "LOGIN",
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Insira seus dados para o login.",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))

            // Email
            Text(
                text = "Email",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 18.dp)
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text(
                        text = "Digite o seu email",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                ),
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Nome
            Text(
                text = "Senha",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 18.dp)
            )
            TextField(
                value = nome,
                onValueChange = { nome = it },
                placeholder = {
                    Text(
                        text = "Digite a sua senha",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                ),

                )


            Button(
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 90.dp)
                    .height(48.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF59C4FF), Color(0xFF0F59FF)),
                            start = Offset(0f, 1f),
                            end = Offset(0f, 180f) // Direção vertical
                        ),
                        shape = CircleShape // Mantém a forma circular do botão
                    )
                    .size(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            )


            {
                Text("Entrar")
            }
        }
    }
}

@Composable
fun IntroducaoApp(onFinish: () -> Unit) {
    var currentScreen by remember { mutableStateOf(0) }

    val textos = listOf(
        Pair("Aprenda Programação", "Descubra o universo da programação de forma simples e interativa!"),
        Pair("Organize suas Tarefas", "Gerencie seu tempo e alcance seus objetivos com eficiência!"),
        Pair("Aprimore suas Habilidades", "Mergulhe no aprendizado contínuo e melhore suas competências!")
    )

    val drawableArray = arrayOf(
        R.drawable.intro_image_1,
        R.drawable.intro_image_2,
        R.drawable.intro_image_3
    )

    BackHandler(enabled = currentScreen > 0) { // Habilita o back apenas se não for a primeira tela
        if (currentScreen > 0) {
            currentScreen-- // Volta para a tela anterior
        }
    }

    if (currentScreen < textos.size) {
        TelaDeIntroducao(
            titulo = textos[currentScreen].first,
            descricao = textos[currentScreen].second,
            imagemResId = drawableArray[currentScreen], // Substitua pelo ID do recurso da imagem
            progresso = currentScreen,
            onAvancar = {
                if (currentScreen < textos.size - 1) {
                    currentScreen++
                } else {
                    onFinish()
                }
            }
        )
    }
}

@Composable
fun TelaDeIntroducao(
    titulo: String,
    descricao: String,
    imagemResId: Int,
    progresso: Int,
    onAvancar: () -> Unit
) {
    Scaffold(
        containerColor = Color(0xF2F2F2F2),
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(
                    shape = RoundedCornerShape(16.dp), // Borda arredondada do Card
                     // Sombra para o Card
                    modifier = Modifier
                        .width(380.dp)
                        .height(500.dp)
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = imagemResId), // Substitua pelo ID da sua imagem
                        contentDescription = "Imagem de introdução",
                        modifier = Modifier.fillMaxSize(), // Preenche o tamanho do Car
                        contentScale = ContentScale.Crop
                    )
                }

                // Título e descrição
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = titulo,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = descricao,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    )
                }

                Box(
                    contentAlignment = Alignment.Center, // Centraliza o botão dentro do círculo
                    modifier = Modifier.size(85.dp) // Tamanho do círculo externo
                ) {
                    // Indicador circular
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val segmentAngle = 120f // Cada segmento tem 120 graus (360/3)
                        val startAngle = -82f // Começa no topo do círculo
                        val radiusOffset = 8.dp.toPx() // Espaçamento interno

                        for (i in 0 until 3) {
                            val alpha = if (i <= progresso) 1f else 0.2f // Alta opacidade para o progresso atual
                            drawArc(
                                color = Color(0xFF59C4FF).copy(alpha = alpha),
                                startAngle = startAngle + i * segmentAngle,
                                sweepAngle = segmentAngle - 15f,
                                useCenter = false,
                                style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round),
                                size = Size(size.width - radiusOffset, size.height - radiusOffset),
                                topLeft = Offset(
                                    x = radiusOffset / 2,
                                    y = radiusOffset / 2
                                )
                            )
                        }
                    }
                    // Botão circular
                    Button(
                        onClick = { onAvancar() },
                        shape = CircleShape,
                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF59C4FF), Color(0xFF0F59FF)),
                                    start = Offset(0f, 1f),
                                    end = Offset(0f, 180f) // Direção vertical
                                ),
                                shape = CircleShape // Mantém a forma circular do botão
                            )
                            .size(60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.seta), // Nome da sua imagem PNG
                            contentDescription = "Seta",
                            modifier = Modifier
                                .graphicsLayer(
                                    scaleX = 3.5f,
                                    scaleY = 3.5f
                                )
                        )

                    }
                }
            }
        }
    )
}

@Composable
fun TelaInicial(onLoginClick: () -> Unit, onCadastroClick: () -> Unit) {
    BackHandler(enabled = true) {
        // Não faça nada para evitar sair do app
    }
    // Tela dividida em duas partes
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Parte superior: Cabeçalho laranja
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f) // Ocupa 1.5/3 da tela
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0x80FFBA83), Color(0xFFF2742B)),
                        start = Offset(0f, 1f),
                        end = Offset(0f, 1090f) // Direção vertical
                    ),
                    // shape = RoundedCornerShape(30.dp) // Mantém a forma circular do botão
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // Alinha os itens horizontalmente no centro
                verticalArrangement = Arrangement.Top, // Alinha os itens verticalmente no centro
                modifier = Modifier.fillMaxSize() // Faz a Column ocupar toda a tela
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo), // Nome da sua imagem PNG
                    contentDescription = "Seta",
                    modifier = Modifier
                        .size(240.dp)
                        .padding(top = 50.dp)
                )

                Text(
                    text = "CodeCraft",
                    color = Color.White,
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Parte inferior: Botões
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Ocupa 1.5/3 restante
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "BEM-VINDO",
                color = Color.Black,
                fontSize = 23.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            // Botão Login
            Button(
                onClick = onLoginClick,
                shape = RoundedCornerShape(30.dp), // Borda arredondada
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Azul claro
                modifier = Modifier
                    .width(320.dp)
                    .height(60.dp)
                    .padding(bottom = 8.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFFFBA83), Color(0xFFF2742B)),
                            start = Offset(0f, 1f),
                            end = Offset(0f, 180f) // Direção vertical
                        ),
                        shape = RoundedCornerShape(30.dp) // Mantém a forma circular do botão
                    )
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Botão Cadastro
            Button(
                onClick = onCadastroClick,
                shape = RoundedCornerShape(30.dp), // Borda arredondada
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Azul escuro
                modifier = Modifier
                    .width(320.dp)
                    .height(60.dp)
                    .padding(top = 8.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF59C4FF), Color(0xFF0F59FF)),
                            start = Offset(0f, 1f),
                            end = Offset(0f, 180f) // Direção vertical
                        ),
                        shape = RoundedCornerShape(30.dp) // Mantém a forma circular do botão
                    )
            ) {
                Text(
                    text = "Cadastro",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Text(
                text = "Faça login com",
                color = Color.Black,
                fontSize = 23.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 15.dp, top = 28.dp)
            )

            Button(
                onClick = onCadastroClick,
                shape = RoundedCornerShape(30.dp), // Borda arredondada
                colors = ButtonDefaults.buttonColors(containerColor = Color.White), // Azul escuro
                modifier = Modifier
                    .shadow(5.dp, shape = RoundedCornerShape(30.dp))
                    .width(200.dp)
                    .height(60.dp)
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "Facebook",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onCadastroClick,
                shape = RoundedCornerShape(30.dp), // Borda arredondada
                colors = ButtonDefaults.buttonColors(containerColor = Color.White), // Azul escuro
                modifier = Modifier
                    .shadow(5.dp, shape = RoundedCornerShape(30.dp))
                    .width(200.dp)
                    .height(60.dp)
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "Gmail",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }




}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpChar(
    modifier: Modifier = Modifier
){
    val pattern = remember { Regex("^[^\\t]*\$") } //to not accept the tab key as value
    var (text,setText) = remember { mutableStateOf("") }
    val maxChar = 1
    val focusManager = LocalFocusManager.current

    LaunchedEffect(
        key1 = text,
    ) {
        if (text.isNotEmpty()) {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Next,
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value =text,
            onValueChange = {
                if (it.length <= maxChar &&
                    ((it.isEmpty() || it.matches(pattern))))
                    setText(it)
            },
            modifier = modifier
                .width(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .onKeyEvent {
                    if (it.key == Key.Tab) {
                        focusManager.moveFocus(FocusDirection.Next)
                        true
                    }
                    if (text.isEmpty() && it.key == Key.Backspace) {
                        focusManager.moveFocus(FocusDirection.Previous)
                    }
                    false
                },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 18.sp,
                textAlign= TextAlign.Center),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            colors= TextFieldDefaults.textFieldColors(
                //backgroundColor = Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent),

            )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewIntroducao() {
    TentativaResticTheme {
        TelaDeIntroducao(
            titulo = "Aprenda Programação",
            descricao = "Descubra o universo da programação de forma simples e interativa!",
            imagemResId = R.drawable.intro_image_1, // Substitua pelo ID da sua imagem
            progresso = 0,
            onAvancar = {}
        )
    }
}


