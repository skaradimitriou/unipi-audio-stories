package com.stathis.unipiaudiostories.ui.intro.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    private val auth by lazy { FirebaseAuth.getInstance() }

    val loginResult: LiveData<Boolean>
        get() = _loginResult

    private val _loginResult = MutableLiveData<Boolean>()

    fun login(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                loginUser(email, pass)
            } else {
                _loginResult.postValue(false)
            }
        }
    }

    private suspend fun loginUser(email: String, pass: String) {
        try {
            val task = auth.signInWithEmailAndPassword(email, pass).await()
            task.user?.let {
                _loginResult.postValue(true)
            } ?: run {
                _loginResult.postValue(false)
            }
        } catch (e: FirebaseAuthException) {
            _loginResult.postValue(false)
        }
    }
}