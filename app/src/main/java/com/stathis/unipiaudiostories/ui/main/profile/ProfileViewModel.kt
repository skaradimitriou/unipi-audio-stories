package com.stathis.unipiaudiostories.ui.main.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.unipiaudiostories.abstraction.BaseViewModel
import com.stathis.unipiaudiostories.util.authmanager.Authenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authenticator: Authenticator,
    app: Application
) : BaseViewModel(app) {

    val userEmail: LiveData<String>
        get() = _userEmail

    private val _userEmail = MutableLiveData<String>()

    val userLoggedOut: LiveData<Boolean>
        get() = _userLoggedOut

    private val _userLoggedOut = MutableLiveData<Boolean>()

    fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            val email = authenticator.getActiveUser()?.email ?: ""
            _userEmail.postValue(email)
        }
    }

    fun logoutUser() {
        authenticator.logout()
        _userLoggedOut.postValue(true)
    }
}