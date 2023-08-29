package com.dhxxn17.helpyou.ui.page

import androidx.lifecycle.ViewModel
import com.dhxxn17.helpyou.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {

}