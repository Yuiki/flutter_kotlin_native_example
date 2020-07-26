package jp.yuiki.common

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

internal expect val ApplicationDispatcher: CoroutineDispatcher

fun getTodos(callback: (List<Todo>) -> Unit) {
    val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }
    GlobalScope.launch(ApplicationDispatcher) {
        val url = "https://jsonplaceholder.typicode.com/todos"
        val res = client.get<List<Todo>>(url)
        callback(res)
    }
}
