package com.studyflow.app.ui.screens.session

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.studyflow.app.ui.theme.ActionGreen
import com.studyflow.app.ui.theme.BackgroundOffWhite
import com.studyflow.app.ui.theme.CameraDark
import com.studyflow.app.ui.theme.PurplePrimary
import com.studyflow.app.ui.theme.TextPrimary

@Composable
fun EvidenceScreen(
    onSaveAndContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var hasCameraPermission by remember { mutableStateOf(context.hasCameraPermission()) }
    var isCameraUnavailable by remember { mutableStateOf(false) }
    var captureError by remember { mutableStateOf<String?>(null) }
    // La foto solo se muestra en pantalla; no se guarda ni se envía a ningún lado.
    var photo by remember { mutableStateOf<Bitmap?>(null) }
    // Respaldo si no hay cámara o permiso: la captura se simula como en el wireframe.
    var isSimulatedPhotoTaken by rememberSaveable { mutableStateOf(false) }
    val isPhotoTaken = photo != null || isSimulatedPhotoTaken

    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> hasCameraPermission = granted }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    val canUseCamera = hasCameraPermission && !isCameraUnavailable

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundOffWhite)
            .systemBarsPadding()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enviar evidencia",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Muestra el trabajo que realizaste", color = TextPrimary, fontSize = 13.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(CameraDark),
            contentAlignment = Alignment.Center
        ) {
            val currentPhoto = photo
            when {
                currentPhoto != null -> Image(
                    bitmap = currentPhoto.asImageBitmap(),
                    contentDescription = "Foto de evidencia",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                isSimulatedPhotoTaken -> SimulatedPhotoPlaceholder()
                canUseCamera -> {
                    CameraPreview(
                        imageCapture = imageCapture,
                        onCameraUnavailable = { isCameraUnavailable = true }
                    )
                    ShutterButton(
                        onClick = {
                            captureError = null
                            imageCapture.takePhoto(
                                context = context,
                                onPhoto = { photo = it },
                                onError = { captureError = "No se pudo tomar la foto, intenta de nuevo" }
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 48.dp)
                    )
                }
                else -> CameraFallback(
                    showPermissionButton = !hasCameraPermission,
                    onRequestPermission = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                    onSimulatedShot = { isSimulatedPhotoTaken = true }
                )
            }
            Text(
                text = captureError ?: "Vista previa de cámara / foto del trabajo",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = {
                    photo = null
                    isSimulatedPhotoTaken = false
                },
                enabled = isPhotoTaken,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(1.dp, PurplePrimary),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PurplePrimary)
            ) {
                Text(text = "Repetir foto", fontSize = 13.sp)
            }
            Button(
                onClick = onSaveAndContinue,
                enabled = isPhotoTaken,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
            ) {
                Text(text = "Guardar y continuar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun CameraPreview(
    imageCapture: ImageCapture,
    onCameraUnavailable: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
            // TextureView para que el recorte redondeado de Compose se respete.
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        }
    }

    DisposableEffect(lifecycleOwner) {
        val providerFuture = ProcessCameraProvider.getInstance(context)
        providerFuture.addListener({
            try {
                val provider = providerFuture.get()
                val selector = when {
                    provider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) -> CameraSelector.DEFAULT_BACK_CAMERA
                    provider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) -> CameraSelector.DEFAULT_FRONT_CAMERA
                    else -> null
                }
                if (selector == null) {
                    onCameraUnavailable()
                    return@addListener
                }
                val preview = Preview.Builder().build()
                preview.setSurfaceProvider(previewView.surfaceProvider)
                provider.unbindAll()
                provider.bindToLifecycle(lifecycleOwner, selector, preview, imageCapture)
            } catch (e: Exception) {
                onCameraUnavailable()
            }
        }, ContextCompat.getMainExecutor(context))

        onDispose {
            if (providerFuture.isDone) {
                runCatching { providerFuture.get().unbindAll() }
            }
        }
    }

    AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
}

@Composable
private fun CameraFallback(
    showPermissionButton: Boolean,
    onRequestPermission: () -> Unit,
    onSimulatedShot: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = if (showPermissionButton) {
                "Sin permiso para usar la cámara"
            } else {
                "No se encontró una cámara disponible"
            },
            color = Color.White,
            fontSize = 13.sp
        )
        if (showPermissionButton) {
            TextButton(onClick = onRequestPermission) {
                Text(text = "Permitir cámara", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ShutterButton(onClick = onSimulatedShot)
    }
}

@Composable
private fun SimulatedPhotoPlaceholder() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = ActionGreen,
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Foto capturada", color = Color.White, fontSize = 14.sp)
    }
}

@Composable
private fun ShutterButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(width = 60.dp, height = 44.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)
            .clickable(onClickLabel = "Tomar foto", onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(PurplePrimary)
        )
    }
}

private fun Context.hasCameraPermission(): Boolean =
    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
        PackageManager.PERMISSION_GRANTED

private fun ImageCapture.takePhoto(
    context: Context,
    onPhoto: (Bitmap) -> Unit,
    onError: () -> Unit
) {
    takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                val rotation = image.imageInfo.rotationDegrees
                val bitmap = image.toBitmap()
                image.close()
                onPhoto(bitmap.rotated(rotation))
            }

            override fun onError(exception: ImageCaptureException) {
                onError()
            }
        }
    )
}

private fun Bitmap.rotated(degrees: Int): Bitmap {
    if (degrees == 0) return this
    val matrix = Matrix().apply { postRotate(degrees.toFloat()) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}
