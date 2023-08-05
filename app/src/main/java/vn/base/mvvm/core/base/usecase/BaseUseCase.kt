package vn.base.mvvm.core.base.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import vn.base.mvvm.core.delivery.Result

abstract class BaseUseCase<T, in P : UseCaseParameters> {

    protected abstract suspend fun FlowCollector<Result<T>>.run(params: P)

    operator fun invoke(params: P) =
        flow<Result<T>> {
            emit(Result.Loading)
            run(params)
        }.flowOn(Dispatchers.IO)
}

interface UseCaseParameters

object None : UseCaseParameters
