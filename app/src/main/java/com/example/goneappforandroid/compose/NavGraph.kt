package com.example.goneappforandroid.compose

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.goneappforandroid.TasksViewModel
import com.example.goneappforandroid.compose.bottomappbar.CustomBottomAppBarItems
import com.example.goneappforandroid.compose.settings.SettingsScreen
import com.example.goneappforandroid.compose.tasks.TasksScreen
import com.example.goneappforandroid.compose.tasks.tutorial.TutorialScreen
import com.example.goneappforandroid.data.Task

@Composable
fun NavGraph(
    navHostController: NavHostController,
    topBarTitle: MutableState<String>,
    tasksList: State<List<Task>>,
    tasksViewModel: TasksViewModel,
    confettiGo: MutableState<Boolean>,
    lazyState: LazyListState,
){
    val local = LocalContext.current
    NavHost(navController = navHostController, startDestination = CustomBottomAppBarItems.tasks.route) {
        composable(route = CustomBottomAppBarItems.tasks.route){
            val isFirstStart = prefFirstStart(local, isLoad = true)
            if(isFirstStart){
                topBarTitle.value = "Gone"
                TutorialScreen(tasksViewModel = tasksViewModel, confettiGo = confettiGo, navHostController = navHostController, local = local)
            } else {
                topBarTitle.value = "Today"
                TasksScreen(tasksList = tasksList, tasksViewModel = tasksViewModel, confettiGo = confettiGo, lazyState = lazyState)
            }

        }
        composable(route = CustomBottomAppBarItems.settings.route){
            topBarTitle.value = "Settings"
            SettingsScreen(navHostController = navHostController)
        }
        composable(route = "overview"){

        }
    }
}

fun prefFirstStart(local: Context, isLoad: Boolean = false): Boolean{
    val sPref = PreferenceManager.getDefaultSharedPreferences(local)
    return if(isLoad){
        sPref.getBoolean("isFirstStart", true)
    } else {
        val ed: SharedPreferences.Editor = sPref.edit()
        ed.putBoolean("isFirstStart", false)
        ed.commit()
        false
    }

}