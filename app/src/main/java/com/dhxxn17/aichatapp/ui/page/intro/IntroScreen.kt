package com.dhxxn17.aichatapp.ui.page.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dhxxn17.aichatapp.R
import com.dhxxn17.aichatapp.ui.page.Screens

@Composable
fun IntroScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "AI CHAT",
            fontSize = 25.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = R.mipmap.ic_launcher_foreground),
            contentDescription = "",
            modifier = Modifier.size(200.dp)
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Screens.ChatListScreen.route)
                }
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xffFCE38A),
                            Color(0xffF38181)
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    ), shape = RoundedCornerShape(10.dp)
                )
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "시작하기",
                fontSize = 17.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}