@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.goneappforandroid.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomTopAppBar(title: String, isLeftIcon: Boolean, scrollBehavior: TopAppBarScrollBehavior){
    TopAppBar(title = {Text(text = title)},
        navigationIcon = {
            if(isLeftIcon){
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.ArrowBackIos, contentDescription = null)
                }
            }
        },
        scrollBehavior = scrollBehavior)
}