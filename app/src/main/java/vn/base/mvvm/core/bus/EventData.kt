package vn.base.mvvm.core.bus

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import java.lang.Exception

data class EventData<T>(
    val coroutineScope: CoroutineScope,
    val eventDispatcher: CoroutineDispatcher,
    val onEvent: (T) -> Unit,
    val exception: ((Throwable)->Unit)? = null
) {

    private val channel = Channel<T>()

    init {
        coroutineScope.launch {
            channel.consumeEach {
                launch(eventDispatcher) {

                    if (exception!=null) {

                        try{
                            onEvent(it)
                        } catch (e:Exception) {

                            exception.invoke(e)
                        }
                    } else {

                        onEvent(it)
                    }
                }
            }
        }
    }

    fun postEvent(event: Any) {
        if (!channel.isClosedForSend) {

            coroutineScope.launch {
                @Suppress("UNCHECKED_CAST")
                channel.send(event as T)
            }
        } else {
            println("Channel is closed for send")
        }
    }

    fun cancel() {
        channel.cancel()
    }
}