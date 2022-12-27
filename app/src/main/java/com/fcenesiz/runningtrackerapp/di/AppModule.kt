package com.fcenesiz.runningtrackerapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fcenesiz.runningtrackerapp.db.RunDAO
import com.fcenesiz.runningtrackerapp.db.RunningDatabase
import com.fcenesiz.runningtrackerapp.other.Constants.RUNNING_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(@ApplicationContext app: Context): RunningDatabase =
        Room.databaseBuilder(
            app,
            RunningDatabase::class.java,
            RUNNING_DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideRunDAO(db: RunningDatabase): RunDAO = db.getRunDao()

}