package proyectoFCT.mascotasyvacunas.authentication.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "authentication")

private val TOKEN_KEY = stringPreferencesKey("jwt_token")

class TokenManager(
    private val context: Context
) {

    suspend fun guardarToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun obtenerToken(): String? {
        return context.dataStore.data
            .map { preferences ->
                preferences[TOKEN_KEY]
            }
            .first()
    }

    suspend fun eliminarToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }
}