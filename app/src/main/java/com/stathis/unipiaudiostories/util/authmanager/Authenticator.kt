package com.stathis.unipiaudiostories.util.authmanager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.stathis.unipiaudiostories.models.domain.Result
import com.stathis.unipiaudiostories.util.GENERIC_ERROR
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * [Authenticator] singleton class is responsible for all the transactions between the app
 * and [FirebaseAuth].
 */

class Authenticator @Inject constructor(
    private val auth: FirebaseAuth
) : AuthManager {

    /**
     * Attempts to register a user (email, pass) async and returns the result.
     */

    override suspend fun register(email: String, pass: String): Result {
        try {
            val task = auth.createUserWithEmailAndPassword(email, pass).await()
            task.user?.let {
                return Result.Success(it)
            } ?: kotlin.run {
                return Result.Failure(GENERIC_ERROR)
            }
        } catch (e: FirebaseAuthException) {
            return Result.Failure(e.localizedMessage ?: GENERIC_ERROR)
        }
    }

    /**
     * Attempts to login a user (email, pass) async and returns the result.
     */

    override suspend fun login(email: String, pass: String): Result {
        try {
            val task = auth.signInWithEmailAndPassword(email, pass).await()
            task.user?.let {
                return Result.Success(it)
            } ?: run {
                return Result.Failure(GENERIC_ERROR)
            }
        } catch (e: FirebaseAuthException) {
            return Result.Failure(e.localizedMessage ?: GENERIC_ERROR)
        }
    }

    override fun isUserActive(): Boolean = auth.currentUser != null

    override fun getActiveUser(): FirebaseUser? = auth.currentUser

    override fun logout() {
        auth.signOut()
    }
}