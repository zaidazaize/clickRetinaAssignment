package tech.zaidaziz.clickretinaassignment.data.scorescreen.models

data class ApiResponseObject(
    var id: String = "",
    var objectX: String = "",
    var created: Int = 0,
    var model: String = "",
    val choices: MutableList<Choice> = mutableListOf(),
    val usage: Usage = Usage(),
    var systemFingerprint: String? = null
)