package com.dhxxn17.aichatapp.ui.page.list

import com.dhxxn17.aichatapp.data.entity.ChatData
import com.dhxxn17.aichatapp.ui.base.BaseUiAction
import com.dhxxn17.aichatapp.ui.base.BaseUiEffect
import com.dhxxn17.aichatapp.ui.base.BaseUiState
import com.dhxxn17.aichatapp.ui.base.ChatStateList

class ChatListContract {

    data class ChatListUiState(
        val chatList: ChatStateList<ChatData>
    ): BaseUiState

    sealed class ChatListUiAction: BaseUiAction {
        data class deleteChatData(val data: ChatData): ChatListUiAction()
    }

    sealed class ChatListUiEffect: BaseUiEffect {
        data class ShowToast(val msg: String): ChatListUiEffect()
    }
}