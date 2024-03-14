package co.luminositylabs.examples

import co.luminositylabs.oss.examples.main
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.test.Test

val logger = KotlinLogging.logger {}

class CoroutinesTest {
    @Test
    fun `Example Basic NonBlocking Test`() {
        logger.trace { "example basic non-blocking test" }
        main()
    }
}
