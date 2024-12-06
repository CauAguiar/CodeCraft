package com.example.tentativarestic

import android.os.Bundle
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app.ui.UserViewModel
import com.example.tentativarestic.data.SharedPrefsManager
import com.example.tentativarestic.models.Atividade
import com.example.tentativarestic.models.Unidade
import com.example.tentativarestic.models.UserViewModelFactory
import com.example.tentativarestic.ui.theme.TentativaResticTheme
import kotlinx.coroutines.delay
import java.net.URLEncoder
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.random.Random

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
    private lateinit var sharedPrefsManager: SharedPrefsManager

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(application) // Passando o application context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefsManager = SharedPrefsManager(application.applicationContext)
        setContent {
            TentativaResticTheme {
                AppNavigation(userViewModel, sharedPrefsManager)
            }
        }
    }
}

@Composable
fun AppNavigation(userViewModel: UserViewModel, sharedPrefsManager: SharedPrefsManager) {
    // Controlador de navegação
    val navController = rememberNavController()

    // Definir o NavHost e as rotas
    NavHost(navController = navController, startDestination = "telaPrincipal") {
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
            TelaLogin(navController, onNextClick = {navController.navigate("telaPrincipal")}, userViewModel = userViewModel)
        }
        composable("telaCadastro") {
            TelaCadastro(
                navController = navController,
                onNextClick = { nome, dataNascimento, email, telefone ->
                    // URL encode parameters
                    val encodedNome = URLEncoder.encode(nome, "UTF-8")
                    val encodedDataNascimento = URLEncoder.encode(dataNascimento, "UTF-8")
                    val encodedEmail = URLEncoder.encode(email, "UTF-8")
                    val encodedTelefone = URLEncoder.encode(telefone, "UTF-8")

                    // Navigate with encoded parameters
                    navController.navigate("verificacaoTelefone/$encodedNome/$encodedDataNascimento/$encodedEmail/$encodedTelefone")
                },
                userViewModel = userViewModel
            )
        }

        composable(
            "verificacaoTelefone/{nome}/{dataNascimento}/{email}/{telefone}",
            arguments = listOf(
                navArgument("nome") { type = NavType.StringType },
                navArgument("dataNascimento") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("telefone") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Decode parameters
            val nome = URLDecoder.decode(backStackEntry.arguments?.getString("nome") ?: "", "UTF-8")
            val dataNascimento = URLDecoder.decode(backStackEntry.arguments?.getString("dataNascimento") ?: "", "UTF-8")
            val email = URLDecoder.decode(backStackEntry.arguments?.getString("email") ?: "", "UTF-8")
            val telefone = URLDecoder.decode(backStackEntry.arguments?.getString("telefone") ?: "", "UTF-8")

            TelaVerificacaoTelefone(
                navController = navController,
                onConfirmClick = { nome, dataNascimento, email, telefone ->
                    // URL encode parameters
                    val encodedNome = URLEncoder.encode(nome, "UTF-8")
                    val encodedDataNascimento = URLEncoder.encode(dataNascimento, "UTF-8")
                    val encodedEmail = URLEncoder.encode(email, "UTF-8")
                    val encodedTelefone = URLEncoder.encode(telefone, "UTF-8")

                    // Navigate with encoded parameters
                    navController.navigate("digitarSenha/$encodedNome/$encodedDataNascimento/$encodedEmail/$encodedTelefone")
                },
                nome = nome,
                dataNascimento = dataNascimento,
                email = email,
                telefone = telefone
            )
        }


        composable("digitarSenha/{nome}/{dataNascimento}/{email}/{telefone}",
            arguments = listOf(
                navArgument("nome") { type = NavType.StringType },
                navArgument("dataNascimento") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("telefone") { type = NavType.StringType }
            )
        ){ backStackEntry ->
            // Decode parameters
            val nome = URLDecoder.decode(backStackEntry.arguments?.getString("nome") ?: "", "UTF-8")
            val dataNascimento = URLDecoder.decode(backStackEntry.arguments?.getString("dataNascimento") ?: "", "UTF-8")
            val email = URLDecoder.decode(backStackEntry.arguments?.getString("email") ?: "", "UTF-8")
            val telefone = URLDecoder.decode(backStackEntry.arguments?.getString("telefone") ?: "", "UTF-8")

            TelaDigitarSenha(
                navController = navController,
                onConfirmClick = { navController.navigate("telaPrincipal") },
                nome = nome,
                dataNascimento = dataNascimento,
                email = email,
                telefone = telefone,
                userViewModel = userViewModel
            )
        }

        composable("telaPrincipal") {
            TelaPrincipal(navController, onProfileClick = {navController.navigate("telaPerfil")}, sharedPrefsManager = sharedPrefsManager, userViewModel = userViewModel)
        }

        composable("telaPerfil") {
            TelaPerfil(navController, onHomeClick = {navController.navigate("telaPrincipal")})
        }

        composable("curso"){
            TelaCurso(navController, onModuloClick = {navController.navigate("modulo")}, sharedPrefsManager = sharedPrefsManager, userViewModel = userViewModel)
        }

        composable ("modulo"){
            TelaModulo(navController, sharedPrefsManager = sharedPrefsManager, userViewModel = userViewModel)
        }


    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaModulo(navController: NavHostController, sharedPrefsManager: SharedPrefsManager, userViewModel: UserViewModel) {
    // Container principal
    var atividades = sharedPrefsManager.getAtividades()
    var currentActivityIndex by remember { mutableStateOf(0) }
    val nome_curso = sharedPrefsManager.getCursoNome()
    val unidade_ordem = sharedPrefsManager.getUnidadeOrdem()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "$nome_curso - Unidade $unidade_ordem",
                        fontSize = 24.sp,
                        modifier = Modifier.offset(y = 14.dp)
                    ) },
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
        Log.d("AtividadesDebug", "Lista de atividades: $atividades")
        Log.d("AtividadesDebug", "Índice atual: $currentActivityIndex")
        val atividadeAtual = atividades[currentActivityIndex]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título da atividade
            Text(
                text = atividadeAtual.nome,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Tipo de atividade
            Text(
                text = "Tipo: ${atividadeAtual.tipo}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Exibição personalizada para atividades específicas
            when (atividadeAtual.tipo) {
                "quiz" -> QuizContent(atividadeAtual)
                "video" -> VideoContent(atividadeAtual)
                "exercicio_aberto" -> ExerciseContent(atividadeAtual)
                else -> DefaultContent(atividadeAtual)
            }

            Spacer(modifier = Modifier.weight(1f))

            // Barra de progresso e navegação
            BottomProgressBar(
                currentActivityIndex = currentActivityIndex,
                totalActivities = atividades.size,
                onPreviousClick = { if (currentActivityIndex > 0) currentActivityIndex-- },
                onNextClick = { if (currentActivityIndex < atividades.size - 1) currentActivityIndex++ }
            )
        }
    }
}

