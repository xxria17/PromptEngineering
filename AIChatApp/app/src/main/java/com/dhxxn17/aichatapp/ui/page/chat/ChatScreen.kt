package com.dhxxn17.aichatapp.ui.page.chat

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dhxxn17.aichatapp.R
import com.dhxxn17.aichatapp.data.entity.ROLE
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    id: Int
) {
    val viewModel: ChatViewModel = hiltViewModel()

    if (id != 0) {
        viewModel.requestHistory(id)
    }

    var isShowDialog by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Effect(viewModel)

    if (isShowDialog) {
        AlertDialog(
            onDismissRequest = { isShowDialog = false },
            title = {
                Text(text = "내용을 지우시겠습니까??")
            },
            text = {
                Text(text = "전체 내역이 삭제됩니다.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.sendAction(ChatContract.ChatUiAction.ClearData)
                        isShowDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text("삭제")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { isShowDialog = false }
                ) {
                    Text("취소")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(60.dp)
                .padding(10.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "AI Chat",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 30.dp)
                    .weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.eraser),
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
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

                if (_chat.role == ROLE.USER.text) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            MyChatItem(message = _chat.content)
                        }
                    }
                } else {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            AiChatItem(message = _chat.content)
                        }
                    }
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
                    focusManager.clearFocus()
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
                    )
                )
            }
        )
    }
}

@Composable
fun AiChatItem(
    message: String
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .background(
                color = Color(0xffE9EBEE), shape = RoundedCornerShape(
                    bottomEnd = 30.dp, topEnd = 30.dp, topStart = 30.dp
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
fun MyChatItem(
    message: String
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xffF54EA2),
                        Color(0xffFF7676)
                    )
                ), shape = RoundedCornerShape(
                    topStart = 30.dp, topEnd = 30.dp, bottomStart = 30.dp
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

@Composable
fun Effect(viewModel: ChatViewModel) {
    val context = LocalContext.current

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.onEach { _effect ->
            when (_effect) {
                is ChatContract.ChatUiEffect.ShowToast -> {
                    Toast.makeText(context, _effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }.collect()
    }
}