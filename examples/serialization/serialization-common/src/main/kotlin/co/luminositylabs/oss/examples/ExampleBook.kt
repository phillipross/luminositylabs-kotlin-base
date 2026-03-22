package co.luminositylabs.oss.examples

import java.util.UUID

data class ExampleBook(
    val id: UUID,
    val title: String,
    val authorName: String,
    val fiction: Boolean? = null,
)
