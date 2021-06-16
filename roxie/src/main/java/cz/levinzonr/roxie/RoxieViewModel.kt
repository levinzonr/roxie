package cz.levinzonr.roxie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Store which manages business data and viewState.
 */
abstract class RoxieViewModel<A : BaseAction, S : BaseState, C : BaseChange>() :
    ViewModel() {
    private val changes: MutableSharedFlow<C> = MutableSharedFlow()

    protected abstract val initialState: S
    protected abstract val reducer: Reducer<S, C>

    protected val currentState: S
        get() = _stateFlow.value


    private val tag by lazy { javaClass.simpleName }

    /**
     * Returns the current viewState. It is equal to the last value returned by the store's reducer.
     */

    private val _stateFlow by lazy { MutableStateFlow<S>(this.initialState) }


    val stateFlow: Flow<S> = _stateFlow

    protected fun startActionsObserver() {
        changes.scan(initialState, reducer)
            .distinctUntilChanged()
            .onEach { _stateFlow.emit(it) }
            .launchIn(viewModelScope)

    }

    /**
     * Dispatches an action. This is the only way to trigger a viewState change.
     */
    fun dispatch(action: A) {
        emitAction(action)
            .onEach { changes.emit(it) }
            .launchIn(viewModelScope)
    }

    protected abstract fun emitAction(action: A): Flow<C>
}