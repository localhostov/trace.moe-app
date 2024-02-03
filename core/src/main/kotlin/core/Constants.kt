package core

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val DEFAULT_URL = "https://trace.moe"
    const val API_URL = "https://api.trace.moe"
    const val GITHUB_PROJECT_URL = "https://github.com/localhostov/trace.moe-app"
    const val PREFS_FILE_NAME = "prefs"
}

object Prefs {
    val ACCOUNT = stringPreferencesKey("ACCOUNT")
}
