package tech.zaidaziz.clickretinaassignment.data.scorescreen.models

data class Choice(
    var index: Int = 0,
    var message: Message = Message(),
    var logprobs: Any? = Any(),
    var finishReason: String = ""
)