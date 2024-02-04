package lol.hostov.tracemoe.presentation.ui.screens.search.views

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.ContentResolver
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lol.hostov.tracemoe.presentation.theme.LocalNavController
import lol.hostov.tracemoe.presentation.theme.Theme
import lol.hostov.tracemoe.presentation.ui.navigation.Routing
import lol.hostov.tracemoe.presentation.ui.screens.search.SearchViewModel

private fun getImagesList(context: Context): List<String> {
    val list = mutableListOf<String>()
    val queryArgs = Bundle().apply {
        putInt(ContentResolver.QUERY_ARG_OFFSET, 0)
        putInt(ContentResolver.QUERY_ARG_LIMIT, MAX_IMAGES_COUNT)
        putStringArray(ContentResolver.QUERY_ARG_SORT_COLUMNS, arrayOf(MediaStore.Files.FileColumns.DATE_MODIFIED))
        putInt(ContentResolver.QUERY_ARG_SORT_DIRECTION, ContentResolver.QUERY_SORT_DIRECTION_DESCENDING)
    }

    val imagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    val cursor = context.contentResolver.query(
        imagesUri,
        null,
        queryArgs,
        null,
    )

    if (cursor != null) {
        if (cursor.count > 0) {
            for (i in 0 until cursor.count) {
                cursor.moveToNext()

                val dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)

                list.add(cursor.getString(dataColumnIndex))
            }

            cursor.close()
        }
    }

    return list.toList()
}

@Composable
fun LastImagesView(viewModel: SearchViewModel) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var images by remember { mutableStateOf(emptyList<String>()) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                coroutineScope.launch(Dispatchers.IO) {
                    images = getImagesList(context)
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(READ_MEDIA_IMAGES)
        } else {
            permissionLauncher.launch(READ_EXTERNAL_STORAGE)
        }
    }

    AnimatedVisibility(
        visible = images.isNotEmpty(),
        enter = fadeIn(),
        exit = scaleOut()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.horizontalScroll(scrollState)
            ) {
                Box(Modifier.width(8.dp)) // empty item for space 8 + 8 dp

                for (url in images) {
                    ImageItem(viewModel, url)
                }

                Box(Modifier.width(8.dp)) // empty item for space 8 + 8 dp
            }
        }
    }
}

@Composable
private fun ImageItem(viewModel: SearchViewModel, url: String) {
    val coroutineScope = rememberCoroutineScope()
    val navController = LocalNavController.current
    var isUploading by remember { mutableStateOf(false) }

    fun uploadImage() {
        coroutineScope.launch {
            if (isUploading) return@launch

            isUploading = true
            viewModel.searchByImage(
                url = url,
                onSuccess = {
                    navController.navigate(Routing.Screen.RESULT)
                    isUploading = false
                },
                onError = { isUploading = false }
            )
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Theme.colors.shimmerBackground)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Color.White),
                onClick = { uploadImage() }
            )
    ) {
        AsyncImage(
            model = url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (isUploading) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)))

            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 3.dp,
                strokeCap = StrokeCap.Round,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

private const val MAX_IMAGES_COUNT = 35