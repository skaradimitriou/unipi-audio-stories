package com.stathis.unipiaudiostories.ui.main.play

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stathis.unipiaudiostories.abstraction.BaseViewModel

class PlayStoryViewModel(app: Application) : BaseViewModel(app) {

    val buttonState: LiveData<Boolean>
        get() = _buttonState

    private val _buttonState = MutableLiveData<Boolean>()

    val test = MutableLiveData<Int>()

    private var isPlaying = false

    fun playButtonClicked() {
        isPlaying = !isPlaying
        _buttonState.postValue(isPlaying)
    }
}