@Composable
fun QuizContent(atividade: Atividade) {
    var respostaSelecionada by remember { mutableStateOf<Int?>(null) }
    var resultado by remember { mutableStateOf<Boolean?>(null) }

    val pergunta = "O que significa uma estrutura de controle \"if\"?"
    val opcoes = listOf(
            "Repita uma ação várias vezes.",
            "Fazer escolhas com base em condições.",
            "Declarar uma variável.",
            "Iniciar um programa."
        )
    val respostaCorreta = 1 // Opção 2 é a correta (índice 1)



        Text(
            text = pergunta,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        opcoes.forEachIndexed { index, opcao ->
            val cor = if (respostaSelecionada == index && resultado == true) {
                //MaterialTheme.colors.primary
                Color(0xFF8CB38D)
            } else if (respostaSelecionada == index && resultado == false) {
                //MaterialTheme.colors.error
                Color(0xFFEB928C)
            } else {
                //MaterialTheme.colors.surface
                Color(0xFFFAFAFA)
            }

            Button(
                onClick = {
                    respostaSelecionada = index
                    resultado = index == respostaCorreta
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(vertical = 8.dp)
                    .background(cor, shape = RoundedCornerShape(14.dp))
                    .border(
                        1.dp,
                        Color.LightGray,
                        RoundedCornerShape(14.dp)
                    ), // Borda menos arredondada
                colors = ButtonDefaults.buttonColors(containerColor = cor) // Branco neve
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    // Círculo com número
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color.White, shape = CircleShape)
                            .border(1.dp, Color.LightGray, CircleShape) // Borda preta
                            .padding(6.dp), // Ajuste o tamanho do círculo
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Espaço entre o círculo e o texto

                    // Texto da opção
                    Text(
                        text = opcao,
                        color = Color.Black,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }



        if (respostaSelecionada != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (resultado == true) "Resposta correta!" else "Resposta incorreta.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (resultado == true) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        }

}

@Composable
fun VideoContent(atividade: Atividade) {
    Text(
        text = "Video: ${atividade.nome}",
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun ExerciseContent(atividade: Atividade) {
    Text(
        text = "Exercício: ${atividade.nome}",
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun DefaultContent(atividade: Atividade) {
    Text(
        text = "Atividade genérica: ${atividade.nome}",
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun BottomProgressBar(
    currentActivityIndex: Int,
    totalActivities: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    // Calcula o progresso
    val progress = (currentActivityIndex + 1).toFloat() / totalActivities

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Barra de progresso
        LinearProgressIndicator(
            progress = progress, // Progresso atual
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = Color(0xFF2196F3),
            trackColor = Color(0xFFE0E0E0)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botões de navegação e indicador
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botão Anterior
            Button(
                onClick = onPreviousClick,
                enabled = currentActivityIndex > 0 // Desabilita se for a primeira atividade
            ) {
                Text(text = "Anterior")
            }

            // Texto indicando progresso
            Text(
                text = "${currentActivityIndex + 1}/$totalActivities",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )

            // Botão Próxima
            Button(
                onClick = onNextClick,
                enabled = currentActivityIndex < totalActivities - 1 // Desabilita se for a última atividade
            ) {
                Text(text = "Próxima")
            }
        }
    }
}

data class LanguageItem(val name: String, val icon: ImageVector)

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCurso(navController: NavHostController, onModuloClick: () -> Unit, sharedPrefsManager: SharedPrefsManager, userViewModel: UserViewModel){
    val nome_curso = sharedPrefsManager.getCursoNome()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                    "$nome_curso",
                    fontSize = 24.sp,
                        modifier = Modifier.offset(y = 14.dp)
                ) },
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
    ) { innerPadding ->

        val unidades = remember { sharedPrefsManager.getUnidades() }
        Log.d("UnidadeListScreen", "Número de unidades: ${unidades.size}")
        // Exibe as unidades em uma lista rolável
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            //contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(unidades.size) { index ->
                UnidadeItem(unidades[index], navController, sharedPrefsManager, userViewModel)
            }
        }
    }
}

@Composable
fun UnidadeItem(unidade: Unidade, navController: NavHostController, sharedPrefsManager: SharedPrefsManager, userViewModel: UserViewModel) {
    // Item individual para cada unidade
    Spacer(modifier = Modifier.height(8.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            text = unidade.titulo,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        )
    }
    Spacer(modifier = Modifier.height(8.dp))

    // Ordenando os módulos pela ordem
    val modulosOrdenados = unidade.modulos.sortedBy { it.ordem }

    // Exibindo cada módulo
    // Definindo a posição das imagens
    val totalModulos = modulosOrdenados.size


    // Exibindo cada módulo com efeito de deslocamento
    modulosOrdenados.forEachIndexed { index, modulo ->
        // Calculando o deslocamento para criar o efeito de "cobra"
        val normalizedPosition = index.toFloat() / totalModulos
        val direction = if (normalizedPosition < 0.5f) -1f else 1f



        var offsetX = (normalizedPosition * 60f * direction).dp


        // Verificando se o módulo está habilitado
        val isEnabled = if (modulo.habilitado) 1 else 0  // Converte true/false para 1/0



        // Determinando a imagem com base no módulo
            val imageResource = when (modulo.descricao.lowercase()) {
                "estrela" -> R.drawable.imagem_estrela
                "bau" -> R.drawable.imagem_bau
                "trofeu" -> R.drawable.imagem_trofeu
                "livro" -> R.drawable.imagem_livro
                else -> R.drawable.imagem_padrao // Imagem padrão se não coincidir
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Modificando o aspecto visual da imagem se o módulo estiver desabilitado
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = modulo.descricao,
                modifier = Modifier
                    .size(80.dp)
                    .offset(x = offsetX) // Aplica o deslocamento calculado
                    .clickable(
                        enabled = (isEnabled == 1) // Clique só funciona se o módulo estiver habilitado
                    ) {
                        if (isEnabled == 1) {
                            sharedPrefsManager.saveUnidadeOrdem(unidade.ordem)
                            sharedPrefsManager.saveAtividades(modulo.atividades ?: emptyList())
                            userViewModel.processarAtividades(modulo.atividades ?: emptyList())
                            navController.navigate("modulo") // Navega para o módulo específico
                        }
                    }
                    .graphicsLayer { // Aplica transparência se desabilitado
                        alpha =
                            if (isEnabled == 1) 1f else 0.3f // 1f = opaco, 0.3f = semitransparente
                    }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPerfil(navController: NavHostController, onHomeClick: () -> Unit) {
    val languages = listOf(
        LanguageItem("Meus cursos", /*R.drawable.python*/Icons.Filled.Home),
        LanguageItem("Tutores", /*R.drawable.javascript*/Icons.Filled.Home),
        LanguageItem("Pagamento", /*R.drawable.uiux*/Icons.Filled.Home),
        LanguageItem("Transação", /*R.drawable.java*/Icons.Filled.Home),
        LanguageItem("Estatísticas", /*R.drawable.cpp*/Icons.Filled.Home),
        LanguageItem("Favoritos", /*R.drawable.algorithms*/Icons.Filled.Home),
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(title = { Text("Perfil", fontWeight = FontWeight.Black, fontSize = 28.sp) }, modifier = Modifier.offset(y = 14.dp), actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Box(
                        modifier = Modifier
                            .height(42.dp)
                            .width(42.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF59C4FF), Color(0xFF0F59FF)),
                                    start = Offset(0f, 1f),
                                    end = Offset(0f, 180f) // Direção vertical
                                ),
                                shape = CircleShape // Forma circular
                            )
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = "Notification",
                            modifier = Modifier
                                .fillMaxSize(1.0f)
                                .scale(0.7f),// Faz o ícone ocupar todo o espaço disponível
                            tint = Color.White
                        )
                    }
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
                    containerColor = Color.White,
                    windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)


                    // Definindo bordas arredondadas para o NavigationBar

                ) {
                    NavigationBarItem(icon = {
                        Box(
                            modifier = Modifier
                                .height(42.dp)
                                .width(15.dp)
                                .background(
                                    color = Color.Transparent,
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
                        selected = false, onClick = onHomeClick
                    )
                    NavigationBarItem(icon = {
                        Box(
                            modifier = Modifier
                                .height(42.dp)
                                .width(15.dp)
                                .background(
                                    color = Color.Transparent, // Cor do círculo (laranja)
                                    shape = CircleShape // Forma circular
                                )
                        ) {
                            Icon(
                                Icons.Filled.Info,
                                contentDescription = "History",
                                modifier = Modifier
                                    .fillMaxSize(1.0f)
                                    .scale(2.0f),// Faz o ícone ocupar todo o espaço disponível
                            )
                        }
                    },
                        selected = false, onClick = { /*TODO*/ })
                    NavigationBarItem(icon = {
                        Box(
                            modifier = Modifier
                                .height(42.dp)
                                .width(15.dp)
                                .background(
                                    color = Color.Transparent, // Cor do círculo (laranja)
                                    shape = CircleShape // Forma circular
                                )
                        ) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search",
                                modifier = Modifier
                                    .fillMaxSize(1.0f)
                                    .scale(2.0f),// Faz o ícone ocupar todo o espaço disponível
                            )
                        }
                    },
                        selected = false, onClick = { /*TODO*/ })
                    NavigationBarItem(icon = {
                        Box(
                            modifier = Modifier
                                .height(42.dp)
                                .width(15.dp)
                                .background(
                                    color = Color.Transparent, // Cor do círculo (laranja)
                                    shape = CircleShape // Forma circular
                                )
                        ) {
                            Icon(
                                Icons.Filled.Email,
                                contentDescription = "Chat",
                                modifier = Modifier
                                    .fillMaxSize(1.0f)
                                    .scale(2.0f),// Faz o ícone ocupar todo o espaço disponível
                            )
                        }
                    },
                        selected = false, onClick = { /*TODO*/ })
                    NavigationBarItem(icon = {
                        Box(
                            modifier = Modifier
                                .height(42.dp)
                                .width(15.dp)
                                .background(
                                    color = Color.Transparent, // Cor do círculo (laranja)
                                    shape = CircleShape // Forma circular
                                )
                        ) {
                            Icon(
                                Icons.Filled.Person,
                                contentDescription = "Profile",
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
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF59C4FF), Color(0xFF0F59FF)),
                                    start = Offset(0f, 1f),
                                    end = Offset(0f, 180f) // Direção vertical
                                ),
                                shape = CircleShape
                            )
                    )
                }

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(36.dp))

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                // You can replace this with an actual image loading library like Coil or Glide
                Image(
                    painter = painterResource(id = R.drawable.intro_image_1),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Pedro Johnson", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Designer de UX/UI", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { /*TODO*/ },
                contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFDD835)
                ), //Gold color
                modifier = Modifier
                    .fillMaxWidth(0.33f)
                    .height(28.dp),
            ) {
                Text("Membro de Ouro", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF59C4FF), Color(0xFF0F59FF)),
                            start = Offset(0f, 1f),
                            end = Offset(0f, 300f) // Direção vertical
                        ),
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .padding(26.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "25", fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color.White)
                    Text(text = "Inscrito", fontSize = 18.sp, color = Color.White)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "8", fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color.White)
                    Text(text = "Certificados", fontSize = 18.sp, color = Color.White)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "12", fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color.White)
                    Text(text = "Cursos", fontSize = 18.sp, color = Color.White)
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(-1f)
                        .padding(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(languages) { language ->
                        LanguageCard2(language)
                    }
                }

                Button(
                    onClick = { /*TODO*/ },
                    contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    ), //Gold color
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(48.dp)
                        .offset(y = (-8).dp),
                ) {
                    Icon(Icons.Filled.Close, contentDescription = "Profile", tint = Color.Red)
                    Text("Sair", color = Color.Gray, fontSize = 16.sp)
                }
            }



        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPrincipal(
    navController: NavHostController,
    onProfileClick: () -> Unit,
    sharedPrefsManager: SharedPrefsManager,
    userViewModel: UserViewModel
) {
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

    val userName = sharedPrefsManager.getUserName()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(title = { Text("Bom dia $userName!")}, modifier = Modifier.offset(y = 14.dp), actions = {
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
                            .height(80.dp)
                            .fillMaxWidth(0.9f),

                        containerColor = Color.White,
                        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)


                        // Definindo bordas arredondadas para o NavigationBar

                    ) {
                        NavigationBarItem(icon = {
                            Box(
                                modifier = Modifier
                                    .height(42.dp)
                                    .width(15.dp)
                                    .background(
                                        color = Color.Transparent,
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
                                    brush = Brush.linearGradient(
                                        colors = listOf(Color(0xFF59C4FF), Color(0xFF0F59FF)),
                                        start = Offset(0f, 1f),
                                        end = Offset(0f, 180f) // Direção vertical
                                    ),
                                    shape = CircleShape
                                )
                        )
                        NavigationBarItem(icon = {
                            Box(
                                modifier = Modifier
                                    .height(42.dp)
                                    .width(15.dp)
                                    .background(
                                        color = Color.Transparent, // Cor do círculo (laranja)
                                        shape = CircleShape // Forma circular
                                    )
                            ) {
                                Icon(
                                    Icons.Filled.Info,
                                    contentDescription = "History",
                                            modifier = Modifier
                                                .fillMaxSize(1.0f)
                                                .scale(2.0f),// Faz o ícone ocupar todo o espaço disponível
                                )
                            }
                        },
                            selected = false, onClick = { /*TODO*/ })
                        NavigationBarItem(icon = {
                            Box(
                                modifier = Modifier
                                    .height(42.dp)
                                    .width(15.dp)
                                    .background(
                                        color = Color.Transparent, // Cor do círculo (laranja)
                                        shape = CircleShape // Forma circular
                                    )
                            ) {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search",
                                    modifier = Modifier
                                        .fillMaxSize(1.0f)
                                        .scale(2.0f),// Faz o ícone ocupar todo o espaço disponível
                                )
                            }
                        },
                            selected = false, onClick = { /*TODO*/ })
                        NavigationBarItem(icon = {
                            Box(
                                modifier = Modifier
                                    .height(42.dp)
                                    .width(15.dp)
                                    .background(
                                        color = Color.Transparent, // Cor do círculo (laranja)
                                        shape = CircleShape // Forma circular
                                    )
                            ) {
                                Icon(
                                    Icons.Filled.Email,
                                    contentDescription = "Chat",
                                    modifier = Modifier
                                        .fillMaxSize(1.0f)
                                        .scale(2.0f),// Faz o ícone ocupar todo o espaço disponível
                                )
                            }
                        },
                            selected = false, onClick = {})
                        NavigationBarItem(
                            icon = {
                                Box(
                                    modifier = Modifier
                                        .height(42.dp)
                                        .width(15.dp)
                                        .background(
                                            color = Color.Transparent, // Cor do círculo (laranja)
                                            shape = CircleShape // Forma circular
                                        )
                                ) {
                                    Icon(
                                        Icons.Filled.Person,
                                        contentDescription = "Profile",
                                        modifier = Modifier
                                            .fillMaxSize(1.0f)
                                            .scale(2.0f),// Faz o ícone ocupar todo o espaço disponível
                                    )
                                }
                            },
                            selected = false, onClick = onProfileClick,
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
                modifier = Modifier
                    .padding(start = 16.dp)
                    .padding(bottom = 1.dp)
                    .padding(top = 16.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Procurar") },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(30)),  // Deixa o campo com bordas arredondadas
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    },
                    shape = RoundedCornerShape(30),  // Borda completamente arredondada
                    colors = OutlinedTextFieldDefaults.colors()
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
                        LanguageCard(language, sharedPrefsManager, userViewModel, navController)
                    }
                }
            }
        }
    }
}


