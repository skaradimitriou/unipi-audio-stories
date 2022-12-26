package com.stathis.unipiaudiostories.ui.start

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
class StartingViewModel @Inject constructor(
    private val auth: Authenticator,
    app: Application
) : BaseViewModel(app) {

    val userIsLoggedIn: LiveData<Boolean>
        get() = _userIsLoggedIn

    private val _userIsLoggedIn = MutableLiveData<Boolean>()

    fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            auth.getActiveUser()?.let { user ->
                _userIsLoggedIn.postValue(true)
            } ?: kotlin.run {
                _userIsLoggedIn.postValue(false)
            }
        }
    }
}