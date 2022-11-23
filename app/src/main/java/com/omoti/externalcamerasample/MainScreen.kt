package com.omoti.externalcamerasample

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.omoti.externalcamerasample.ui.theme.ExternalCameraSampleTheme

@Composable
fun MainScreen() {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode != RESULT_OK) return@rememberLauncherForActivityResult

            // TODO: 撮影後の処理
        }
    )

    Scaffold { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            Button(onClick = {
                launchCameraApp(context = context, launcher)
            }) {
                Text(text = "Launch Camera App")
            }
        }
    }
}

/**
 * カメラアプリ起動
 */
private fun launchCameraApp(context: Context, launcher: ActivityResultLauncher<Intent>) {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    if (takePictureIntent.resolveActivity(context.packageManager) != null) {
        launcher.launch(takePictureIntent)
    } else {
        Toast.makeText(context, "Camera App not found.", Toast.LENGTH_SHORT).show()
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    ExternalCameraSampleTheme {
        MainScreen()
    }
}