@Composable
fun LanguageCard(language: LanguageItem, sharedPrefsManager: SharedPrefsManager, userViewModel: UserViewModel, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenWidthDp.dp / 3 - 16.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                sharedPrefsManager.saveCursoNome(language.name)
                userViewModel.getUnidadesByCurso(language.name)
                navController.navigate("curso")

            },
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

@Composable
fun LanguageCard2(language: LanguageItem) {
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
            Text(language.name, textAlign = TextAlign.Center, fontSize = 14.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDigitarSenha(
    navController: NavHostController,
    onConfirmClick: () -> Unit,
    nome: String,
    dataNascimento: String,
    email: String,
    telefone: String,
    userViewModel: UserViewModel
) {
    // Criação dos FocusRequesters para os campos de OTP
    val (item1, item2, item3, item4, item5, item6) = FocusRequester.createRefs()
    val (item1_confirm, item2_confirm, item3_confirm, item4_confirm, item5_confirm, item6_confirm) = FocusRequester.createRefs()
    val (value1, setValue1) = remember { mutableStateOf("") }
    val (value2, setValue2) = remember { mutableStateOf("") }
    val (value3, setValue3) = remember { mutableStateOf("") }
    val (value4, setValue4) = remember { mutableStateOf("") }
    val (value5, setValue5) = remember { mutableStateOf("") }
    val (value6, setValue6) = remember { mutableStateOf("") }

    val finalValue = value1 + value2 + value3 + value4 + value5 + value6

    val (value12, setValue12) = remember { mutableStateOf("") }
    val (value22, setValue22) = remember { mutableStateOf("") }
    val (value32, setValue32) = remember { mutableStateOf("") }
    val (value42, setValue42) = remember { mutableStateOf("") }
    val (value52, setValue52) = remember { mutableStateOf("") }
    val (value62, setValue62) = remember { mutableStateOf("") }

    val finalValue2 = value1 + value2 + value3 + value4 + value5 + value6

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
                    value = value1,
                    onValueChange = setValue1,
                    modifier = Modifier
                        .focusRequester(item1)
                        .focusProperties {
                            next = item2
                            previous = item1
                        }
                        .padding(start = 10.dp)
                )
                OtpChar(
                    value = value2,
                    onValueChange = setValue2,
                    modifier = Modifier
                        .focusRequester(item2)
                        .focusProperties {
                            next = item3
                            previous = item1
                        }
                )
                OtpChar(
                    value = value3,
                    onValueChange = setValue3,
                    modifier = Modifier
                        .focusRequester(item3)
                        .focusProperties {
                            next = item4
                            previous = item2
                        }
                )
                OtpChar(
                    value = value4,
                    onValueChange = setValue4,
                    modifier = Modifier
                        .focusRequester(item4)
                        .focusProperties {
                            previous = item3
                            next = item5
                        }
                )
                OtpChar(
                    value = value5,
                    onValueChange = setValue5,
                    modifier = Modifier
                        .focusRequester(item5)
                        .focusProperties {
                            previous = item4
                            next = item6
                        }
                )
                OtpChar(
                    value = value6,
                    onValueChange = setValue6,
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
                    value = value12,
                    onValueChange = setValue12,
                    modifier = Modifier
                        .focusRequester(item1_confirm)
                        .focusProperties {
                            next = item2_confirm
                            previous = item1_confirm
                        }
                        .padding(start = 10.dp)
                )
                OtpChar(
                    value = value22,
                    onValueChange = setValue22,
                    modifier = Modifier
                        .focusRequester(item2_confirm)
                        .focusProperties {
                            next = item3_confirm
                            previous = item1_confirm
                        }
                )
                OtpChar(
                    value = value32,
                    onValueChange = setValue32,
                    modifier = Modifier
                        .focusRequester(item3_confirm)
                        .focusProperties {
                            next = item4_confirm
                            previous = item2_confirm
                        }
                )
                OtpChar(
                    value = value42,
                    onValueChange = setValue42,
                    modifier = Modifier
                        .focusRequester(item4_confirm)
                        .focusProperties {
                            previous = item3_confirm
                            next = item5_confirm
                        }
                )
                OtpChar(
                    value = value52,
                    onValueChange = setValue52,
                    modifier = Modifier
                        .focusRequester(item5_confirm)
                        .focusProperties {
                            previous = item4_confirm
                            next = item6_confirm
                        }
                )
                OtpChar(
                    value = value62,
                    onValueChange = setValue62,
                    modifier = Modifier
                        .focusRequester(item6_confirm)
                        .focusProperties {
                            previous = item5_confirm
                            next = item6_confirm
                        }
                        .padding(end = 10.dp)
                )
            }

            fun verifyPassword(): Boolean {
                return finalValue == finalValue2
            }

            val context = LocalContext.current

            fun registerUser() {
                if (verifyPassword()) {
                    userViewModel.registerPerson(nome, email, finalValue, telefone, dataNascimento);
                    onConfirmClick()
                } else {
                    // Senhas não coincidem
                    // Exibir mensagem de erro
                    Toast.makeText(
                        context,
                        "As senhas não coincidem. Por favor, tente novamente.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }



            Button(
                onClick = { registerUser() },
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


@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaVerificacaoTelefone (
    navController: NavController,
    onConfirmClick: (String, String, String, String) -> Unit,
    nome: String,
    dataNascimento: String,
    email: String,
    telefone: String
) {
    val (item1, item2, item3, item4, item5, item6) = FocusRequester.createRefs()

    var remainingTime by remember { mutableStateOf(60) } // 1 minuto (60 segundos)

    var codigo by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        codigo = Random.nextInt(100000, 1000000)  // Gera o código uma vez
        Log.d("Código de verificação", codigo.toString())
    }

    val (value1, setValue1) = remember { mutableStateOf("") }
    val (value2, setValue2) = remember { mutableStateOf("") }
    val (value3, setValue3) = remember { mutableStateOf("") }
    val (value4, setValue4) = remember { mutableStateOf("") }
    val (value5, setValue5) = remember { mutableStateOf("") }
    val (value6, setValue6) = remember { mutableStateOf("") }

    val finalValue = value1 + value2 + value3 + value4 + value5 + value6


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
                text = "VERIFICAR TELEFONE",
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Insira o código enviado por SMS.",
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
                    value = value1,
                    onValueChange = setValue1,
                    modifier = Modifier
                        .focusRequester(item1)
                        .focusProperties {
                            next = item2
                            previous = item1
                        }
                        .padding(start = 10.dp)
                )
                OtpChar(
                    value = value2,
                    onValueChange = setValue2,
                    modifier = Modifier
                        .focusRequester(item2)
                        .focusProperties {
                            next = item3
                            previous = item1
                        }
                )
                OtpChar(
                    value = value3,
                    onValueChange = setValue3,
                    modifier = Modifier
                        .focusRequester(item3)
                        .focusProperties {
                            next = item4
                            previous = item2
                        }
                )
                OtpChar(
                    value = value4,
                    onValueChange = setValue4,
                    modifier = Modifier
                        .focusRequester(item4)
                        .focusProperties {
                            previous = item3
                            next = item5
                        }
                )
                OtpChar(
                    value = value5,
                    onValueChange = setValue5,
                    modifier = Modifier
                        .focusRequester(item5)
                        .focusProperties {
                            previous = item4
                            next = item6
                        }
                )
                OtpChar(
                    value = value6,
                    onValueChange = setValue6,
                    modifier = Modifier
                        .focusRequester(item6)
                        .focusProperties {
                            previous = item5
                            next = item6
                        }
                        .padding(end = 10.dp)
                )

                //Text("Valor final: $finalValue") log

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

            val context = LocalContext.current

            fun verifyCode() {
                if (finalValue.toInt() == codigo) {
                    Log.d("Cadastro", "Nome: $nome, Email: $email, Telefone: $telefone, Data de Nascimento: $dataNascimento")
                    onConfirmClick(nome, dataNascimento, email, telefone)
                } else {
                    // Exibe um Toast com a mensagem de erro
                    Toast.makeText(context, "Código Diferente", Toast.LENGTH_SHORT).show()
                }
            }

            // Botão Confirmar
            Button(
                onClick = { verifyCode() },
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


@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastro(navController: NavController, onNextClick: (String, String, String, String) -> Unit, userViewModel: UserViewModel) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    var dataNascimento by remember { mutableStateOf("") }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val focusManager: FocusManager = LocalFocusManager.current
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    fun checkIfExistsAndRegister() {
        userViewModel.checkIfExists(email, telefone) { result ->
            errorMessage = result

            if (result == "") {
                // Se não houver erro, chama a próxima tela com os dados
                //print no terminal que deu certo
                Log.d("Cadastro", "Nome: $nome, Email: $email, Telefone: $telefone, Data de Nascimento: $dataNascimento")
                onNextClick(nome, dataNascimento, email, telefone)
            } else {
                // Caso contrário, exibe o erro
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }


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
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
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
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
                ),
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
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
                ),
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
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
                ),
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Botão Próximo
            Button(
                onClick = { checkIfExistsAndRegister() },  //onNextClick
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            ),
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLogin(navController: NavController, onNextClick: () -> Unit, userViewModel: UserViewModel) {
    var senha by remember { mutableStateOf("") }
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
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
                ),
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
                value = senha,
                onValueChange = { senha = it },
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
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                ),

                )

            val context = LocalContext.current

            Button(
                onClick = {
                    userViewModel.loginWithEmail(email, senha) { isSuccess ->
                        if (isSuccess) {
                            // Ação para login bem-sucedido
                            Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                            onNextClick()
                        } else {
                            // Ação para falha no login
                            Toast.makeText(context, "Falha no login. Tente novamente.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, //onNextClick
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
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    val pattern = remember { Regex("^[^\\t]*\$") } //to not accept the tab key as value
    val maxChar = 1
    val focusManager = LocalFocusManager.current

    LaunchedEffect(
        key1 = value,
    ) {
        if (value.isNotEmpty()) {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Next,
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = value,
            onValueChange = {
                if (it.length <= maxChar &&
                    ((it.isEmpty() || it.matches(pattern))))
                    onValueChange(it)
            },
            modifier = modifier
                .width(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .onKeyEvent { event ->
                    if (event.key == Key.Tab) {
                        focusManager.moveFocus(FocusDirection.Next)
                        true
                    } else if (value.isEmpty() && event.key == Key.Backspace) {
                        focusManager.moveFocus(FocusDirection.Previous)
                        true
                    } else {
                        false
                    }
                },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 18.sp,
                textAlign= TextAlign.Center),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray,
                disabledBorderColor = Color.LightGray
            ),
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


