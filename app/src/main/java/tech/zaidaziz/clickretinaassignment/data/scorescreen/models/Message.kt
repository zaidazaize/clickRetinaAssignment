package tech.zaidaziz.clickretinaassignment.data.scorescreen.models

data class Message(
    var role: String = "",
    var content: Content = Content()
){

}
 data class Content(
    var title: String = "",
    var description: String = ""
){
     fun isEmpty(): Boolean {
         return title.isEmpty() && description.isEmpty()
     }
 }

