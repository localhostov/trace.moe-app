package lol.hostov.tracemoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import core.Prefs
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lol.hostov.tracemoe.data.remote.model.MeResponse
import lol.hostov.tracemoe.repository.ApiRepository
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val api: ApiRepository,
    private val store: DataStore<Preferences>
) : ViewModel() {
    var account by mutableStateOf<MeResponse?>(null)
        private set

    // This runBlocking is needed to wait until we are sure
    // that account info has been accurately received from server
    fun getMeInfo() = runBlocking {
        val response = api.getMe()
        val body = response.body()

        if (response.isSuccessful && body != null) {
            account = body
            store.edit {
                it[Prefs.ACCOUNT] = Gson().toJson(body)
            }
        }

        startAccountDataCollector()
    }

    private fun startAccountDataCollector() {
        viewModelScope.launch {
            store.data.collect { prefs ->
                account = prefs[Prefs.ACCOUNT]?.let {
                    Gson().fromJson(it, MeResponse::class.java)
                }
            }
        }
    }
}