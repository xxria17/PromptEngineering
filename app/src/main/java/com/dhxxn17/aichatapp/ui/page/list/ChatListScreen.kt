package com.dhxxn17.aichatapp.ui.page.list

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.dhxxn17.aichatapp.R
import com.dhxxn17.aichatapp.ui.page.Screens

@Composable
fun ChatListScreen(navController: NavController) {
    val viewModel : ChatListViewModel = hiltViewModel()
    val state = viewModel.state
    val lifecycleOwner = LocalLifecycleOwner.current

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
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
                    _chatList.forEach { _data ->
                        item {
                            ListItem(
                                title = _data.title,
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(key = "historyId", value = _data.id)
                                    navController.navigate(Screens.ChatScreen.route)
                                }
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(end = 10.dp, bottom = 20.dp)
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set(key = "historyId", value = 0)
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
}

@Composable
private fun ListItem(
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(15.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color(0xff616771),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}