package com.stathis.unipiaudiostories.ui.intro.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {

    private val auth by lazy { FirebaseAuth.getInstance() }

    val userRegistered: LiveData<Boolean>
        get() = _userRegistered

    private val _userRegistered = MutableLiveData<Boolean>()

    fun validateData(email: String, pass: String, confirmPass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isPassValid = pass == confirmPass && pass.isNotEmpty() && confirmPass.isNotEmpty()
            if (email.isNotEmpty() && isPassValid) {
                registerUser(email, pass)
            } else {
                _userRegistered.postValue(false)
            }
        }
    }

    private suspend fun registerUser(email: String, pass: String) {
        try {
            val task = auth.createUserWithEmailAndPassword(email, pass).await()
            task.user?.let {
                _userRegistered.postValue(true)
            } ?: run {
                _userRegistered.postValue(false)
            }
        } catch (e: Exception) {
            _userRegistered.postValue(false)
        }
    }
}