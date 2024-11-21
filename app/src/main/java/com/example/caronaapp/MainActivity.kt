package com.example.caronaapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.caronaapp.data.dto.viagem.ViagemProcuraDto
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
import com.example.caronaapp.presentation.screens.meu_perfil.MeuPerfilScreen
import com.example.caronaapp.presentation.screens.notificacoes.NotificacoesScreen
import com.example.caronaapp.presentation.screens.oferecer_viagem.OferecerViagemScreen
import com.example.caronaapp.presentation.screens.perfil_outro_usuario.PerfilOutroUsuarioScreen
import com.example.caronaapp.presentation.screens.procurar_viagem.ProcurarViagemScreen
import com.example.caronaapp.presentation.screens.viagens.ViagensScreen
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            CaronaAppTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(navController)
                    }
                    composable("cadastro") {
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
                    composable("meu-perfil/avaliacoes") {
                        AvaliacoesScreen(navController)
                    }
                    composable("meu-perfil/fidelizados") {
                        FidelizadosScreen(navController)
                    }
                    composable("meu-perfil/carros") {
                        CarrosScreen(navController)
                    }
                    composable(
                        "viagens/{viagem}/{ponto_partida}/{ponto_destino}",
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
                        )
                    ) { entry ->
                        val viagem = entry.arguments?.getString("viagem")
                        val pontoPartida = entry.arguments?.getString("ponto_partida") ?: ""
                        val pontoDestino = entry.arguments?.getString("ponto_destino") ?: ""
                        val viagemProcuraDto = Gson().fromJson(viagem, ViagemProcuraDto::class.java)
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
                        "viagens/detalhes/{id}",
                        arguments = listOf(navArgument(
                            name = "id",
                            builder = { type = NavType.IntType }
                        ))
                    ) { entry ->
                        val viagemId = entry.arguments?.getInt("id")
                        DetalhesViagemScreen(navController, viagemId ?: 0)
                    }
                    composable(
                        route = "usuarios/perfil/{id}",
                        arguments = listOf(navArgument(
                            name = "id",
                            builder = { type = NavType.IntType }
                        ))) { entry ->
                        val userId = entry.arguments?.getInt("id") ?: 0
                        PerfilOutroUsuarioScreen(navController, userId)
                    }
                    composable("chat") {
                        ChatScreen(navController)
                    }
                    composable("chat/conversa") {
                        ConversaScreen(navController)
                    }
                    composable("feedback/{viagem_id}/{usuario_id}",
                        arguments = listOf(navArgument(
                            name = "viagem_id",
                            builder = { type = NavType.IntType }
                        ),
                            navArgument(
                                name = "usuario_id",
                                builder = { type = NavType.IntType })
                        )
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