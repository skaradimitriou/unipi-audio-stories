package com.stathis.unipiaudiostories.ui.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    val userActive: LiveData<Boolean>
        get() = _userActive

    private val _userActive = MutableLiveData<Boolean>()

    fun isUserActive() {
        val isActive = auth.currentUser != null
        _userActive.postValue(isActive)
    }
}