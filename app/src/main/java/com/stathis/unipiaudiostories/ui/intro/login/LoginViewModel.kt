package com.stathis.unipiaudiostories.ui.intro.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.unipiaudiostories.abstraction.BaseViewModel
import com.stathis.unipiaudiostories.models.domain.Result
import com.stathis.unipiaudiostories.util.GENERIC_ERROR
import com.stathis.unipiaudiostories.util.authmanager.Authenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticator: Authenticator,
    app: Application
) : BaseViewModel(app) {

    /**
     * LiveData that holds the Result of the login FirebaseAuth transaction.
     */

    val loginResult: LiveData<Result>
        get() = _loginResult

    private val _loginResult = MutableLiveData<Result>()

    fun login(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                val result = authenticator.login(email, pass)
                _loginResult.postValue(result)
            } else {
                _loginResult.postValue(Result.Failure(GENERIC_ERROR))
            }
        }
    }
}