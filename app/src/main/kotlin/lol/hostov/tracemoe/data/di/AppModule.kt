package lol.hostov.tracemoe.data.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import core.extensions.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import lol.hostov.tracemoe.data.remote.RetrofitInstance
import lol.hostov.tracemoe.repository.ApiRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiRepository(): ApiRepository {
        val service = RetrofitInstance().service

        return ApiRepository(service)
    }

    @Provides
    @Singleton
    fun providePlayer(app: Application): Player {
        return ExoPlayer.Builder(app).build()
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}