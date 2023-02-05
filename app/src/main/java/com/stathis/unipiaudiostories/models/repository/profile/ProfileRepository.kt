package com.stathis.unipiaudiostories.models.repository.profile

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun saveUserImage(bitmap: Bitmap) : Flow<String>

    suspend fun getUserImage(): Flow<String>
}