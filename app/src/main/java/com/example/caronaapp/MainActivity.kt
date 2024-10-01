package com.example.caronaapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.caronaapp.features.avaliacoes.AvaliacoesScreen
import com.example.caronaapp.features.cadastro.CadastroScreen
import com.example.caronaapp.features.carros.CarrosScreen
import com.example.caronaapp.features.chat.ChatScreen
import com.example.caronaapp.features.chat.ConversaScreen
import com.example.caronaapp.features.esqueci_senha.EsqueciSenhaCodigoScreen
import com.example.caronaapp.features.esqueci_senha.EsqueciSenhaEmailScreen
import com.example.caronaapp.features.esqueci_senha.RedefinirSenhaScreen
import com.example.caronaapp.features.fidelizados.FidelizadosScreen
import com.example.caronaapp.features.historico_viagens.HistoricoViagensScreen
import com.example.caronaapp.features.login.LoginScreen
import com.example.caronaapp.features.meu_perfil.MeuPerfilScreen
import com.example.caronaapp.features.notificacoes.NotificacoesScreen
import com.example.caronaapp.features.procurar_viagem.ProcurarViagemScreen
import com.example.caronaapp.ui.theme.CaronaAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                    composable("viagens/procurar") {
                        ProcurarViagemScreen(navController)
                    }
                    composable("viagens/historico") {
                        HistoricoViagensScreen(navController)
                    }
                    composable("chat") {
                        ChatScreen(navController)
                    }
                    composable("chat/conversa") {
                        ConversaScreen(navController)
                    }
                }
            }
        }
    }
}