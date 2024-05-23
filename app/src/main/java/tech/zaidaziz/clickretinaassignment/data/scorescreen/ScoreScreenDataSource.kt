package tech.zaidaziz.clickretinaassignment.data.scorescreen

import android.util.Log
import org.json.JSONObject
import tech.zaidaziz.clickretinaassignment.data.scorescreen.models.ApiResponseObject
import tech.zaidaziz.clickretinaassignment.data.scorescreen.models.Choice
import tech.zaidaziz.clickretinaassignment.data.scorescreen.models.Message
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class ScoreScreenDataSource @Inject constructor() {

    suspend fun getData(): ApiResponseObject? {
        return try {

            val url = URL("https://run.mocky.io/v3/f4613593-a726-4908-84cf-08b5b96c4a57")
            with(url.openConnection() as HttpsURLConnection) {
                requestMethod = "GET"
                Log.d("ScoreScreenDataSource", "URL : $url")
                Log.d("ScoreScreenDataSource", "Response Code : $responseCode")
                Log.d("ScoreScreenDataSource", "Response Message : $responseMessage")

                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = it.lines().collect(Collectors.joining())
                    Log.d("ScoreScreenDataSource", "Response : ${response.trimExtraWhiteSpace()}")
                    val parsedRequest = requestParser(response)
                     parsedRequest
                }
            }
        } catch (e: Exception) {
             null
        }
    }
}


fun requestParser(string: String): ApiResponseObject {
    val data = ApiResponseObject()
    val jsonObject = JSONObject(string)
    jsonObject.keys().forEach {
        when (it) {
            "id" -> data.id = jsonObject.getString(it)
            "object" -> data.objectX = jsonObject.getString(it)
            "created" -> data.created = jsonObject.getInt(it)
            "model" -> data.model = jsonObject.getString(it)

            "choices" -> {
                parseChoices(jsonObject, it, data.choices)
            }

            "usage" -> {
                val usage = jsonObject.getJSONObject(it)
                usage.keys().forEach { key ->
                    when (key) {
                        "prompt_tokens" -> {
                            data.usage.promptTokens = usage.getInt(key)
                        }

                        "completion_tokens" -> {
                            data.usage.completionTokens = usage.getInt(key)
                        }

                        "total_tokens" -> {
                            data.usage.totalTokens = usage.getInt(key)
                        }
                    }
                }
            }

            "system_fingerprint" -> {
                data.systemFingerprint = jsonObject.getString(it)
            }
        }
    }

    return data
}

private fun parseChoices(jsonObject: JSONObject, it: String?, choiceList: MutableList<Choice>) {
    val choices = jsonObject.getJSONArray(it)
    for (i in 0 until choices.length()) {
        val choice = choices.getJSONObject(i)
        val choiceData = Choice()
        choice.keys().forEach {
            when (it) {
                "index" -> {
                    choiceData.index = choice.getInt(it)
                }

                "message" -> {
                    parseMessage(choice, it, choiceData)

                }

                "logprobs" -> {
                    choiceData.logprobs = choice.get(it)
                }

                "finish_reason" -> {
                    choiceData.finishReason = choice.getString(it)
                }
            }
        }
        choiceList.add(choiceData)
    }
}

private fun parseMessage(
    choice: JSONObject,
    it: String?,
    choiceData: Choice
) {
    val message = choice.getJSONObject(it)
    val messageData = Message()
    message.keys().forEach { key ->
        when (key) {
            "role" -> {
                messageData.role = message.getString(key)
            }

            "content" -> {
                val content = message.getString(key)
                val contentObject = JSONObject(content)
                contentObject.keys().forEach {
                    when (it) {
                        "title" -> {
                            messageData.content.title = contentObject.getString(it)
                        }

                        "description" -> {
                            messageData.content.description = contentObject.getString(it)
                        }
                    }
                }

            }
        }
    }
    choiceData.message = messageData
}

fun String.trimExtraWhiteSpace(): String {
    return this.replace("\\s+".toRegex(), " ")
}
