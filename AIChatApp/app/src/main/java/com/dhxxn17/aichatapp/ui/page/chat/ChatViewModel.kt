package com.dhxxn17.aichatapp.ui.page.chat

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dhxxn17.aichatapp.data.entity.Chat
import com.dhxxn17.aichatapp.data.entity.History
import com.dhxxn17.aichatapp.data.entity.Message
import com.dhxxn17.aichatapp.data.entity.ROLE
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
            messageList = mutableChatStateListOf(emptyList()),
            historyId = mutableChatStateOf(0),
            chatList = mutableChatStateListOf(emptyList())
        )
    }

    fun requestHistory(id: Int) {
        // 이전 대화 내역 불러오기
        viewModelScope.launch {
            val messageList = chatRepository.getMessageList(id)
            state.historyId.sendState { id }
            state.messageList.sendState { messageList }
        }
    }

    private fun requestChat(message: String) {
        viewModelScope.launch {

            if (state.historyId.value() < 1) {
                /* 첫 생성일 경우 */
                val chatData = History(title = message)
                val chatId = chatRepository.saveChatData(chatData).toInt()

                state.historyId.sendState { chatId }
            }

            val userMessageData = Message(
                role = ROLE.USER.text,
                content = message,
                historyId = state.historyId.value()
            )

            // ai response를 받기 전까지 임시로 보여줄 대화
            val loadingMessageData = Message(
                role = ROLE.ASSISTANT.text,
                content = ". . .",
                historyId = state.historyId.value()
            )

            val userChatData = Chat(
                role = ROLE.USER.text,
                content = message
            )

            val tempList = state.messageList.value().toMutableList()
            tempList.add(userMessageData)
            tempList.add(loadingMessageData)
            state.messageList.sendState { tempList }

            val tempChatList = state.chatList.value().toMutableList()
            tempChatList.add(userChatData)

            val response = chatRepository.sendChat(tempChatList)
            when (response) {
                is NetworkResponse.Success -> {
                    val reply = response.body.choices[0].messages.content
                    val replyMessage = Message(
                        role = ROLE.ASSISTANT.text,
                        content = reply,
                        historyId = state.historyId.value()
                    )
                    val replyChat = Chat(
                        role = ROLE.ASSISTANT.text,
                        content = reply
                    )
                    val copyList = state.messageList.value().toMutableList()
                    copyList.removeAt(state.messageList.value().size - 1)
                    copyList.add(replyMessage)

                    state.messageList.sendState { copyList }

                    // 내부 db에 저장
                    chatRepository.saveMessage(userMessageData)
                    chatRepository.saveMessage(replyMessage)

                    val chatList = listOf<Chat>(userChatData, replyChat)
                    state.chatList.sendState { chatList }

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
        state.messageList.sendState { emptyList() }
        val chatId = state.historyId.value()

        viewModelScope.launch {
            chatRepository.deleteChatData(chatId)
            chatRepository.deleteMessagesByChatDataId(chatId)

            state.historyId.sendState { -1 }
            sendEffect(ChatContract.ChatUiEffect.ShowToast("삭제되었습니다."))
        }
    }
}