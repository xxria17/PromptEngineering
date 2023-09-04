package com.dhxxn17.helpyou.ui.page.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dhxxn17.helpyou.data.model.ROLE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {

    val viewModel: ChatViewModel = hiltViewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffF4F6FD))
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(vertical = 60.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
                .align(Alignment.BottomCenter)
        ) {
            viewModel.state.messageList.getValue(this).forEach { _chat ->

                if (_chat.role == ROLE.USER) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            MyChatItem(message = _chat.message)
                        }
                    }
                } else {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            if (_chat.message.contains("<").not()) {
                                LoadingChatItem(message = _chat.message)
                            } else {
                                AiChatItem(message = _chat.message)
                            }
                            
                        }
                    }
                }

            }
        }

        BasicTextField(
            value = viewModel.state.input.getValue(this),
            onValueChange = {
                viewModel.sendAction(
                    ChatContract.ChatUiAction.InputMessage(it)
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onSend = {
                    viewModel.sendAction(ChatContract.ChatUiAction.RequestChat)
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
                    value =  viewModel.state.input.getValue(this),
                    innerTextField = _innerTextField,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = MutableInteractionSource(),
                    contentPadding = PaddingValues(horizontal = 10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        disabledTextColor = Color.Gray,
                        cursorColor = Color.Blue,
                        errorCursorColor = Color.Red,
                        containerColor = Color(0xffececec),
                        placeholderColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            "노래 가사를 입력해 보세요 !",
                            color = Color.LightGray
                        )
                    }
                )
            }
        )
    }
}

@Composable
fun LoadingChatItem(
    message: String
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .background(
                color = Color.White, shape = RoundedCornerShape(15.dp)
            )
            .padding(10.dp)
    ) {
        Text(
            text = message,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@Composable
fun AiChatItem(
    message: String
) {
    val titleIdx = message.indexOf("<")

    Box(
        modifier = Modifier
            .padding(10.dp)
            .background(
                color = Color.White, shape = RoundedCornerShape(15.dp)
            )
            .padding(10.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                append("${message.substring(0, titleIdx)}\n")
                withStyle(
                    SpanStyle(
                        color = Color.LightGray
                    )
                ) {
                    append(message.substring(titleIdx))
                }
            },
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@Composable
fun MyChatItem(
    message: String
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .background(
                color = Color(0xffD37AA6),
                shape = RoundedCornerShape(15.dp)
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