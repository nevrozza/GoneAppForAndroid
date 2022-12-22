@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalTextApi::class, ExperimentalFoundationApi::class)

package com.example.goneappforandroid.compose.tasks

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goneappforandroid.TasksViewModel
import java.util.*

//Date().minutes - minutes
//Date().hours - hours
//Date().date - day
//Date().month - month
//Date().year - year


@Composable
fun Task(
    text: String? = null,
    id: Int,
    minute: Int = 0,
    hour: Int = 0,
    day: Int = 0,
    checked: Boolean = false,
    tasksViewModel: TasksViewModel,
    currentState: MutableState<MutableTransitionState<Boolean>>,
) {
    val mLocal = LocalContext.current
    val focusManager = LocalFocusManager.current
    val checked = remember { mutableStateOf(checked) }
    var value = remember { mutableStateOf(text ?: " ") }


    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp, horizontal = 10.dp)) {

        CustomCheckBox(
            modifier = Modifier
                .padding(top = 13.dp, start = 10.dp, end = 10.dp)
                .size(20.dp),
            checked = checked.value,
            onCheckedChange = {
                if (text != null) {
                    checked.value = !checked.value
                    tasksViewModel.updateTask(id, checked.value)
                    currentState.value = MutableTransitionState(false)
                }
            }
        )
        var pressed by remember { mutableStateOf(false) }

        var expanded = remember { mutableStateOf(false) }
        var offsetX by remember { mutableStateOf(0.dp) }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 13.dp)
            .scale(animateFloatAsState(targetValue = if (pressed) 0.99f else 1f, tween(150)).value)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressed = true
                        tryAwaitRelease()
                        pressed = false
                    },
                    onLongPress = {
                        offsetX = it.x.toDp()
                        expanded.value = true

                    }
                )
            }
        ) {
            Column {
                if (text == null) {

                    Box() {
                        BasicTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = value.value,
                            onValueChange = { value.value = it },
                            textStyle = TextStyle(fontSize = 16.sp),
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                            keyboardOptions = KeyboardOptions(imeAction = if (value.value.isNotEmpty()) ImeAction.Done else ImeAction.Send),
                            keyboardActions = KeyboardActions(
                                onSend = {
                                    Toast.makeText(mLocal,
                                        "Cannot be empty",
                                        Toast.LENGTH_SHORT).show()
                                },
                                onDone = {
                                    onDone(tasksViewModel = tasksViewModel,
                                        value = value,
                                        focusManager = focusManager)
                                }
                            )
                        )

                        if (value.value == " ") {

                            Text(" New task...",
                                modifier = Modifier.alpha(0.5f))
                        }
                    }

                } else {
                    val durationDay =
                        day - (((Date().year - 1) * 365) + ((Date().month - 1) * 30) + (Date().date))
                    val hours = hour + durationDay * 24
                    val durationHours = hours - Date().hours
                    val durationMinutes = minute - Date().minutes
                    var duration = "$durationHours h"
                    if (durationHours <= 0) {
                        duration = "$durationMinutes m"
                    }
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            textDecoration = if (duration[0] == '0' || duration[0] == '-' || checked.value) TextDecoration.LineThrough else null,
                            brush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
                            alpha = if (duration[0] == '0' || duration[0] == '-' || checked.value) 0.5f else 1f
                        )) {
                            append(value.value)
                        }
                        withStyle(style = SpanStyle(
                            textDecoration = if (duration[0] == '0' || duration[0] == '-' || checked.value) TextDecoration.LineThrough else null,
                            brush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
                            alpha = 0.5f)) {
                            append(" · $duration")
                        }
                    })
                }

                CustomDropDownMenu(expanded = expanded,
                    delete = { tasksViewModel.deleteTask(id) },
                    offsetX = offsetX)

            }

        }
    }


}

private fun onDone(
    value: MutableState<String>,
    focusManager: FocusManager,
    tasksViewModel: TasksViewModel,
) {
    val newDay =
        ((Date().year - 1) * 365) + ((Date().month - 1) * 30) + (Date().date + 1)
    tasksViewModel.insertTask(value.value,
        minute = Date().minutes,
        hour = Date().hours,
        day = newDay,
        checked = false)
    value.value = " "
    focusManager.clearFocus()
}