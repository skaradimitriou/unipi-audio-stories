package com.stathis.unipiaudiostories.ui.intro.register

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.stathis.unipiaudiostories.abstraction.BaseViewModel
import com.stathis.unipiaudiostories.models.domain.Result
import com.stathis.unipiaudiostories.util.GENERIC_ERROR
import com.stathis.unipiaudiostories.util.authmanager.Authenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authenticator: Authenticator,
    app: Application
) : BaseViewModel(app) {

    /**
     * LiveData that holds the Result of the registration FirebaseAuth transaction.
     */

    val registrationResult: LiveData<Result<FirebaseUser>>
        get() = _registrationResult

    private val _registrationResult = MutableLiveData<Result<FirebaseUser>>()

    fun validateData(email: String, pass: String, confirmPass: String) {
        _registrationResult.postValue(Result.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val isPassValid = pass == confirmPass && pass.isNotEmpty() && confirmPass.isNotEmpty()
            if (email.isNotEmpty() && isPassValid) {
                val result = authenticator.register(email, pass)
                _registrationResult.postValue(result)
            } else {
                _registrationResult.postValue(Result.Failure(GENERIC_ERROR))
            }
        }
    }
}