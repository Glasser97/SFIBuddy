package se.grayson.sfibuddy.presentation.addword.ui

import AlertMessageDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import se.grayson.sfibuddy.presentation.addword.model.DisplayImage
import se.grayson.sfibuddy.presentation.imagepicker.*
import sfibuddy.composeapp.generated.resources.*
import sfibuddy.composeapp.generated.resources.Res
import sfibuddy.composeapp.generated.resources.add_word_done
import sfibuddy.composeapp.generated.resources.request_permission_content
import sfibuddy.composeapp.generated.resources.request_permission_title

@Composable
fun AddWordScreen(
    viewModel: AddWordViewModel = viewModel { AddWordViewModel() },
    modifier: Modifier = Modifier, onNavigateUp: () -> Unit = {}
) {
    val isLaunchCamera: Boolean by viewModel.isLaunchCamera.collectAsStateWithLifecycle()
    val imageList by viewModel.imageList.collectAsStateWithLifecycle()

    AddWordScreenViewModeless(
        isLaunchCamera = isLaunchCamera,
        imageList = imageList,
        modifier = modifier,
        onNavigateUp = onNavigateUp,
        onAddImage = { viewModel.addImage(it) },
        onDeleteImage = { viewModel.deleteImage(it) },
        onUpdateLaunchCamera = { viewModel.updateLaunchCamera(it) }
    )
}

@Composable
fun AddWordScreenViewModeless(
    isLaunchCamera: Boolean = false,
    imageList: List<DisplayImage> = emptyList(),
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
    onAddImage: (SharedImage) -> Unit = {},
    onDeleteImage: (Int) -> Unit = {},
    onUpdateLaunchCamera: (Boolean) -> Unit = {},
) {
    val pagerState = rememberPagerState { imageList.size }
    var launchSetting by remember { mutableStateOf(value = false) }
    var permissionRationalDialog by remember { mutableStateOf(value = false) }
    val cameraManager = rememberCameraManager {
        onAddImage(it ?: return@rememberCameraManager)
    }
    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> {
                            cameraManager.launch()
                        }

                        else -> Unit
                    }
                }

                else -> {
                    permissionRationalDialog = true
                }
            }
        }

    })

    if (permissionRationalDialog) {
        AlertMessageDialog(
            title = stringResource(Res.string.request_permission_title),
            message = stringResource(Res.string.request_permission_content),
            positiveButtonText = stringResource(Res.string.settings),
            negativeButtonText = stringResource(Res.string.cancel),
            onPositiveClick = {
                permissionRationalDialog = false
                launchSetting = true
            },
            onNegativeClick = {
                permissionRationalDialog = false
            })
    }

    if (isLaunchCamera) {
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
            cameraManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.CAMERA)
        }
        onUpdateLaunchCamera(false)
    }

    LaunchedEffect(key1 = imageList.size) {
        val lastIndex = imageList.lastIndex
        if (lastIndex >= 0 && pagerState.currentPage != lastIndex) {
            pagerState.animateScrollToPage(lastIndex)
        }
    }

    LaunchedEffect(key1 = true) {
        onUpdateLaunchCamera(true)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            AddWordTopBar(
                onNavigateUp = onNavigateUp,
                onTakeImageDone = {}
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 24.dp),
                pageSpacing = 15.dp,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(fraction = 0.7f),
                key = {
                    imageList.getOrNull(it)?.imageId ?: 0
                }
            ) { page ->
                Image(
                    bitmap = imageList[page].bitmap,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${pagerState.currentPage + 1} / ${imageList.size}",
                fontSize = 16.sp,
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    modifier = Modifier.size(45.dp),
                    onClick = {
                        onDeleteImage(pagerState.currentPage)
                    }
                ) {
                    Icon(
                        painterResource(Res.drawable.ic_delete_24px),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                IconButton(
                    modifier = Modifier.size(45.dp),
                    onClick = {
                        onUpdateLaunchCamera(true)
                    }
                ) {
                    Icon(
                        painterResource(Res.drawable.ic_add_circle_24px),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}

@Composable
fun AddWordTopBar(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
    onTakeImageDone: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier.height(45.dp),
            onClick = onNavigateUp
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }

        Button(
            onClick = onTakeImageDone,
            shape = CircleShape
        ) {
            Text(
                text = stringResource(Res.string.add_word_done),
                color = MaterialTheme.colors.onPrimary,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
fun AddWordScreenPreview() {
    MaterialTheme {
        AddWordScreenViewModeless()
    }
}