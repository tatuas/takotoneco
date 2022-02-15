package com.tatuas.takotoneco.ui

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatuas.takotoneco.model.LoadState
import com.tatuas.takotoneco.model.UserItemList
import com.tatuas.takotoneco.usecase.UserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UserListUseCase,
) : ViewModel(), DefaultLifecycleObserver {

    private val _loadStateFlow: MutableStateFlow<LoadState<UserItemList>> =
        MutableStateFlow(LoadState.None)
    val loadStateFlow: StateFlow<LoadState<UserItemList>> = _loadStateFlow

    init {
        loadFirstUserList()
    }

    fun loadFirstUserList() {
        viewModelScope.launch {
            useCase.getUserItemList().collect {
                _loadStateFlow.value = it
            }
        }
    }

    fun loadPagingUserList(current: UserItemList) {
        viewModelScope.launch {
            useCase.getNextUserItemList(current).collect {
                _loadStateFlow.value = it
            }
        }
    }
}
