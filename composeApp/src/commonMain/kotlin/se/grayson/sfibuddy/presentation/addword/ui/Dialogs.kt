import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import sfibuddy.composeapp.generated.resources.Res
import sfibuddy.composeapp.generated.resources.ic_error_dialog

@Composable
fun AlertMessageDialog(
    title: String,
    message: String? = null,
    resource: DrawableResource = Res.drawable.ic_error_dialog,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    onPositiveClick: () -> Unit = {},
    onNegativeClick: () -> Unit = {},
) {

    Dialog(
        onDismissRequest = {}, properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            shape = RoundedCornerShape(size = 12.dp)
        ) {
            Column(
                modifier = Modifier.background(MaterialTheme.colors.secondaryVariant)
                    .padding(all = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                resource.let {
                    Image(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(it),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.subtitle2.fontSize,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                message?.let {
                    Text(
                        text = it,
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(end = 16.dp, start = 16.dp)
                ) {
                    negativeButtonText?.let {
                        Button(
                            modifier = Modifier.weight(1f), onClick = {
                                onNegativeClick()
                            }, colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colors.primary
                            )
                        ) {
                            Text(text = it, textAlign = TextAlign.Center, maxLines = 1)
                        }

                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    positiveButtonText?.let {
                        Button(
                            modifier = Modifier.weight(1f), onClick = {
                                onPositiveClick()
                            }, colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colors.primary
                            )
                        ) {
                            Text(text = it, textAlign = TextAlign.Center, maxLines = 1)
                        }
                    }
                }
            }

        }

    }
}

@Composable
fun FullScreenLoadingDialog() {
    Box(
        modifier = Modifier.fillMaxSize().clickable(enabled = false, onClick = {})
    ) {
        Box(
            modifier = Modifier.align(Alignment.Center).padding(bottom = 80.dp).size(height = 128.dp, width = 98.dp)
                .background(color = Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
                .clickable(enabled = false, onClick = {}),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}