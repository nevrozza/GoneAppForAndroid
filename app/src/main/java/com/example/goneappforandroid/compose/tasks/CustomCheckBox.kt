@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.goneappforandroid.compose.tasks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun CustomCheckBox(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
    editing: Boolean,

    ){
    Box(modifier = modifier, contentAlignment = Alignment.Center){


        OutlinedCard(
            modifier = Modifier
                .fillMaxSize()
                .alpha(if (checked) 1f else .5f),
            shape = AbsoluteRoundedCornerShape(20),
            border = BorderStroke(color = if(checked || editing) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurfaceVariant, width = 1.dp),
            onClick = {
                onCheckedChange()
            }) {
            if(editing){
                Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
            } else if(checked){
                Icon(imageVector = Icons.Rounded.Done, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}