package tech.zaidaziz.clickretinaassignment.ui.scorescreen

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import tech.zaidaziz.clickretinaassignment.data.scorescreen.ScoreScreenRepository
import tech.zaidaziz.clickretinaassignment.data.scorescreen.models.Content
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val repository: ScoreScreenRepository,
    @ApplicationContext val context: Context
) : ViewModel() {

    val dataContent = mutableStateOf(Content())

        fun getData() {
            viewModelScope.launch {
               dataContent.value =  repository.getData() ?: Content()
            }
        }
}