package com.omoti.externalcamerasample

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import com.omoti.externalcamerasample.ui.theme.ExternalCameraSampleTheme
import java.io.File
import java.io.IOException

@Composable
fun MainScreen() {
    val context = LocalContext.current

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult(),
            onResult = { result ->
                if (result.resultCode == RESULT_OK) {
                    // TODO: 保存したファイルからBitmap取得
                }
            })

    Scaffold { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
        ) {
            Button(onClick = {
                imageUri = createImageFileUri(context)?.also {
                    launchCameraApp(context = context, launcher, it)
                }
            }) {
                Text(text = "Launch Camera App")
            }

            bitmap?.let {
                Image(bitmap = it.asImageBitmap(), contentDescription = null)

                Text(text = "width: ${it.width}, height: ${it.height}")
            }
        }
    }
}

/**
 * カメラアプリ起動
 */
private fun launchCameraApp(context: Context, launcher: ActivityResultLauncher<Intent>, uri: Uri) {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        .putExtra(MediaStore.EXTRA_OUTPUT, uri)

    if (takePictureIntent.resolveActivity(context.packageManager) != null) {
        launcher.launch(takePictureIntent)
    } else {
        Toast.makeText(context, "Camera App not found.", Toast.LENGTH_SHORT).show()
    }
}

/**
 * 保存先ファイルを用意
 */
private fun createImageFileUri(context: Context): Uri? {
    val file = try {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        File.createTempFile("temp", ".jpg", storageDir)
    } catch (e: IOException) {
        return null
    }

    // 共有可能なcontent URIを取得
    return FileProvider.getUriForFile(
        context,
        "com.omoti.externalcamerasample.fileprovider", // AndroidManifestのProvider名と一致
        file
    )
}

@Preview
@Composable
private fun MainScreenPreview() {
    ExternalCameraSampleTheme {
        MainScreen()
    }
}
