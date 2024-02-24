package nexters.hyomk.data.util

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import nexters.hyomk.domain.model.BaseError
import retrofit2.Response

object SafeApiCall {
    suspend fun <T> call(response: Response<T>): Flow<T> {
        try {
            return flow {
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(it)
                    }
                } else {
                    val code = response.code()
                    val errorMessage = response.errorBody()?.string()
                    val error = Gson().fromJson<BaseError>(errorMessage, BaseError::class.java)
                    throw HttpException(code, error)
                }
            }.flowOn(Dispatchers.IO)
        } catch (e: Exception) {
            throw e
        }
    }
}

class HttpException(val code: Int, private val error: BaseError?) : RuntimeException() {
    override val message: String
        get() = error?.message ?: ""
}
