package com.dhxxn17.helpyou.ui.page.chat

import com.dhxxn17.helpyou.data.model.Chat
import com.dhxxn17.helpyou.ui.base.AIState
import com.dhxxn17.helpyou.ui.base.AIStateList
import com.dhxxn17.helpyou.ui.base.BaseUiAction
import com.dhxxn17.helpyou.ui.base.BaseUiEffect
import com.dhxxn17.helpyou.ui.base.BaseUiState

class ChatContract {

    data class ChatUiState(
        val messageList: AIStateList<Chat>,
        val input: AIState<String>
    ): BaseUiState

    sealed class ChatUiAction: BaseUiAction {
        data class InputMessage(val message: String): ChatUiAction()

        object RequestChat: ChatUiAction()

        object DeleteAll: ChatUiAction()
    }

    sealed class ChatUiEffect: BaseUiEffect {
        data class ShowToast(val message: String): ChatUiEffect()
    }

}