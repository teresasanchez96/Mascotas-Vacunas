package proyectoFCT.mascotasyvacunas.authentication.data.remote

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import proyectoFCT.mascotasyvacunas.authentication.data.local.TokenManager

class JwtInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val token = runBlocking {
            tokenManager.obtenerToken()
        }

        val nuevaPeticion = if (token != null) {
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }

        return chain.proceed(nuevaPeticion)
    }
}