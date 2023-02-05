package com.stathis.unipiaudiostories.models.repository.profile

import android.graphics.Bitmap
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.stathis.unipiaudiostories.util.DOWNLOAD_LINK
import com.stathis.unipiaudiostories.util.USERS_DB_PATH
import com.stathis.unipiaudiostories.util.authmanager.Authenticator
import com.stathis.unipiaudiostories.util.compressBitmap
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val authenticator: Authenticator,
    private val dbRef: DatabaseReference,
    private val storage: StorageReference,
) : ProfileRepository {

    override suspend fun saveUserImage(bitmap: Bitmap) = flow {
        val storageRef = storage.child("pics/${authenticator.getActiveUser()?.uid}")
        val image = bitmap.compressBitmap()
        val upload = storageRef.putBytes(image).await()
        val downloadUrl = upload.metadata?.reference?.downloadUrl?.await().toString()
        val uuid = authenticator.getActiveUser()?.uid.toString()

        dbRef.child(USERS_DB_PATH)
            .child(uuid)
            .child(DOWNLOAD_LINK)
            .setValue(downloadUrl).await()

        emit(downloadUrl)
    }

    override suspend fun getUserImage() = flow {
        val uuid = authenticator.getActiveUser()?.uid.toString()
        val imageUrl = dbRef.child(USERS_DB_PATH)
            .child(uuid).child(DOWNLOAD_LINK)
            .get()
            .await()
            .value.toString()

        emit(imageUrl)
    }
}
