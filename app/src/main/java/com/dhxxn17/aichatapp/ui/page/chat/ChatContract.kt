package com.dhxxn17.aichatapp.ui.page.chat

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
        /* 메시지 입력 */
        data class InputMessage(val message: String): ChatUiAction()

        /* 메시지 전송 */
        object RequestMessage: ChatUiAction()

        /* 메시지 이력 삭제 */
        object ClearData: ChatUiAction()
    }

    sealed class ChatUiEffect: BaseUiEffect {
        data class ShowToast(val message: String): ChatUiEffect()
    }

}