package com.dhxxn17.helpyou.ui.page.chat

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dhxxn17.helpyou.R
import com.dhxxn17.helpyou.data.model.ROLE
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val viewModel: ChatViewModel = hiltViewModel()
    var isShowDialog by remember { mutableStateOf(false) }

    Effect(viewModel)

    if (isShowDialog) {
        AlertDialog(
            onDismissRequest = {isShowDialog = false},
            title = {
                Text("내용을 지우시겠습니까?")
            },
            text = {
                Text("전체 내역이 삭제됩니다.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.sendAction(ChatContract.ChatUiAction.DeleteAll)
                        isShowDialog = false
                    }
                ) {
                    Text("삭제")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isShowDialog = false
                    }
                ) {
                    Text("취소")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffF4F6FD))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, top = 10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painterResource(id = R.drawable.ic_eraser),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        isShowDialog = true
                    }
            )
        }

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

            if (viewModel.state.messageList.getValue(this).isEmpty()) {
                item {
                    Text(
                        "가사는 멜론 가사 기준으로 학습되었습니다.",
                        color = Color.LightGray,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
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

@Composable
fun Effect(viewModel: ChatViewModel) {
    val context = LocalContext.current

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.onEach {
            when (it) {
                is ChatContract.ChatUiEffect.ShowToast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }.collect()
    }
}