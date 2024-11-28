package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.carro.CarroListagemDto
import com.example.caronaapp.data.dto.viagem.Coordenadas
import com.example.caronaapp.data.dto.viagem.ViagemCriacaoDto
import com.example.caronaapp.data.repositories.CarroRepositoryImpl
import com.example.caronaapp.data.repositories.GoogleMapsRepositoryImpl
import com.example.caronaapp.data.repositories.MapboxRepositoryImpl
import com.example.caronaapp.data.repositories.ViagemRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.presentation.screens.oferecer_viagem.OferecerViagemEnderecoField
import com.example.caronaapp.presentation.screens.oferecer_viagem.OferecerViagemField
import com.example.caronaapp.presentation.screens.oferecer_viagem.OferecerViagemState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class OferecerViagemViewModel(
    private val viagemRepository: ViagemRepositoryImpl,
    private val carroRepository: CarroRepositoryImpl,
    private val dataStoreManager: DataStoreManager,
    private val googleMapsRepository: GoogleMapsRepositoryImpl,
    private val mapboxRepository: MapboxRepositoryImpl
) : ViewModel() {
    val perfilUser = MutableStateFlow("")
    val generoUser = MutableStateFlow("")

    init {
        viewModelScope.launch {
            perfilUser.update { dataStoreManager.getPerfilUser() ?: "" }
            generoUser.update { dataStoreManager.getGeneroUser() ?: "" }
        }
        getCarrosUser()
    }

    private val viagemData = MutableStateFlow(ViagemCriacaoDto())
    val state = MutableStateFlow(OferecerViagemState())

    val etapaAtual = MutableStateFlow(1)

    val carros = MutableStateFlow<List<CarroListagemDto>?>(null)

    val isLoading = MutableStateFlow(false)
    val isSuccessful = MutableStateFlow(false)
    val isError = MutableStateFlow(false)
    val idCreatedViagem = MutableStateFlow(0)

    private val _pontoPartida = MutableStateFlow("")
    val pontoPartida = _pontoPartida.asStateFlow()

    private val _pontoChegada = MutableStateFlow("")
    val pontoChegada = _pontoChegada.asStateFlow()

    @OptIn(FlowPreview::class)
    val pontoPartidaResults = pontoPartida
        .debounce(700)
        .filter { it.isNotEmpty() }
        .distinctUntilChanged() // Ignore valores iguais consecutivos
        .onEach { query ->
            searchAddress(query, true)
        }
        .launchIn(viewModelScope)

    @OptIn(FlowPreview::class)
    val pontoChegadaResults = pontoChegada
        .debounce(700)
        .filter { it.isNotEmpty() }
        .distinctUntilChanged() // Ignore valores iguais consecutivos
        .onEach { query ->
            searchAddress(query, false)
        }
        .launchIn(viewModelScope)

    private fun getCarrosUser() {
        viewModelScope.launch {
            try {
                val idUser = dataStoreManager.getIdUser()
                val response = carroRepository.findByUsuarioId(idUser!!)

                if (response.isSuccessful) {
                    Log.i(
                        "oferecerCarona",
                        "Sucesso ao buscar carros do usuário: ${response.body()}"
                    )
                    if (response.code() == 200) {
                        carros.update { response.body() }
                        viagemData.update {
                            it.copy(carroId = response.body()?.get(0)?.id ?: 0)
                        }
                        state.update {
                            it.copy(
                                carro = "${response.body()?.get(0)?.marca} " +
                                        "${response.body()?.get(0)?.modelo}"
                            )
                        }
                    }
                } else {
                    Log.e(
                        "oferecerCarona",
                        "Erro ao buscar carros do usuário: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "oferecerCarona",
                    "Exception -> erro ao buscar carros do usuário: ${e.printStackTrace()}"
                )
            }
        }
    }

    fun onChangeEvent(field: OferecerViagemField) {
        when (field) {
            is OferecerViagemField.PontoPartida -> {
                state.update {
                    it.copy(pontoPartida = field.value)
                }
                _pontoPartida.update { field.value }
            }

            is OferecerViagemField.PontoDestino -> {
                state.update {
                    it.copy(pontoDestino = field.value)
                }
                _pontoChegada.update { field.value }
            }

            is OferecerViagemField.Data -> {
                viagemData.update {
                    it.copy(data = field.value.format(DateTimeFormatter.ISO_LOCAL_DATE))
                }

                state.update {
                    it.copy(data = field.value)
                }
            }

            is OferecerViagemField.HorarioPartida -> {
                viagemData.update {
                    it.copy(horarioPartida = field.value.format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                }

                state.update {
                    it.copy(hora = field.value)
                }
            }

            is OferecerViagemField.CapacidadePassageiros -> {
                viagemData.update {
                    it.copy(capacidadePassageiros = field.value)
                }

                state.update {
                    it.copy(capacidadePassageiros = field.value)
                }
            }

            is OferecerViagemField.Preco -> {
                viagemData.update {
                    it.copy(preco = field.value)
                }

                state.update {
                    it.copy(preco = field.value)
                }
            }

            is OferecerViagemField.ApenasMulheres -> {
                viagemData.update {
                    it.copy(apenasMulheres = field.value)
                }

                state.update {
                    it.copy(apenasMulheres = field.value)
                }
            }

            is OferecerViagemField.Carro -> {
                viagemData.update {
                    it.copy(carroId = field.value.id)
                }

                state.update {
                    it.copy(
                        carro = "${field.value.marca} ${field.value.modelo}",
                        isCarrosDropdownExpanded = false
                    )
                }
            }
        }
    }

    private fun searchAddress(address: String, isPartida: Boolean) {
        viewModelScope.launch {
            try {
                val response = googleMapsRepository.getGeocode(address)

                if (response.isSuccessful) {
                    Log.i("geocoder", "Sucesso no Geocode: ${response.body()}")
                    Log.i("geocoder", "Endereços retornados: ${response.body()!!.results.size}")
                    Log.i("geocoder", "_________________________________________________________")

                    if (isPartida) {
                        state.update {
                            it.copy(
                                isPartidaDropdownExpanded = true,
                                resultsPontoPartida = response.body()!!.results
                            )
                        }
                    } else {
                        state.update {
                            it.copy(
                                isDestinoDropdownExpanded = true,
                                resultsPontoDestino = response.body()!!.results
                            )
                        }
                    }
                } else {
                    Log.e("geocoder", "Erro no Geocode: ${response.errorBody()}")
                    Log.e("geocoder", "_________________________________________________________")

                }
            } catch (e: Exception) {
                Log.e("geocoder", "Exception -> erro ao realizar geocode: ${e.printStackTrace()}")
                Log.e("geocoder", "Erro: ${e.message}")
                Log.e("geocoder", "_________________________________________________________")
            }
        }
    }

    fun onEnderecoClick(field: OferecerViagemEnderecoField) {
        when (field) {
            is OferecerViagemEnderecoField.PontoPartida -> {
                viagemData.update {
                    it.copy(
                        pontoPartida = Coordenadas(
                            field.value.geometry.location.latitude,
                            field.value.geometry.location.longitude,
                        )
                    )
                }

                state.update {
                    it.copy(
                        pontoPartida = field.value.formatted_address,
                        isPartidaDropdownExpanded = false
                    )
                }
            }

            is OferecerViagemEnderecoField.PontoDestino -> {
                viagemData.update {
                    it.copy(
                        pontoDestino = Coordenadas(
                            field.value.geometry.location.latitude,
                            field.value.geometry.location.longitude,
                        )
                    )
                }

                state.update {
                    it.copy(
                        pontoDestino = field.value.formatted_address,
                        isDestinoDropdownExpanded = false
                    )
                }

            }
        }
    }

    fun handleNextClick() {
        when (etapaAtual.value) {
            1 -> etapaAtual.update { 2 }
            2 -> etapaAtual.update { 3 }
            3 -> saveViagem()
        }
    }

    fun handleBackClick() {
        when (etapaAtual.value) {
            2 -> etapaAtual.update { 1 }
            3 -> etapaAtual.update { 2 }
        }
    }

    fun onCarrosDropdownClick() {
        state.update { it.copy(isCarrosDropdownExpanded = true) }
    }

    fun onCarrosDismissRequest() {
        state.update { it.copy(isCarrosDropdownExpanded = false) }
    }

    private fun saveViagem() {
        viewModelScope.launch {
            isLoading.update { true }
            try {
                calculateDurationAndSetHorarioChegada()
                viagemData.update { it.copy(motoristaId = dataStoreManager.getIdUser()!!) }
                Log.i("oferecerCarona", "ViagemCriacaoDto: ${viagemData.value}")

                val response = viagemRepository.save(viagemData.value)

                if (response.isSuccessful) {
                    Log.i(
                        "oferecerViagem",
                        "Sucesso ao salvar viagem: ${response.body()}"
                    )
                    isSuccessful.update { true }
                    idCreatedViagem.update { response.body()?.id ?: 0 }
                } else {
                    Log.e(
                        "oferecerViagem",
                        "Erro ao salvar viagem: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "oferecerViagem",
                    "Exception -> erro ao salvar viagem: ${e.message}"
                )
            } finally {
                isLoading.update { false }
            }
        }
    }

    private suspend fun calculateDurationAndSetHorarioChegada() {
        try {
            val response = mapboxRepository.getDistanceBetweenPlaces(
                viagemData.value.pontoPartida.latitude,
                viagemData.value.pontoPartida.longitude,
                viagemData.value.pontoDestino.latitude,
                viagemData.value.pontoDestino.longitude,
            )

            if (response.isSuccessful) {
                Log.i(
                    "mapbox",
                    "Sucesso ao consultar informações no Mapbox: ${response.body()}"
                )
                val horarioChegada =
                    calculateHorarioChegada(
                        state.value.hora,
                        response.body()?.routes?.get(0)?.duration ?: 0.0
                    )

                viagemData.update {
                    it.copy(horarioChegada = horarioChegada.format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                }
            } else {
                Log.e(
                    "mapbox",
                    "Erro ao consultar informações no Mapbox: ${response.errorBody()}"
                )
            }
        } catch (e: Exception) {
            Log.e(
                "mapbox",
                "Exception -> erro ao consultar informações no Mapbox: ${e.message}"
            )
        }
    }

    private fun calculateHorarioChegada(
        horarioPartida: LocalTime,
        duration: Double
    ): LocalTime {
        // Converte a duração de segundos para um objeto Duration
        val durationInSeconds = duration.toLong()
        val durationAsDuration = Duration.ofSeconds(durationInSeconds)

        // Calcula o horário de chegada
        var horarioDeChegada = horarioPartida.plus(durationAsDuration)

        // Arredonda os minutos para o próximo múltiplo de 10
        val minutos = horarioDeChegada.minute
        val minutosArredondados = if (minutos % 10 == 0) minutos else ((minutos / 10) + 1) * 10

        // Ajusta o horário de chegada com o arredondamento
        horarioDeChegada = horarioDeChegada.withMinute(0).plusMinutes(minutosArredondados.toLong())

        Log.i(
            "mapbox",
            "horarioChegada: $horarioDeChegada"
        )
        return horarioDeChegada
    }

    fun setIsSuccessfulToFalse() {
        isSuccessful.update { false }
    }

    fun setIsErrorToFalse() {
        isError.update { false }
    }
}