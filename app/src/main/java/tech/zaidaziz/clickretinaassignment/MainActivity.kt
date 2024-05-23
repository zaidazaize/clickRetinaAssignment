package tech.zaidaziz.clickretinaassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tech.zaidaziz.clickretinaassignment.ui.scorescreen.MainApp
import tech.zaidaziz.clickretinaassignment.ui.scorescreen.ScoreViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel.getData()
        enableEdgeToEdge()
        setContent {
          MainApp(
            viewModel = viewModel
          )
        }
    }
}

