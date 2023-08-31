package com.dhxxn17.helpyou.ui.page.chat

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dhxxn17.helpyou.data.model.Chat
import com.dhxxn17.helpyou.data.model.ChatRequest
import com.dhxxn17.helpyou.data.model.ROLE
import com.dhxxn17.helpyou.data.repository.ChatRepository
import com.dhxxn17.helpyou.ui.base.BaseUiAction
import com.dhxxn17.helpyou.ui.base.BaseUiState
import com.dhxxn17.helpyou.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): BaseViewModel() {

    val state: ChatContract.ChatUiState
        get() = state()

    val effect: Flow<ChatContract.ChatUiEffect>
        get() = effect()
    private fun sendChatMessage(message: String) {
        viewModelScope.launch {
            val today = Calendar.getInstance().time
            val format = SimpleDateFormat("yyyyMMdd")
            val date = format.format(today)

            val userMessage = Chat(message, ROLE.USER)
            val loadingMessage = Chat(". . .", ROLE.AI)

            val tempList = state.messageList.value().toMutableList()
            tempList.add(userMessage)
            tempList.add(loadingMessage)
            state.messageList.sendState { tempList }

            val response = chatRepository.sendChat(ChatRequest(message, date))

            if (response.traits.greeting.isNotEmpty()) {
                val aiResponse = response.traits.greeting[0].value
                val replyMessage = Chat(
                    aiResponse, ROLE.AI
                )
                val copyList = state.messageList.value().toMutableList()
                copyList.removeAt(state.messageList.value().size - 1)
                copyList.add(replyMessage)

                state.messageList.sendState { copyList }
            }

        }
    }

    private fun inputMessage(message: String) {
        state.input.sendState { message }
    }

    override fun loadData() {

    }

    override fun initialData() {
        state.input.sendState { "" }
    }

    override fun handleEvents(action: BaseUiAction) {
       when(action) {
           is ChatContract.ChatUiAction.InputMessage -> {
               inputMessage(action.message)
           }
           is ChatContract.ChatUiAction.RequestChat -> {
               sendChatMessage(state.input.value())
               state.input.sendState { "" }
           }
       }
    }

    override fun initialState(): BaseUiState {
        return ChatContract.ChatUiState(
            input = mutableAIStateOf(""),
            messageList = mutableAIStateListOf(emptyList())
        )
    }
}