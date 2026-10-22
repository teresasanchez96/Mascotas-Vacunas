package proyectoFCT.mascotasyvacunas.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import proyectoFCT.mascotasyvacunas.authentication.data.local.TokenManager
import proyectoFCT.mascotasyvacunas.authentication.data.remote.AuthenticationClient
import proyectoFCT.mascotasyvacunas.authentication.data.remote.JwtInterceptor
import proyectoFCT.mascotasyvacunas.mascotas.data.network.MascotaClient
import proyectoFCT.mascotasyvacunas.utils.Constantes
import proyectoFCT.mascotasyvacunas.vacunaciones.data.network.VacunacionClient
import proyectoFCT.mascotasyvacunas.vacunas.data.network.VacunaClient
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

    @Provides
    @Singleton
    fun provideMascotaClient(
        retrofit: Retrofit
    ): MascotaClient {
        return retrofit.create(
            MascotaClient::class.java
        )
    }

    @Provides
    @Singleton
    fun provideVacunaClient(
        retrofit: Retrofit
    ): VacunaClient {
        return retrofit.create(
            VacunaClient::class.java
        )
    }

    @Provides
    @Singleton
    fun provideVacunacionClient(
        retrofit: Retrofit
    ): VacunacionClient {
        return retrofit.create(
            VacunacionClient::class.java)
    }
}