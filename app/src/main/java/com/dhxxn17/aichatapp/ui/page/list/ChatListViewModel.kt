package com.dhxxn17.aichatapp.ui.page.list

import androidx.lifecycle.viewModelScope
import com.dhxxn17.aichatapp.data.repository.ChatRepository
import com.dhxxn17.aichatapp.ui.base.BaseUiAction
import com.dhxxn17.aichatapp.ui.base.BaseUiState
import com.dhxxn17.aichatapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val repository: ChatRepository
) : BaseViewModel() {

    val state: ChatListContract.ChatListUiState
        get() = state()

    override fun loadData() {
        requestChatList()
    }

    private fun requestChatList() {
        viewModelScope.launch {
            val chatList = repository.getHistoryList()
            state.chatList.sendState {
               chatList
            }
        }
    }

    private fun deleteChatData(id: Int) {
        viewModelScope.launch {
            repository.deleteChatData(id)
            repository.deleteMessagesByChatDataId(id)

            sendEffect(ChatListContract.ChatListUiEffect.ShowToast("삭제되었습니다."))
        }
    }

    override fun initialData() {
        state.chatList.sendState { emptyList() }
    }

    override fun handleEvents(action: BaseUiAction) {
        when(action) {
            is ChatListContract.ChatListUiAction.DeleteChatData -> {
                deleteChatData(action.id)
            }
        }
    }

    override fun initialState(): BaseUiState {
        return ChatListContract.ChatListUiState(
            chatList = mutableChatStateListOf(emptyList())
        )
    }

}