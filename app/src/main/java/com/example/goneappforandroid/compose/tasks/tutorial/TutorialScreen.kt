package com.example.goneappforandroid.compose.tasks.tutorial

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.goneappforandroid.TasksViewModel
import com.example.goneappforandroid.compose.bottomappbar.CustomBottomAppBarItems
import com.example.goneappforandroid.compose.prefFirstStart
import com.example.goneappforandroid.compose.settings.MenuItem
import com.example.goneappforandroid.compose.tasks.Task
import com.example.goneappforandroid.R
import com.example.goneappforandroid.compose.CustomAnimation
import java.util.Calendar

@Composable
fun TutorialScreen(
    tasksViewModel: TasksViewModel,
    confettiGo: MutableState<Boolean>,
    navHostController: NavHostController,
    local: Context,
) {
    val text = listOf(stringResource(id = R.string.tutorial_1),
        stringResource(id = R.string.tutorial_2),
        stringResource(id = R.string.tutorial_3))
    Column(modifier = Modifier.fillMaxSize()) {
        for (i in text.indices) {
            Task(
                text = text[i],
                id = i,
                tasksViewModel = tasksViewModel,
                day = 999999999999999999,
                checked = false,
                confettiGo = confettiGo, cal = remember { mutableStateOf(Calendar.getInstance()) })
        }
        CustomAnimation {
            MenuItem(icon = Icons.Rounded.ArrowForwardIos,
                text = stringResource(id = R.string.get_started),
                textColor = MaterialTheme.colorScheme.primary,
                iconColor = MaterialTheme.colorScheme.primary,
                size = 25.dp,
                iconAlpha = 1f,
                onClick = {
                    prefFirstStart(local)
                    navHostController.navigate(CustomBottomAppBarItems.Tasks.route)
                },
                size2 = 20.dp)
        }



    }
}