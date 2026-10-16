package proyectoFCT.mascotasyvacunas.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import proyectoFCT.mascotasyvacunas.authentication.data.local.TokenManager
import proyectoFCT.mascotasyvacunas.authentication.data.remote.AuthenticationClient
import proyectoFCT.mascotasyvacunas.authentication.data.remote.JwtInterceptor
import proyectoFCT.mascotasyvacunas.utils.Constantes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenManager: TokenManager
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                JwtInterceptor(tokenManager)
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @Provides
    @Singleton
    fun provideAuthenticationClient(
        retrofit: Retrofit
    ): AuthenticationClient {
        return retrofit.create(
            AuthenticationClient::class.java
        )
    }
}