package core.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import core.Constants

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.PREFS_FILE_NAME)