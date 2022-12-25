package com.stathis.unipiaudiostories.ui.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class IntroViewModel : ViewModel() {

    val userActive: LiveData<Boolean>
        get() = _userActive

    private val _userActive = MutableLiveData<Boolean>()

    private val auth = FirebaseAuth.getInstance()

    fun isUserActive() {
        val isActive = auth.currentUser != null
        _userActive.postValue(isActive)
    }
}