package com.stathis.unipiaudiostories.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.stathis.unipiaudiostories.db.StoriesDao
import com.stathis.unipiaudiostories.db.StoriesDatabase
import com.stathis.unipiaudiostories.models.repo.StoryRepository
import com.stathis.unipiaudiostories.models.repo.StoryRepositoryImpl
import com.stathis.unipiaudiostories.util.authmanager.Authenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStoriesRepository(
        onlineDb: DatabaseReference,
        localDb: StoriesDao,
        authenticator: Authenticator
    ): StoryRepository {
        return StoryRepositoryImpl(onlineDb, localDb, authenticator)
    }

    @Provides
    @Singleton
    fun provideFirebaseDbReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    @Provides
    @Singleton
    fun provideLocalDbReference(app: Application): StoriesDao {
        return StoriesDatabase.getDatabase(app).countriesDao()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthenticator(auth : FirebaseAuth): Authenticator {
        return Authenticator(auth)
    }
}