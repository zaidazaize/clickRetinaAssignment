package tech.zaidaziz.clickretinaassignment.ui.scorescreen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.zaidaziz.clickretinaassignment.ui.theme.ClickRetinaAssignmentTheme
import tech.zaidaziz.clickretinaassignment.ui.theme.interFontFamily
import tech.zaidaziz.clickretinaassignment.ui.theme.robotoFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(viewModel: ScoreViewModel) {
    ClickRetinaAssignmentTheme {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = {
                Text("Android Assignment",
                     fontWeight = FontWeight.Bold,
                     fontFamily = robotoFontFamily)
            },
                      colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFEA392F),
                                                                 titleContentColor = Color.White))

        }) { innerPadding ->
            ScoreScreen(viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding))
        }
    }
}

@Composable
fun ScoreScreen(viewModel: ScoreViewModel, modifier: Modifier = Modifier) {
    val dataContent = viewModel.dataContent.value
    val context = LocalContext.current
    Column(
        modifier = modifier
            .background(color = Color(0xFFF2F5F6))
            .verticalScroll(rememberScrollState()),

        ) {
        Spacer(modifier = Modifier.height(32.dp))
        OverAllScore()
        Spacer(modifier = Modifier.height(20.dp))
        TitleBodyPart(title = dataContent.title,
                      copyButtonClicked = {
                          copyToClipboard(dataContent.title, context = viewModel.context)
                      },
                      onShareButtonClicked = {
                          val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            intent.putExtra(Intent.EXTRA_TEXT, dataContent.title)
                            val shateIntent = Intent.createChooser(intent, "Share via")
                            context.startActivity(shateIntent)
                      },
                      lableTitle = "TITLE")
        Spacer(modifier = Modifier.height(20.dp))
        TitleBodyPart(title = dataContent.description,
                      copyButtonClicked = {
                            copyToClipboard(dataContent.description, context = viewModel.context)
                      },
                      onShareButtonClicked = {
                            val intent = Intent(Intent.ACTION_SEND)
                                intent.type = "text/plain"
                                intent.putExtra(Intent.EXTRA_TEXT, dataContent.description)
                          val shateIntent = Intent.createChooser(intent, "Share via")
                            context.startActivity(shateIntent)

                      },
                      lableTitle = "DESCRIPTION")
        Spacer(modifier = Modifier.height(40.dp))
    }
}

fun copyToClipboard(text: String = "", context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OverAllScore() {

    AppHeading("OVERALL SCORE")
    Spacer(modifier = Modifier.height(16.dp))
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp),
         shape = RoundedCornerShape(8.dp),
         colors = CardDefaults.cardColors(
             containerColor = Color(0xFFFFFFFF),
         ),
         elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Column {
                Text(
                    text = "OVERALL SCORE",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp,
                                                                      fontWeight = FontWeight.Bold,
                                                                      letterSpacing = 0.15.sp,
                                                                      fontFamily = interFontFamily),
                )

                Text(text = "AVERAGE",
                     style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp,
                                                                       fontWeight = FontWeight.Bold,
                                                                       letterSpacing = 1.sp,
                                                                       fontFamily = interFontFamily),
                     color = Color(0xFFF8BB00)

                )
            }
            Spacer(modifier = Modifier.weight(1f))
            ProgressCard(progress = 40)

        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F1F1).copy(alpha = 0.3f))) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                SearchVolumeCompetition(modifier = Modifier,
                                        subHeading = "Search Volume",
                                        color = Color(0xFF62C734),
                                        text = "HIGH")
                Spacer(modifier = Modifier.weight(1f))
                SearchVolumeCompetition(modifier = Modifier,
                                        subHeading = "Competition",
                                        color = Color(0xFFF54241),
                                        text = "HIGH")
            }

        }
    }
}

@Composable
private fun SearchVolumeCompetition(
    subHeading: String, color: Color, text: String, modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = subHeading,
             style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp,
                                                               fontWeight = FontWeight.Medium,
                                                               letterSpacing = 0.15.sp,
                                                               fontFamily = interFontFamily,
                                                               lineHeight = 14.52.sp),
             color = Color(0xFF000000).copy(alpha = 0.5f))
        Text(text = text,
             style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp,
                                                               fontWeight = FontWeight.Bold,
                                                               letterSpacing = 0.15.sp,
                                                               lineHeight = 14.52.sp,
                                                               fontFamily = interFontFamily),
             color = color)
    }
}

@Composable
private fun AppHeading(title: String) {

    Text(text = title,
         modifier = Modifier.padding(horizontal = 20.dp),
         style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 1.sp,
                                                          fontFamily = interFontFamily),
         color = Color(0xFF000000).copy(alpha = 0.4f))
}

@Composable
fun TitleBodyPart(
    title: String = "Some Random String",
    copyButtonClicked: () -> Unit,
    onShareButtonClicked: () -> Unit,
    lableTitle: String
) {
    AppHeading(title = lableTitle)
    Spacer(modifier = Modifier.height(16.dp))
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp),
         colors = CardDefaults.cardColors(
             containerColor = Color(0xFFFFFFFF),
         ),
         shape = RoundedCornerShape(8.dp),
         elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {

        Text(text = title,
             modifier = Modifier.padding(20.dp),
             style = MaterialTheme.typography.labelMedium.copy(fontSize = 14.sp,
                                                               fontWeight = FontWeight.Medium,
                                                               fontFamily = interFontFamily,
                                                               letterSpacing = 0.15.sp,
                                                               lineHeight = 24.sp),
             color = Color(0xFF000000).copy(alpha = 0.6f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFAFAFCFC).copy(alpha = 0.3f)),
        ) {

            val rowHeight = remember {
                mutableStateOf(0.dp)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .onSizeChanged {
                        rowHeight.value = (it.height - 20).dp
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                TextButton(onClick = copyButtonClicked) {
                    Text(text = "Copy",
                         color = Color(0xFF2F80ED),
                         fontSize = 16.sp,
                         fontFamily = interFontFamily)
                }
                Divider(modifier = Modifier
                    .width(1.dp)
                    .height(if (rowHeight.value < 40.dp) rowHeight.value else 40.dp),
                        color = Color(0xFF959595).copy(alpha = 0.6f))
                TextButton(onClick = onShareButtonClicked) {
                    Text(text = "Share",
                         color = Color(0xFF2F80ED),
                         fontSize = 16.sp,
                         fontFamily = interFontFamily)
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProgressCard(
    progress: Int = 50
) {
    Box(modifier = Modifier
        .height(88.41.dp)
        .width(88.41.dp)
        .padding(4.dp)) {
        CircularProgressIndicator(
            progress = progress / 100f,
            modifier = Modifier
                .fillMaxSize()
                .align(alignment = Alignment.Center),
            trackColor = Color(0xFFFAFAFA),
            strokeWidth = 8.dp,
            color = Color(0xFFF8BB00),
        )
        Text(text = "$progress",
             modifier = Modifier.align(alignment = Alignment.Center),
             style = MaterialTheme.typography.labelMedium.copy(fontSize = 22.sp,
                                                               fontWeight = FontWeight.Bold,
                                                               letterSpacing = 0.167.sp,
                                                               lineHeight = 26.72.sp),
             color = Color(0xFFF8BB00))
        Text(
            text = "/100",
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(top = 27.dp),
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp,
                                                              fontWeight = FontWeight.Medium,
                                                              letterSpacing = 0.167.sp,
                                                              lineHeight = 14.52.sp),
        )

    }
}

