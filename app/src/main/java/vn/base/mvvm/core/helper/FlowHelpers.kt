package vn.base.mvvm.core.helper

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.util.*

fun <T> Flow<T>.asCommonFlow(): CFlow<T> = CFlow(this)
class CFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {
    fun watch(block: (T) -> Unit) {
        val job = Job()

        onEach {
            block(it)
        }.launchIn(CoroutineScope(Dispatchers.Main + job))
    }
}

@ExperimentalCoroutinesApi
fun tickFlow(millis: Long = 1000L) = callbackFlow<Int> {
    val timer = Timer()
    var time = 0
    timer.scheduleAtFixedRate(
        object : TimerTask() {
            override fun run() {
                try {
                    trySend(time)
                } catch (e: Exception) {
                }
                time += 1
            }
        },
        0,
        millis
    )
    awaitClose {
        timer.cancel()
    }
}

fun <T, R> zip(
    vararg flows: Flow<T>,
    transform: suspend (List<T>) -> R
): Flow<R> = when(flows.size) {
    0 -> error("No flows")
    1 -> flows[0].map{ transform(listOf(it)) }
    2 -> flows[0].zip(flows[1]) { a, b -> transform(listOf(a, b)) }
    else -> {
        var accFlow: Flow<List<T>> = flows[0].zip(flows[1]) { a, b -> listOf(a, b) }
        for (i in 2 until flows.size) {
            accFlow = accFlow.zip(flows[i]) { list, it ->
                list + it
            }
        }
        accFlow.map(transform)
    }
}