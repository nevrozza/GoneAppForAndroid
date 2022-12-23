@file:OptIn(
    ExperimentalTextApi::class,
    ExperimentalComposeUiApi::class
)

package com.example.goneappforandroid.compose.tasks

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.goneappforandroid.R
import com.example.goneappforandroid.TasksViewModel
import com.example.goneappforandroid.ui.theme.Typography
import kotlinx.coroutines.delay
import java.util.*



@Composable
fun Task(
    text: String? = null,
    id: Int,
    minute: Int = 0,
    hour: Int = 0,
    day: Long = 0,
    checked: Boolean = false,
    tasksViewModel: TasksViewModel,
    confettiGo: MutableState<Boolean>,
    tweenDur: MutableState<Int> = mutableStateOf(800),
) {
    val mLocal = LocalContext.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val newChecked = remember { mutableStateOf(checked) }
    val editing = remember { mutableStateOf(false) }
    val value = remember {
        mutableStateOf(
            TextFieldValue(
                text ?: "",
                selection = TextRange((text ?: "").length)
            )
        )
    }

    var pressed by remember { mutableStateOf(false) }
    var doned by remember { mutableStateOf(false) }
    val expanded = remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0.dp) }

    val keyboard = LocalSoftwareKeyboardController.current
    var currentState = remember { MutableTransitionState(false) }
    currentState.targetState = true
    val cal = Calendar.getInstance()
    val durationDay =
        day - ( ((cal.get(Calendar.YEAR) - 1901) * 365 ) + ((cal.get(Calendar.MONTH) - 1 ) * 30 ) + cal.get(Calendar.DAY_OF_MONTH))
    val hours = hour + durationDay * 24
    val durationHours = hours - cal.get(Calendar.HOUR_OF_DAY)
    val durationMinutes = minute - cal.get(Calendar.MINUTE)
    var duration = "$durationHours ${stringResource(id = R.string.hours)}"
    if (durationHours <= 0) {
        duration = "$durationMinutes ${stringResource(id = R.string.minutes)}"
    }
    if (duration.subSequence(0, duration.length - 2).toString().toLong() > 0 || text == null) {
        AnimatedVisibility(visibleState = currentState,
            enter = fadeIn(tween(tweenDur.value))) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp, start = 10.dp, end = 25.dp)
            ) {

                CustomCheckBox(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                        .size(25.dp),
                    checked = newChecked.value,
                    editing = editing.value,
                    onCheckedChange = {
                        if (text != null && !editing.value) {
                            newChecked.value = !newChecked.value
                            tasksViewModel.checkTask(id, newChecked.value)
                            currentState = MutableTransitionState(false)
                        }
                        if (newChecked.value) {
                            confettiGo.value = true
                        }
                    }
                )



                LaunchedEffect(editing.value) {
                    if (editing.value) {
                        focusRequester.requestFocus()
                        expanded.value = false
                        //necessarily
                        delay(150)
                        keyboard?.show()
                    }
                }
                BackHandler(enabled = editing.value) {
                    focusManager.clearFocus()
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .scale(
                        animateFloatAsState(
                            targetValue = if (pressed) 0.99f else 1f,
                            tween(150)
                        ).value
                    )
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
                        if (text == null || editing.value) {
                            Box {
                                val toast = stringResource(id = R.string.no_empty)
                                BasicTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .focusRequester(focusRequester = focusRequester)
                                        .onFocusChanged {
                                            if (!expanded.value) {
                                                if (!it.isFocused) {
                                                    editing.value = false
                                                    if (!doned) {
                                                        value.value = TextFieldValue(
                                                            text ?: "",
                                                            selection = TextRange((text
                                                                ?: "").length)
                                                        )
                                                    }
                                                    doned = false
                                                }
                                            }
                                        },
                                    value = value.value,
                                    onValueChange = { value.value = it },

                                    textStyle = Typography.bodyLarge,
                                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = if (value.value.text != " ".repeat(
                                                value.value.text.length
                                            )
                                        ) ImeAction.Done else ImeAction.Send
                                    ),
                                    keyboardActions = KeyboardActions(

                                        onSend = {
                                            Toast.makeText(
                                                mLocal,
                                                toast,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        },
                                        onDone = {
                                            doned = true
                                            if (editing.value) {
                                                tasksViewModel.textTask(id, value.value.text)
                                            } else {
                                                val newDay =
                                                    ((cal.get(Calendar.YEAR) - 1901) * 365) + ((cal.get(Calendar.MONTH) - 1) * 30) + (cal.get(Calendar.DAY_OF_MONTH) + 1)
                                                tasksViewModel.insertTask(
                                                    value.value.text,
                                                    minute = cal.get(Calendar.MINUTE),
                                                    hour = cal.get(Calendar.HOUR_OF_DAY),
                                                    day = newDay.toLong(),
                                                    checked = false
                                                )
                                            }
                                            focusManager.clearFocus()
                                        }
                                    )
                                )

                                if (value.value.text == "") {

                                    Text(
                                        stringResource(id = R.string.new_Task),
                                        modifier = Modifier.alpha(.5f)
                                    )
                                }
                            }

                        } else {
                            Text(text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        textDecoration = if (newChecked.value) TextDecoration.LineThrough else null,
                                        brush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
                                        alpha = if (newChecked.value) 0.5f else 1f
                                    )
                                ) {
                                    append(value.value.text)
                                }
                                withStyle(
                                    style = SpanStyle(
                                        textDecoration = if (newChecked.value) TextDecoration.LineThrough else null,
                                        brush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
                                        alpha = 0.5f
                                    )
                                ) {
                                    if(duration.subSequence(0, duration.length-2).toString().toLong() < 61) {
                                        append(" · $duration")
                                    }
                                }
                            })
                        }

                        CustomDropDownMenu(
                            checked = newChecked.value,
                            expanded = expanded,
                            delete = { tasksViewModel.deleteTask(id) },
                            edit = {
                                editing.value = true

                            },
                            offsetX = offsetX
                        )

                    }


                }
            }
        }
    }
    if (duration.subSequence(0, duration.length-2).toString().toLong() < 0) {
        15+11
    }
}