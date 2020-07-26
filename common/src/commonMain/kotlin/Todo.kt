package jp.yuiki.common

import kotlinx.serialization.Serializable

@Serializable
data class Todo(
        val userId: Int,
        val id: Int,
        val title: String,
        val completed: Boolean
) {
    fun toHashMap(): HashMap<String, Any> =
            HashMap<String, Any>().apply {
                put("userId", userId)
                put("id", id)
                put("title", title)
                put("completed", completed)
            }
}
