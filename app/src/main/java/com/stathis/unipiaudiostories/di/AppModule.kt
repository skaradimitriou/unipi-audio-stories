package com.stathis.unipiaudiostories.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.stathis.unipiaudiostories.db.StoriesDao
import com.stathis.unipiaudiostories.db.StoriesDatabase
import com.stathis.unipiaudiostories.db.StoryStatisticsDao
import com.stathis.unipiaudiostories.models.repository.profile.ProfileRepository
import com.stathis.unipiaudiostories.models.repository.profile.ProfileRepositoryImpl
import com.stathis.unipiaudiostories.models.repository.stories.StoryRepository
import com.stathis.unipiaudiostories.models.repository.stories.StoryRepositoryImpl
import com.stathis.unipiaudiostories.tts.MyTTsFeatures
import com.stathis.unipiaudiostories.tts.TextToSpeechUtil
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
        storiesDao: StoriesDao,
        statisticsDao: StoryStatisticsDao,
        authenticator: Authenticator
    ): StoryRepository {
        return StoryRepositoryImpl(onlineDb, storiesDao, statisticsDao, authenticator)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        authenticator: Authenticator,
        dbRef: DatabaseReference,
        storage: StorageReference,
        storiesDao: StoriesDao,
        storyStatisticsDao: StoryStatisticsDao
    ): ProfileRepository {
        return ProfileRepositoryImpl(authenticator, dbRef, storage, storiesDao, storyStatisticsDao)
    }

    @Provides
    @Singleton
    fun provideFirebaseDbReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    @Provides
    @Singleton
    fun provideStoriesDbReference(app: Application): StoriesDao {
        return StoriesDatabase.getDatabase(app).countriesDao()
    }

    @Provides
    @Singleton
    fun provideStatisticsDbReference(app: Application): StoryStatisticsDao {
        return StoriesDatabase.getDatabase(app).statisticsDao()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): StorageReference = FirebaseStorage.getInstance().reference

    @Provides
    @Singleton
    fun provideAuthenticator(auth: FirebaseAuth): Authenticator {
        return Authenticator(auth)
    }

    @Provides
    @Singleton
    fun provideTTs(): MyTTsFeatures {
        return TextToSpeechUtil()
    }
}