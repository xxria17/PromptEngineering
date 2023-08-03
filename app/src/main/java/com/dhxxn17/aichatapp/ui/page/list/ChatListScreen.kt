package com.dhxxn17.aichatapp.ui.page.list

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.dhxxn17.aichatapp.R
import com.dhxxn17.aichatapp.ui.page.Screens
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun ChatListScreen(navController: NavController) {
    val viewModel : ChatListViewModel = hiltViewModel()
    val state = viewModel.state
    val lifecycleOwner = LocalLifecycleOwner.current
    var isShowDialog by remember  { mutableStateOf(false) }
    var selectedId by remember { mutableStateOf(0) }
    val context = LocalContext.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, _event ->
            when(_event) {
                Lifecycle.Event.ON_RESUME -> {
                    // 화면 진입시마다 데이터 불러오기
                    viewModel.sendAction(ChatListContract.ChatListUiAction.Refresh)
                }
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Effect(viewModel)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        Text(
            text = "AI Chat",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
        ) {

            state.chatList.getValue(this).let { _chatList ->
                if (_chatList.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 350.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "새로운 대화를 생성해보세요 !",
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                } else {
                    _chatList.forEachIndexed { _index, _data ->
                        item {
                            ListItem(
                                title = _data.title,
                                onTap = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(key = "historyId", value = _data.id)
                                    navController.navigate(Screens.ChatScreen.route)
                                },
                                onLongPress = {
                                    longPressHaptic(context)
                                    isShowDialog = true
                                    selectedId = _data.id
                                }
                            )
                        }

                        if (_index < _chatList.size - 1) {
                            item {
                                Divider(
                                    color = Color.LightGray,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    thickness = 1.dp
                                )
                            }
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(end = 10.dp, bottom = 20.dp)
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "historyId",
                        value = 0
                    )
                    navController.navigate(Screens.ChatScreen.route)
                }
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xffF54EA2),
                            Color(0xffFF7676)
                        ),
                    ),
                    shape = CircleShape
                )
                .align(Alignment.BottomEnd)
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }

    if (isShowDialog) {
        AlertDialog(
            onDismissRequest = { isShowDialog = false },
            text = {
                Text(text = "해당 대화를 삭제하시겠습니까?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.sendAction(ChatListContract.ChatListUiAction.DeleteChatData(selectedId))
                        isShowDialog = false
                        selectedId = 0
                    }
                ) {
                    Text("삭제")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isShowDialog = false
                        selectedId = 0
                    }
                ) {
                    Text("취소")
                }
            }
        )
    }
}

@Composable
private fun ListItem(
    title: String,
    onTap: () -> Unit,
    onLongPress: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onLongPress.invoke()
                    },
                    onTap = {
                        onTap.invoke()
                    }
                )
            }
            .padding(15.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private fun longPressHaptic(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val vibration = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
        vibrator.vibrate(vibration)
    } else {
        vibrator.vibrate(100L)
    }
}

@Composable
private fun Effect(viewModel: ChatListViewModel) {
    val context = LocalContext.current

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.onEach {
            when (it) {
                is ChatListContract.ChatListUiEffect.ShowToast -> {
                   Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }.collect()
    }
}