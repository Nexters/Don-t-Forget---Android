package nexters.hyomk.data.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

object SafeApiCall {
    suspend fun <T> call(response: Response<T>): Flow<T> {
        return flow {
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {
                val code = response.code()
                val errorMessage = response.errorBody()?.string()
                throw HttpException(code, errorMessage)
            }
        }
    }
}

class HttpException(val code: Int, private val errorMessage: String?) : RuntimeException() {
    override val message: String?
        get() = errorMessage
}