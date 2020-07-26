package jp.yuiki.common

import com.russhwolf.settings.Settings
import com.russhwolf.settings.invoke
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

internal expect val ApplicationDispatcher: CoroutineDispatcher

fun getTodos(callback: (List<Todo>) -> Unit) {
    val settings = Settings()
    val json = Json(JsonConfiguration.Stable)

    val savedJson = settings.getString("todos")
    if (savedJson.isNotEmpty()) {
        val parsed = json.parse(Todo.serializer().list, savedJson)
        callback(parsed)
    } else {
        val client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
        GlobalScope.launch(ApplicationDispatcher) {
            val url = "https://jsonplaceholder.typicode.com/todos"
            val res = client.get<List<Todo>>(url)

            val serialized = json.stringify(Todo.serializer().list, res)
            settings.putString("todos", serialized)

            delay(5000)
            callback(res)
        }
    }
}
