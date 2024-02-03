package lol.hostov.tracemoe.presentation.ui.screens.account

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import core.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lol.hostov.tracemoe.repository.ApiRepository
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val api: ApiRepository,
    private val store: DataStore<Preferences>
) : ViewModel() {
    fun getMe() {
        viewModelScope.launch {
            val request = api.getMe()
            val body = request.body()

            if (request.isSuccessful && body != null) {
                store.edit {
                    it[Prefs.ACCOUNT] = Gson().toJson(body)
                }
            }
        }
    }
}