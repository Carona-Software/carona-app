package com.example.caronaapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import com.example.caronaapp.presentation.screens.perfil_outro_usuario.PerfilOutroUsuarioScreen
import com.example.caronaapp.presentation.screens.procurar_viagem.ProcurarViagemScreen
import com.example.caronaapp.presentation.screens.viagens.ViagensScreen
import com.example.caronaapp.ui.theme.CaronaAppTheme

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
                    composable("viagens") {
                        ViagensScreen(navController)
                    }
                    composable("viagens/procurar") {
                        ProcurarViagemScreen(navController)
                    }
                    composable("viagens/historico") {
                        HistoricoViagensScreen(navController)
                    }
                    composable("viagens/detalhes/{id}") { entry ->
                        val viagemId = entry.arguments?.getInt("id")
                        DetalhesViagemScreen(navController, viagemId!!)
                    }
                    composable("usuarios/perfil/{id}") { entry ->
                        val userId = entry.arguments?.getInt("id")
                        PerfilOutroUsuarioScreen(navController, userId!!)
                    }
                    composable("chat") {
                        ChatScreen(navController)
                    }
                    composable("chat/conversa") {
                        ConversaScreen(navController)
                    }
                    composable("feedback/{viagemId}/{usuarioId}") { entry ->
                        val viagemId = entry.arguments?.getInt("viagemId")
                        val usuarioId = entry.arguments?.getInt("usuarioId")
                        FeedbackScreen(navController, viagemId, usuarioId)
                    }
                }
            }
        }
    }
}