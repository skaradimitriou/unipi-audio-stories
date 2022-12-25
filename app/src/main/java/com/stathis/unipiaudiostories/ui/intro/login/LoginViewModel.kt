package com.stathis.unipiaudiostories.ui.intro.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stathis.unipiaudiostories.models.domain.Result
import com.stathis.unipiaudiostories.util.GENERIC_ERROR
import com.stathis.unipiaudiostories.util.authmanager.Authenticator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    /**
     * LiveData that holds the Result of the login [FirebaseAuth] transaction.
     */
    
    val loginResult: LiveData<Result>
        get() = _loginResult

    private val _loginResult = MutableLiveData<Result>()

    fun login(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                val result = Authenticator.login(email, pass)
                _loginResult.postValue(result)
            } else {
                _loginResult.postValue(Result.Failure(GENERIC_ERROR))
            }
        }
    }
}