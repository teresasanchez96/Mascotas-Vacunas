package proyectoFCT.mascotasyvacunas.authentication.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import proyectoFCT.mascotasyvacunas.authentication.data.local.TokenManager
import proyectoFCT.mascotasyvacunas.authentication.data.remote.AuthenticationClient
import proyectoFCT.mascotasyvacunas.authentication.domain.LoginUseCase
import proyectoFCT.mascotasyvacunas.authentication.domain.RegistroUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {
    @Provides
    @Singleton
    fun provideTokenManager(
        @ApplicationContext context: Context
    ): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(
        authenticationClient: AuthenticationClient,
        tokenManager: TokenManager
    ): LoginUseCase {
        return LoginUseCase(
            authenticationClient = authenticationClient,
            tokenManager = tokenManager
        )
    }

    @Provides
    @Singleton
    fun provideRegistroUseCase(
        authenticationClient: AuthenticationClient
    ): RegistroUseCase {
        return RegistroUseCase(
            authenticationClient = authenticationClient
        )
    }
}