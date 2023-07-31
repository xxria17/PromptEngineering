package com.dhxxn17.aichatapp.ui.page

import com.dhxxn17.aichatapp.ui.base.BaseUiAction
import com.dhxxn17.aichatapp.ui.base.BaseUiEffect
import com.dhxxn17.aichatapp.ui.base.BaseUiState
import com.dhxxn17.aichatapp.ui.base.ChatState
import com.dhxxn17.aichatapp.ui.base.ChatStateList

class ChatContract {

    data class ChatUiState(
        val chatList: ChatStateList<Pair<String, String>>,
        val myInput: ChatState<String>
    ): BaseUiState

    sealed class ChatUiAction: BaseUiAction {
        data class InputMessage(val message: String): ChatUiAction()

        object RequestMessage: ChatUiAction()

        object BackToList: ChatUiAction()
    }

    sealed class ChatUiEffect: BaseUiEffect {
        data class ShowToast(val message: String): ChatUiEffect()
    }

}