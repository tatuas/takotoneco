package com.tatuas.takotoneco.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatuas.takotoneco.model.LoadState
import com.tatuas.takotoneco.model.UserDetail
import com.tatuas.takotoneco.model.UserItem
import com.tatuas.takotoneco.usecase.UserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val useCase: UserDetailUseCase,
) : ViewModel() {

    private val _loadStateFlow: MutableStateFlow<LoadState<UserDetail>> =
        MutableStateFlow(LoadState.None)
    val loadStateFlow: StateFlow<LoadState<UserDetail>> = _loadStateFlow

    fun init(user: UserItem.Data?) {
        val current = loadStateFlow.value

        if (current is LoadState.Success || current is LoadState.Failure) return

        viewModelScope.launch {
            useCase.getDetail(user).collect {
                _loadStateFlow.value = it
            }
        }
    }

    fun reload(user: UserItem.Data?) {
        viewModelScope.launch {
            useCase.getDetail(user).collect {
                _loadStateFlow.value = it
            }
        }
    }
}
