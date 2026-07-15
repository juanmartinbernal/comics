package com.comicsopentrends.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/** Acción disparada desde la UI (ej. click, texto de búsqueda, scroll...). */
interface MviIntent

/** Estado inmutable que representa por completo lo que la UI debe pintar. */
interface MviState

/** Evento de un solo disparo que la UI debe consumir una única vez (navegación, snackbar...). */
interface MviEffect

/**
 * ViewModel base para implementar el patrón MVI (Model-View-Intent):
 * la UI envía [MviIntent]s con [submitIntent], el ViewModel reduce el
 * estado actual a uno nuevo con [setState] y opcionalmente emite
 * [MviEffect]s de un solo uso con [sendEffect].
 */
abstract class MviViewModel<Intent : MviIntent, State : MviState, Effect : MviEffect>(
    initialState: State,
) : ViewModel() {

    private val stateFlow = MutableStateFlow(initialState)
    val state: StateFlow<State> = stateFlow.asStateFlow()

    protected val currentState: State
        get() = stateFlow.value

    private val effectChannel = Channel<Effect>(Channel.BUFFERED)
    val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    /** Punto de entrada único desde la UI para disparar una acción. */
    fun submitIntent(intent: Intent) {
        viewModelScope.launch { handleIntent(intent) }
    }

    /** Implementado por cada feature: decide cómo reducir el estado para cada [Intent]. */
    protected abstract suspend fun handleIntent(intent: Intent)

    protected fun setState(reduce: State.() -> State) {
        stateFlow.value = currentState.reduce()
    }

    protected fun sendEffect(build: () -> Effect) {
        viewModelScope.launch { effectChannel.send(build()) }
    }
}
