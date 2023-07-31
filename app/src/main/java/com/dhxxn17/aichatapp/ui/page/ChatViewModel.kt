package com.dhxxn17.aichatapp.ui.page

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dhxxn17.aichatapp.data.entity.Messages
import com.dhxxn17.aichatapp.data.remote.NetworkResponse
import com.dhxxn17.aichatapp.data.repository.ChatRepository
import com.dhxxn17.aichatapp.ui.base.BaseUiAction
import com.dhxxn17.aichatapp.ui.base.BaseUiState
import com.dhxxn17.aichatapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    val chatRepository: ChatRepository
): BaseViewModel() {

    val state: ChatContract.ChatUiState
        get() = state()

    init {
        initialData()
    }

    override fun loadData() {

    }

    override fun initialData() {
        with(state) {
            myInput.sendState { "" }
        }
    }

    override fun handleEvents(action: BaseUiAction) {
        when(action) {
            is ChatContract.ChatUiAction.InputMessage -> {
                state.myInput.sendState { action.message }
            }
            is ChatContract.ChatUiAction.RequestMessage -> {
                requestChat(state.myInput.value())
                state.myInput.sendState { "" }
            }
        }
    }

    override fun initialState(): BaseUiState {
        return ChatContract.ChatUiState(
            myInput = mutableChatStateOf(""),
            chatList = mutableChatStateListOf(emptyList())
        )
    }

    private fun requestChat(message: String) {
        val content = Messages(
            role = "user",
            content = message
        )

        viewModelScope.launch {
            val response = chatRepository.fetchChat(content)
            when (response) {
                is NetworkResponse.Success -> {
                    val pair = Pair(
                        message,
                        response.body.choices[0].messages.content
                    )
                    val copyList = state.chatList.value().toMutableList()
                    copyList.add(pair)
                    state.chatList.sendState { copyList }
                }
                is NetworkResponse.ApiError -> {
                    sendEffect(ChatContract.ChatUiEffect.ShowToast("${response.body.message}"))
                }
                is NetworkResponse.NetworkError -> {
                    sendEffect(ChatContract.ChatUiEffect.ShowToast("Network Error"))
                    Log.e("ChatViewModel!!!", response.error.message.toString())
                }
                is NetworkResponse.UnknownError -> {
                    Log.e("ChatViewModel!!!", response.error?.message.toString())
                }
            }
        }
    }
}