package com.example.caronaapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.caronaapp.data.dto.viagem.Coordenadas
import com.example.caronaapp.data.dto.viagem.ViagemProcuraDto
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.presentation.screens.avaliacoes.AvaliacoesScreen
import com.example.caronaapp.presentation.screens.cadastro.CadastroScreen
import com.example.caronaapp.presentation.screens.carros.CarrosScreen
import com.example.caronaapp.presentation.screens.chat.ChatScreen
import com.example.caronaapp.presentation.screens.chat.ConversaScreen
import com.example.caronaapp.presentation.screens.detalhes_viagem.DetalhesViagemScreen
import com.example.caronaapp.presentation.screens.esqueci_senha.EsqueciSenhaCodigoScreen
import com.example.caronaapp.presentation.screens.esqueci_senha.EsqueciSenhaEmailScreen
import com.example.caronaapp.presentation.screens.esqueci_senha.RedefinirSenhaScreen
import com.example.caronaapp.presentation.screens.feedback.FeedbackScreen
import com.example.caronaapp.presentation.screens.fidelizados.FidelizadosScreen
import com.example.caronaapp.presentation.screens.historico_viagens.HistoricoViagensScreen
import com.example.caronaapp.presentation.screens.login.LoginScreen
import com.example.caronaapp.presentation.screens.mapa_viagem.MapaViagemScreen
import com.example.caronaapp.presentation.screens.meu_perfil.MeuPerfilScreen
import com.example.caronaapp.presentation.screens.notificacoes.NotificacoesScreen
import com.example.caronaapp.presentation.screens.oferecer_viagem.OferecerViagemScreen
import com.example.caronaapp.presentation.screens.onboarding.OnboardingScreen
import com.example.caronaapp.presentation.screens.perfil_outro_usuario.PerfilOutroUsuarioScreen
import com.example.caronaapp.presentation.screens.procurar_viagem.ProcurarViagemScreen
import com.example.caronaapp.presentation.screens.viagens.ViagensScreen
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            CaronaAppTheme {
                val navController = rememberNavController()
                val dataStoreManager = DataStoreManager(LocalContext.current)

                fun navigate(destination: String) {
                    navController.navigate(destination) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }

                LaunchedEffect(key1 = Unit) {
                    val isOnboardingDone = dataStoreManager.getOnboardingState()
                    val idUser = dataStoreManager.getIdUser()

                    Log.d("initial", "isOnboardingDone: $isOnboardingDone")
                    Log.d("initial", "idUser: $idUser")

                    if (isOnboardingDone == null) {
                        navigate("onboarding")
                    } else if (idUser != null && idUser != 0) {
                        navigate("meu-perfil")
                    } else {
                        navigate("login")
                    }
                }

                NavHost(navController = navController, startDestination = "post-splash") {
                    composable("post-splash") {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                        )
                    }
                    composable("onboarding") {
                        OnboardingScreen(navController)
                    }
                    composable("login") {
                        LoginScreen(navController)
                    }
                    composable(
                        route = "cadastro",
                        enterTransition = {
                            slideIntoContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Up
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Down
                            )
                        }
                    ) {
                        CadastroScreen(navController)
                    }
                    composable("esqueci-senha") {
                        EsqueciSenhaEmailScreen(navController)
                    }
                    composable("esqueci-senha/codigo/{email}") { entry ->
                        val email = entry.arguments?.getString("email")
                        EsqueciSenhaCodigoScreen(navController, email = email)
                    }
                    composable("esqueci-senha/redefinir") {
                        RedefinirSenhaScreen(navController)
                    }
                    composable("meu-perfil") {
                        MeuPerfilScreen(navController)
                    }
                    composable("meu-perfil/notificacoes") {
                        NotificacoesScreen(navController)
                    }
                    composable(
                        route = "meu-perfil/avaliacoes",
                        enterTransition = {
                            slideIntoContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Left
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Right
                            )
                        }
                    ) {
                        AvaliacoesScreen(navController)
                    }
                    composable(
                        route = "meu-perfil/fidelizados",
                        enterTransition = {
                            slideIntoContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Left
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Right
                            )
                        }
                    ) {
                        FidelizadosScreen(navController)
                    }
                    composable(
                        route = "meu-perfil/carros",
                        enterTransition = {
                            slideIntoContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Left
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Right
                            )
                        }
                    ) {
                        CarrosScreen(navController)
                    }
                    composable(
                        route = "viagens/{viagem}/{ponto_partida}/{ponto_destino}",
                        arguments = listOf(
                            navArgument(
                                name = "viagem",
                                builder = { type = NavType.StringType }
                            ),
                            navArgument(
                                name = "ponto_partida",
                                builder = { type = NavType.StringType }
                            ),
                            navArgument(
                                name = "ponto_destino",
                                builder = { type = NavType.StringType }
                            ),
                        ),
                        enterTransition = {
                            slideIntoContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Up
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Down
                            )
                        }
                    ) { entry ->
                        val viagem = entry.arguments?.getString("viagem")
                        val pontoPartida = entry.arguments?.getString("ponto_partida") ?: ""
                        val pontoDestino = entry.arguments?.getString("ponto_destino") ?: ""
                        val viagemProcuraDto =
                            Gson().fromJson(viagem, ViagemProcuraDto::class.java)
                        ViagensScreen(
                            navController = navController,
                            viagem = viagemProcuraDto,
                            pontoPartida = pontoPartida,
                            pontoDestino = pontoDestino
                        )
                    }
                    composable("viagens/procurar") {
                        ProcurarViagemScreen(navController)
                    }
                    composable("viagens/oferecer") {
                        OferecerViagemScreen(navController)
                    }
                    composable("viagens/historico") {
                        HistoricoViagensScreen(navController)
                    }
                    composable(
                        route = "viagens/detalhes/{id}/{distancia_partida}/{distancia_destino}",
                        arguments = listOf(
                            navArgument(
                                name = "id",
                                builder = { type = NavType.IntType }
                            ),
                            navArgument(
                                name = "distancia_partida",
                                builder = {
                                    type = NavType.StringType
                                    nullable = true
                                    defaultValue = null
                                },
                            ),
                            navArgument(
                                name = "distancia_destino",
                                builder = {
                                    type = NavType.StringType
                                    nullable = true
                                    defaultValue = null
                                }
                            ),
                        ),
                        enterTransition = {
                            slideIntoContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Left
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Right
                            )
                        }
                    ) { entry ->
                        val viagemId = entry.arguments?.getInt("id")
                        val distanciaPartida =
                            entry.arguments?.getString("distancia_partida")
                        val distanciaDestino =
                            entry.arguments?.getString("distancia_destino")
                        DetalhesViagemScreen(
                            navController = navController,
                            viagemId = viagemId ?: 0,
                            distanciaPartida = distanciaPartida?.toDouble(),
                            distanciaDestino = distanciaDestino?.toDouble()
                        )
                    }
                    composable(
                        route = "viagens/mapa/{viagemId}/{pontoPartida}",
                        arguments = listOf(
                            navArgument(
                                name = "viagemId",
                                builder = { type = NavType.IntType }
                            ),
                            navArgument(
                                name = "pontoPartida",
                                builder = { type = NavType.StringType }
                            )
                        ),
                        enterTransition = {
                            slideIntoContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Left
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Right
                            )
                        }
                    ) { entry ->
                        val viagemId = entry.arguments?.getInt("viagemId") ?: 0
                        val pontoPartida = entry.arguments?.getString("pontoPartida")
                        val pontoPartidaCoordenadas =
                            Gson().fromJson(pontoPartida, Coordenadas::class.java)
                        MapaViagemScreen(
                            navController = navController,
                            viagemId = viagemId,
                            pontoPartida = pontoPartidaCoordenadas
                        )

                    }
                    composable(
                        route = "usuarios/perfil/{id}",
                        arguments = listOf(navArgument(
                            name = "id",
                            builder = { type = NavType.IntType }
                        )),
                        enterTransition = {
                            slideIntoContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Left
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Right
                            )
                        }
                    ) { entry ->
                        val userId = entry.arguments?.getInt("id") ?: 0
                        PerfilOutroUsuarioScreen(navController, userId)
                    }
                    composable("chat") {
                        ChatScreen(navController)
                    }
                    composable(
                        route = "chat/conversa",
                        enterTransition = {
                            slideIntoContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Left
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Right
                            )
                        }
                    ) {
                        ConversaScreen(navController)
                    }
                    composable(
                        route = "feedback/{viagem_id}/{usuario_id}",
                        arguments = listOf(
                            navArgument(
                                name = "viagem_id",
                                builder = { type = NavType.IntType }
                            ),
                            navArgument(
                                name = "usuario_id",
                                builder = { type = NavType.IntType })
                        ),
                        enterTransition = {
                            slideIntoContainer(
                                animationSpec = tween(500, easing = Ease),
                                towards = AnimatedContentTransitionScope.SlideDirection.Left
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                animationSpec = tween(500, easing = EaseOut),
                                towards = AnimatedContentTransitionScope.SlideDirection.Right
                            )
                        }
                    ) { entry ->
                        val viagemId = entry.arguments?.getInt("viagem_id")
                        val usuarioId = entry.arguments?.getInt("usuario_id")
                        FeedbackScreen(
                            navController = navController,
                            viagemId = viagemId,
                            usuarioId = usuarioId
                        )
                    }
                }
            }
        }
    }
}