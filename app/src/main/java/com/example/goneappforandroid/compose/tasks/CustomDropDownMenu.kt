package com.example.goneappforandroid.compose.tasks

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.goneappforandroid.ui.theme.redInk

@Composable
fun CustomDropDownMenu(
    expanded: MutableState<Boolean>,
    offsetX: Dp,
    delete: () -> Unit,
) {
    DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }, offset = DpOffset(offsetX-100.dp, 0.dp)) {
        DropdownMenuItem(
            text = {Text("Edit")},
            onClick = { /*TODO*/ },
            leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null)})
        DropdownMenuItem(
            text = {Text("Delete", color = redInk)},
            onClick = {delete()},
            leadingIcon = { Icon(Icons.Outlined.Delete, contentDescription = null, tint = redInk)})
    }
}