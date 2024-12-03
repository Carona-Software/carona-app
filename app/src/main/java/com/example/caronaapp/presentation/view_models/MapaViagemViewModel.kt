package com.example.caronaapp.presentation.view_models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.caronaapp.data.dto.usuario.UsuarioSimplesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemDetalhesListagemDto
import com.example.caronaapp.data.repositories.MapboxRepositoryImpl
import com.example.caronaapp.data.repositories.ViagemRepositoryImpl
import com.example.caronaapp.presentation.screens.mapa_viagem.MapaViagemUiState
import com.example.caronaapp.utils.functions.isUrlFotoUserValida
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.max
import kotlin.math.min

class MapaViagemViewModel(
    private val mapboxRepository: MapboxRepositoryImpl,
    private val viagemRepository: ViagemRepositoryImpl
) : ViewModel() {

    val viagemData = MutableStateFlow<ViagemDetalhesListagemDto?>(null)

    val state = MutableStateFlow(MapaViagemUiState())

    fun getDetalhesViagem(viagemId: Int) {
        viewModelScope.launch {
            try {
                val response = viagemRepository.findById(viagemId)

                if (response.isSuccessful) {
                    Log.i(
                        "mapaViagem",
                        "Sucesso ao buscar detalhes da viagem: ${response.body()}"
                    )
                    val viagemResponse = response.body()
                    viagemData.update { setDetalhesViagemComFotoVerificada(viagemResponse!!) }
                    state.update {
                        it.copy(
                            pontoPartida = LatLng(
                                viagemResponse?.trajeto?.pontoPartida?.coordenadas?.latitude ?: 0.0,
                                viagemResponse?.trajeto?.pontoPartida?.coordenadas?.longitude ?: 0.0
                            ),
                            pontoDestino = LatLng(
                                viagemResponse?.trajeto?.pontoChegada?.coordenadas?.latitude ?: 0.0,
                                viagemResponse?.trajeto?.pontoChegada?.coordenadas?.longitude ?: 0.0
                            )
                        )
                    }
                    getRouteBetweenPlaces()
                } else {
                    state.update { it.copy(isError = true) }
                    Log.e(
                        "mapaViagem",
                        "Erro ao buscar detalhes da viagem: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                state.update { it.copy(isError = true) }
                Log.e(
                    "mapaViagem",
                    "Erro ao buscar detalhes da viagem: ${e.message}"
                )
            } finally {
                state.update { it.copy(isLoadingScreen = false) }
            }
        }
    }

    fun setMapLoaded() {
        state.update { it.copy(isMapLoaded = true) }
    }

    private suspend fun getRouteBetweenPlaces() {
        try {
            val response = mapboxRepository.getDistanceBetweenPlaces(
                latitudePartida = viagemData.value?.trajeto?.pontoPartida?.coordenadas?.latitude
                    ?: 0.0,
                longitudePartida = viagemData.value?.trajeto?.pontoPartida?.coordenadas?.longitude
                    ?: 0.0,
                latitudeDestino = viagemData.value?.trajeto?.pontoChegada?.coordenadas?.latitude
                    ?: 0.0,
                longitudeDestino = viagemData.value?.trajeto?.pontoChegada?.coordenadas?.longitude
                    ?: 0.0,
            )

            if (response.isSuccessful) {
                Log.i("mapaViagem", "Sucesso ao consultar rota para a viagem: ${response.body()}")
                val route = response.body()!!.routes[0]
                setPointsRoute(route.geometry.coordinates)
                state.update {
                    it.copy(
                        distancia = route.distance / 1000,
                        duracao = formatarDuracaoViagem(route.duration)
                    )
                }
            } else {
                Log.e("mapaViagem", "Erro ao consultar rota para a viagem: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            Log.e("mapaViagem", "Exception -> erro ao consultar rota para a viagem: ${e.message}")
        }
    }

    private fun setPointsRoute(route: List<List<Double>>) {
        val points = mutableListOf<LatLng>()

        route.map { point ->
            val latLng = LatLng(
                point[1], // latitude
                point[0], // longitude
            )
            points.add(latLng)
        }

        state.update {
            it.copy(
                routePoints = points
            )
        }
    }

    private fun formatarDuracaoViagem(duration: Double): String {
        // Converte a duração de segundos para horas e minutos
        val totalMinutos = (duration / 60).toInt()
        val horas = totalMinutos / 60
        val minutos = totalMinutos % 60

        // Constrói a string baseada nos valores
        return buildString {
            if (horas > 0) append("$horas h")
            if (minutos > 0) append("${if (horas > 0) " " else ""}$minutos min")
        }
    }

    fun setControlVariablesToFalse() {
        state.update { it.copy(isError = false) }
    }

    private suspend fun setDetalhesViagemComFotoVerificada(viagemData: ViagemDetalhesListagemDto): ViagemDetalhesListagemDto {
        return withContext(Dispatchers.IO) {
            val motoristaValidado = validarFotoMotorista(viagemData.motorista)
            viagemData.copy(motorista = motoristaValidado)
        }
    }

    private suspend fun validarFotoMotorista(motorista: UsuarioSimplesListagemDto): UsuarioSimplesListagemDto {
        return motorista.copy(isFotoValida = isUrlFotoUserValida(motorista.fotoUrl))
    }

    private suspend fun loadBitmapDescriptor(context: Context, fotoUrl: String): BitmapDescriptor {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(fotoUrl)
            .allowHardware(false) // Garante que o Bitmap seja manipulável
            .build()

        val result = loader.execute(request)
        val bitmap = (result.drawable as? BitmapDrawable)?.bitmap
        val styledBitmap = getStyledBitmap(bitmap!!)
        return styledBitmap.let {
            val resizedBitmap = Bitmap.createScaledBitmap(it, 120, 120, false)
            BitmapDescriptorFactory.fromBitmap(resizedBitmap)
        }
    }

    private fun getStyledBitmap(bitmap: Bitmap): Bitmap {
        val whiteColor = Color.WHITE // Cor do espaço branco

        val size = min(bitmap.width, bitmap.height) // Tamanho do círculo (menor lado)
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = Paint()
        paint.isAntiAlias = true

        // Desenhar o círculo base
        val circleRadius = size / 2f
        canvas.drawCircle(circleRadius, circleRadius, circleRadius, paint)

        // Aplicar "ContentScale.Crop" na imagem
        val scale = max(size.toFloat() / bitmap.width, size.toFloat() / bitmap.height)
        val scaledWidth = bitmap.width * scale
        val scaledHeight = bitmap.height * scale
        val left = (size - scaledWidth) / 2f
        val top = (size - scaledHeight) / 2f

        val rect = RectF(left, top, left + scaledWidth, top + scaledHeight)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, null, rect, paint)

        // Desenhar o círculo branco (espaço entre imagem e borda)
        val whitePaint = Paint()
        whitePaint.isAntiAlias = true
        whitePaint.color = Color.parseColor("#E6AD47")
        whitePaint.style = Paint.Style.STROKE
        whitePaint.strokeWidth = 30f
        canvas.drawCircle(circleRadius, circleRadius, circleRadius - 30f / 2, whitePaint)

        // Desenhar a borda ao redor
        val borderPaint = Paint()
        borderPaint.isAntiAlias = true
        borderPaint.color = whiteColor
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = 15f
        canvas.drawCircle(
            circleRadius,
            circleRadius,
            circleRadius - 30f - 15f / 2,
            borderPaint
        )

        return output
    }

    suspend fun setFotoUserBitmap(context: Context) {
        state.update {
            it.copy(
                fotoUserAsBitmap = loadBitmapDescriptor(
                    context = context,
                    fotoUrl = viagemData.value!!.motorista.fotoUrl
                )
            )
        }
    }
}