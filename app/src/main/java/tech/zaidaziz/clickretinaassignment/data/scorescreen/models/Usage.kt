package tech.zaidaziz.clickretinaassignment.data.scorescreen.models

data class Usage(
    var promptTokens: Int = 0,
    var completionTokens: Int = 0,
    var totalTokens: Int = 0
)