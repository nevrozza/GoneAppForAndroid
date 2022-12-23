@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.goneappforandroid.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.goneappforandroid.R
import com.example.goneappforandroid.TasksViewModel
import com.example.goneappforandroid.TasksViewModelFactory
import com.example.goneappforandroid.compose.bottomappbar.CustomBottomAppBar
import com.example.goneappforandroid.ui.theme.GoneAppForAndroidTheme





@Composable
fun BaseScreen(factory: TasksViewModelFactory,
               tasksViewModel: TasksViewModel = viewModel(factory = factory)
) {
    val tasksList = tasksViewModel.tasks.collectAsState(initial = emptyList())
    val topBarTitle = remember{ mutableStateOf("") }
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    GoneAppForAndroidTheme {
        val confettiGo = remember { mutableStateOf(false)}
        var bottomPadding by remember { mutableStateOf(0.dp)}


        Scaffold(
            topBar = {
                CustomTopAppBar(titleState = topBarTitle,
                    isLeftIcon = false, scrollBehavior = scrollBehavior)
            },
            bottomBar = { CustomBottomAppBar(navHostController = navController) }
        ) {
            bottomPadding = it.calculateBottomPadding()
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())) {
                NavGraph(navHostController = navController, tasksList = tasksList, tasksViewModel = tasksViewModel, topBarTitle = topBarTitle, confettiGo = confettiGo)
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