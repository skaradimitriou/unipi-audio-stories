package com.stathis.unipiaudiostories.ui.intro.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stathis.unipiaudiostories.models.domain.Result
import com.stathis.unipiaudiostories.util.GENERIC_ERROR
import com.stathis.unipiaudiostories.util.authmanager.Authenticator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    /**
     * LiveData that holds the Result of the registration [FirebaseAuth] transaction.
     */

    val registrationResult: LiveData<Result>
        get() = _registrationResult

    private val _registrationResult = MutableLiveData<Result>()

    fun validateData(email: String, pass: String, confirmPass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isPassValid = pass == confirmPass && pass.isNotEmpty() && confirmPass.isNotEmpty()
            if (email.isNotEmpty() && isPassValid) {
                val result = Authenticator.register(email, pass)
                _registrationResult.postValue(result)
            } else {
                _registrationResult.postValue(Result.Failure(GENERIC_ERROR))
            }
        }
    }
}