@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.goneappforandroid.compose

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*
import com.example.goneappforandroid.*
import com.example.goneappforandroid.R
import com.example.goneappforandroid.compose.bottomappbar.CustomBottomAppBar

import com.example.goneappforandroid.ui.theme.GoneAppForAndroidTheme


@SuppressLint("RestrictedApi")
@Composable
fun BaseScreen(factory: TasksViewModelFactory,
               tasksViewModel: TasksViewModel = viewModel(factory = factory),
               context: Context, alarmManager: AlarmManager
) {
    val tasksList = tasksViewModel.tasks.collectAsState(initial = listOf())
    val topBarTitle = remember{ mutableStateOf("") }
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val lazyState = rememberLazyListState()
    setRepeatAlarm(
        alarmManager = alarmManager,
        context = context
    )
    GoneAppForAndroidTheme {
        val confettiGo = remember { mutableStateOf(false)}
        var bottomPadding by remember { mutableStateOf(0.dp)}


        Scaffold(
            topBar = {
                CustomTopAppBar(titleState = topBarTitle,
                    scrollBehavior = scrollBehavior,
                    navHostController = navController)
            },
            bottomBar = { CustomBottomAppBar(navHostController = navController) }
        ) {
            bottomPadding = it.calculateBottomPadding()
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())) {
                NavGraph(navHostController = navController, tasksList = tasksList, tasksViewModel = tasksViewModel, topBarTitle = topBarTitle, confettiGo = confettiGo, lazyState = lazyState)
            }

        }
        if(confettiGo.value) {
            Confetti(bottomPadding = bottomPadding, confettiGo = confettiGo)
        }
    }
}

@Composable
private fun Confetti(bottomPadding: Dp, confettiGo: MutableState<Boolean>){
    val confettiRaw by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.confetti))
    val confettiState = animateLottieCompositionAsState(composition = confettiRaw)
    LottieAnimation(composition = confettiState.composition, iterations = LottieConstants.IterateForever, modifier = Modifier
        .padding(bottom = bottomPadding)
        .fillMaxSize(), contentScale = ContentScale.FillHeight)
    if(confettiState.progress >= 0.9f){
        confettiGo.value = false
    }
}