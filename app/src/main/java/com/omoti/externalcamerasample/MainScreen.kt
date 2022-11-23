package com.omoti.externalcamerasample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.omoti.externalcamerasample.ui.theme.ExternalCameraSampleTheme

@Composable
fun MainScreen() {
    Scaffold { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            Button(onClick = {
                // TODO : Launch Camera App
            }) {
                Text(text = "Launch Camera App")
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    ExternalCameraSampleTheme {
        MainScreen()
    }
}
