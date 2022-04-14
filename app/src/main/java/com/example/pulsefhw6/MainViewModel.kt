package com.example.pulsefhw6

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel: ViewModel() {

    val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState

    fun changeFirstValue(value: String) {
        _viewState.update {
            it.copy(stField = value)
        }
    }

    fun changeSecondValue(value: String) {
        _viewState.update {
            it.copy(ndField = value)
        }
    }

    fun addItemToList(pulseInfo: PulseInfo) {
        val newList = viewState.value.list.toMutableList().apply {
            add(pulseInfo)
        }
        _viewState.update {
            it.copy(list = newList)
        }
    }
}