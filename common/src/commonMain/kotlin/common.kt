package jp.yuiki.common

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal expect val ApplicationDispatcher: CoroutineDispatcher

fun testApi(callback: (ByteArray) -> Unit) {
    val client = HttpClient()
    GlobalScope.launch(ApplicationDispatcher) {
        val url = "https://jsonplaceholder.typicode.com/todos"
        val res = client.get<ByteArray>(url)
        callback(res)
    }
}
