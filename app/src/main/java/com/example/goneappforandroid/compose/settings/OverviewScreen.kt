@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.goneappforandroid.compose.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.goneappforandroid.data.Task
import com.example.goneappforandroid.ui.theme.redInk
import com.example.goneappforandroid.R
import com.example.goneappforandroid.compose.tasks.durationReturn
import java.util.*

@Composable
fun OverViewScreen(
    tasksList: State<List<Task>>,
    cal: MutableState<Calendar>,
    navHostController: NavHostController
) {
    val currentState = remember { MutableTransitionState(false) }
    currentState.targetState = true

    var previousMonth = cal.value.get(Calendar.MONTH)
    var previousDay = cal.value.get(Calendar.DAY_OF_MONTH)-7
    val currentDay = cal.value.get(Calendar.DAY_OF_MONTH)
    if(currentDay < 8){
        previousMonth--
        if(previousMonth == -1) previousMonth = 11
    }
    val maxDays = GregorianCalendar(cal.value.get(Calendar.YEAR), previousMonth, 1).getActualMaximum(Calendar.DAY_OF_MONTH)
    if(previousDay <= 0){
        previousDay = maxDays - previousDay
    }

    val previousMonthText = monthToText(previousMonth)
    val currentMonthText = monthToText(cal.value.get(Calendar.MONTH))

    val previousDayText = dayToText(day = previousDay)
    val currentDayText = dayToText(day = currentDay)
    val dateText = "$previousDayText $previousMonthText - $currentDayText $currentMonthText "

    var expiredCount = 0
    var completed = 0
    var incomplete = 0
    for (item in tasksList.value) {


        val duration = durationReturn(day = item.day, minute = item.minute, hour = item.hour, cal = cal)
        if(duration.subSequence(0, duration.length - 2).toString().toLong() < 0){
            expiredCount++
            if(!item.checked) {
                incomplete++
            }
        }
        if(item.checked){
            completed++
        }
    }

    AnimatedVisibility(visibleState = currentState, enter = fadeIn(tween(800))) {
        Column(modifier = Modifier.fillMaxSize()) {

            MenuItem(icon = Icons.Outlined.CalendarToday,
                text = dateText, onPressAnimation = false)

            MenuItem(icon = Icons.Rounded.List,
                text = pluralStringResource(R.plurals.tasks,
                    tasksList.value.size,
                    tasksList.value.size),
                textAdd = "$expiredCount ${stringResource(R.string.tasks_expired)}",
                onClick = {navHostController.navigate("history")}
            )

            MenuItem(icon = Icons.Rounded.TaskAlt,
                iconColor = MaterialTheme.colorScheme.primary,
                iconAlpha = 1f,
                text = "$completed ${stringResource(id = R.string.tasks_completed) }",
                textAdd = "${(completed/(tasksList.value.size).toFloat()*100).toInt()}%", onPressAnimation = false)

            MenuItem(icon = Icons.Rounded.Schedule,
                iconAlpha = 1f,
                text = "${tasksList.value.size - (completed+incomplete)} ${stringResource(id = R.string.tasks_idle) }",
                textAdd = "${((tasksList.value.size - (completed+incomplete))/(tasksList.value.size).toFloat()*100).toInt()}%", onPressAnimation = false)

            MenuItem(icon = Icons.Outlined.Cancel, iconColor = redInk, iconAlpha = 1f,
                text = "$incomplete ${stringResource(id = R.string.tasks_incomplete) }",
                textAdd = "${(incomplete/(tasksList.value.size).toFloat()*100).toInt()}%", onPressAnimation = false)
        }
    }
}

@Composable
private fun dayToText(day: Int): String{
    var firstNum = ""
    if(day.toString().length > 1){
        firstNum = day.toString()[0].toString()
    }
    val lastNum = day.toString()[day.toString().length-1].toString().toInt()
    return "$firstNum${stringArrayResource(id = R.array.ordinals)[lastNum]}"
}

@Composable
private fun monthToText(month: Int): String{
    return when(month){
        0 -> {
            stringResource(id = R.string.month_0)}
        1 -> {
            stringResource(id = R.string.month_1)}
        2 -> {
            stringResource(id = R.string.month_2)}
        3 -> {
            stringResource(id = R.string.month_3)}
        4 -> {
            stringResource(id = R.string.month_4)}
        5 -> {
            stringResource(id = R.string.month_5)}
        6 -> {
            stringResource(id = R.string.month_6)}
        7 -> {
            stringResource(id = R.string.month_7)}
        8 -> {
            stringResource(id = R.string.month_8)}
        9 -> {
            stringResource(id = R.string.month_9)}
        10 -> {
            stringResource(id = R.string.month_10)}
        else -> {
            stringResource(id = R.string.month_11)}
    }
}