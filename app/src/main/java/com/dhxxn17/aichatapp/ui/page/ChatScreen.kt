package com.dhxxn17.aichatapp.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val viewModel: ChatViewModel = hiltViewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
        ) {
            viewModel.state.chatList.getValue(this).forEach { _chat ->
                item {
                    MyChatItem(message = _chat.first)
                }

                item {
                    AiChatItem(message = _chat.second)
                }
            }
        }

        BasicTextField(
            value = viewModel.state.myInput.getValue(this),
            onValueChange = {
                viewModel.sendAction(
                    ChatContract.ChatUiAction.InputMessage(it)
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onSend = {
                    viewModel.sendAction(ChatContract.ChatUiAction.RequestMessage)
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            ),
            decorationBox = { _innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    value = viewModel.state.myInput.getValue(this),
                    innerTextField = _innerTextField,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = MutableInteractionSource(),
                    contentPadding = PaddingValues(0.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        disabledTextColor = Color.Gray,
                        cursorColor = Color.Blue,
                        errorCursorColor = Color.Red,
                        containerColor = Color.LightGray,
                        placeholderColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }
        )
    }
}

@Composable
fun MyChatItem(
    message: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xffE9EBEE), shape = RoundedCornerShape(
                    topStart = 30.dp, topEnd = 30.dp, bottomStart = 30.dp
                )
            )
            .padding(10.dp)
    ) {
        Text(
            text = message,
            fontSize = 16.sp,
            color = Color(0xff616771)
        )
    }
}

@Composable
fun AiChatItem(
    message: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xff0078FF), shape = RoundedCornerShape(
                    bottomEnd = 30.dp, topEnd = 30.dp, bottomStart = 30.dp
                )
            )
            .padding(10.dp)
    ) {
        Text(
            text = message,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}