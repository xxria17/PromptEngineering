package com.dhxxn17.helpyou.ui.page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhxxn17.helpyou.data.model.Chat
import com.dhxxn17.helpyou.data.model.ChatRequest
import com.dhxxn17.helpyou.data.model.ROLE
import com.dhxxn17.helpyou.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {

    private val _messageList: MutableLiveData<List<Chat>> = MutableLiveData()
    val messageList: LiveData<List<Chat>> = _messageList

    private val _input: MutableLiveData<String> = MutableLiveData()
    val input: LiveData<String> = _input

    fun sendChatMessage(message: String) {

        val list = mutableListOf<Chat>()
        list.add(Chat(message, ROLE.USER))
        _messageList.value = list

        val today = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyyMMdd")
        val date = format.format(today)

        viewModelScope.launch {
            chatRepository.sendChat(
                ChatRequest(message, date)
            ).traits.greeting.forEach {
                list.add(
                    Chat(it.value, ROLE.AI)
                )
                _messageList.value = list
            }
        }
    }

    fun inputMessage(message: String) {
        _input.value = message
    }
}