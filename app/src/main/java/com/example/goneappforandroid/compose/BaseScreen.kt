@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.goneappforandroid.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.goneappforandroid.TasksViewModel
import com.example.goneappforandroid.TasksViewModelFactory
import com.example.goneappforandroid.compose.bottomappbar.CustomBottomAppBar
import com.example.goneappforandroid.ui.theme.GoneAppForAndroidTheme

//Date().minutes - minutes
//Date().hours - hours
//Date().date - day
//Date().month - month
//Date().year - year


@Composable
fun BaseScreen(factory: TasksViewModelFactory,
               tasksViewModel: TasksViewModel = viewModel(factory = factory)
) {
    val tasksList = tasksViewModel.tasks.collectAsState(initial = emptyList())
    val topBarTitle = remember{ mutableStateOf("") }
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    GoneAppForAndroidTheme {
        Scaffold(
            topBar = {
                CustomTopAppBar(title = topBarTitle.value,
                    isLeftIcon = false, scrollBehavior = scrollBehavior)
            },
            bottomBar = { CustomBottomAppBar(navHostController = navController) }
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())) {
                NavGraph(navHostController = navController, tasksList = tasksList, tasksViewModel = tasksViewModel, topBarTitle = topBarTitle)
            }

        }
    }
}
