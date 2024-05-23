package tech.zaidaziz.clickretinaassignment.data.scorescreen

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.zaidaziz.clickretinaassignment.data.scorescreen.models.Content
import javax.inject.Inject

class ScoreScreenRepository @Inject constructor(
    private val dataSource: ScoreScreenDataSource
)  {
    suspend fun getData() : Content? {
        return withContext(Dispatchers.IO){
            val response = dataSource.getData()
             response?.choices?.first()?.message?.content
        }
    }
}