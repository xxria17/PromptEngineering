package com.dhxxn17.aichatapp.ui.page.chat

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dhxxn17.aichatapp.data.entity.ChatData
import com.dhxxn17.aichatapp.data.entity.Messages
import com.dhxxn17.aichatapp.data.remote.NetworkResponse
import com.dhxxn17.aichatapp.data.repository.ChatRepository
import com.dhxxn17.aichatapp.ui.base.BaseUiAction
import com.dhxxn17.aichatapp.ui.base.BaseUiState
import com.dhxxn17.aichatapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): BaseViewModel() {

    val state: ChatContract.ChatUiState
        get() = state()

    val effect: Flow<ChatContract.ChatUiEffect>
        get() = effect()

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

            is ChatContract.ChatUiAction.ClearData -> {
                deleteChat()
            }
        }
    }

    override fun initialState(): BaseUiState {
        return ChatContract.ChatUiState(
            myInput = mutableChatStateOf(""),
            chatList = mutableChatStateListOf(emptyList()),
            chatID = mutableChatStateOf(0L)
        )
    }

    fun requestHistory(id: Long) {
        viewModelScope.launch {
            val response = chatRepository.requestChatHistory(id)

            state.chatID.sendState { id }
            state.chatList.sendState { response.messages }
        }
    }

    private fun requestChat(message: String) {
        viewModelScope.launch {

            if (state.chatID.value() == 0L) {
                /* 첫 생성일 경우 */
                val chatData = ChatData(title = message)
                chatRepository.saveChatData(chatData)

                state.chatID.sendState { chatData.id }
            }

            val content = Messages(
                role = "user",
                content = message,
                chatDataId = state.chatID.value()
            )

            // ai response를 받기 전까지 임시로 보여줄 대화
            val loadingContent = Messages(
                role = "assistant",
                content = ". . .",
                chatDataId = state.chatID.value()
            )

            val tempList = state.chatList.value().toMutableList()
            tempList.add(content)
            tempList.add(loadingContent)
            state.chatList.sendState { tempList }

            val response = chatRepository.fetchChat(content)
            when (response) {
                is NetworkResponse.Success -> {
                    val reply = Messages(
                        role = "assistant",
                        content = response.body.choices[0].messages.content,
                        chatDataId = state.chatID.value()
                    )
                    val copyList = state.chatList.value().toMutableList()
                    copyList.removeAt(state.chatList.value().size - 1)
                    copyList.add(reply)
                    state.chatList.sendState { copyList }

                    // 내부 db에 저장
                    if (state.chatList.value().size < 3) {
                        // 대화 초기 생성
                        chatRepository.saveMessage(state.chatList.value())
                    } else {
                        chatRepository.updateMessages(state.chatList.value())
                    }
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

    private fun deleteChat() {
        state.chatList.sendState { emptyList() }
        val chatId = state.chatID.value()

        viewModelScope.launch {
            chatRepository.deleteChatData(chatId)
            chatRepository.deleteMessagesByChatDataId(chatId)

            state.chatID.sendState { 0L }
            sendEffect(ChatContract.ChatUiEffect.ShowToast("삭제되었습니다."))
        }
    }
}