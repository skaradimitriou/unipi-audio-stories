package com.stathis.unipiaudiostories.util.authmanager

import com.google.firebase.auth.FirebaseUser
import com.stathis.unipiaudiostories.models.domain.Result

/**
 * [AuthManager] is responsible for the authorization features inside the app.
 */

interface AuthManager {

    /**
     * Register feature
     */

    suspend fun register(email: String, pass: String): Result

    /**
     * Login Feature
     */

    suspend fun login(email: String, pass: String): Result

    /**
     * Returns whether or not there is a user active at the moment.
     */

    fun isUserActive(): Boolean

    /**
     * Returns the current user that is logged in.
     */

    fun getActiveUser() : FirebaseUser?
}