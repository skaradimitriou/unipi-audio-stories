package com.stathis.unipiaudiostories.ui.main.details

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.stathis.unipiaudiostories.abstraction.BaseViewModel
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.models.repository.stories.StoryRepositoryImpl
import com.stathis.unipiaudiostories.util.FAVORITES_DB_PATH
import com.stathis.unipiaudiostories.util.authmanager.Authenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val app: Application,
    private val authenticator: Authenticator,
    private val dbRef: DatabaseReference,
    private val repo: StoryRepositoryImpl,
) : BaseViewModel(app), FavoritesUtil {

    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val _isFavorite = MutableLiveData(false)

    val keywords: LiveData<String>
        get() = _keywords

    private val _keywords = MutableLiveData("")

    private val _favorites = MutableLiveData<List<Story>>(listOf())

    private var _tempFavoriteList = mutableListOf<Story>()

    private var _favoriteState = false
    var currentStory: Story? = null

    init {
        getUserFavorites()
    }

    fun extractTextFromImage(imageUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = Glide.with(app).asBitmap().load(imageUrl).submit().get()
            val image2 = InputImage.fromBitmap(bitmap, 0)
            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
            val task = labeler.process(image2).await()
            val output = task.map { it.text }.toString()
            _keywords.postValue("Keywords: $output")
            labeler.close()
        }
    }

    fun observe(owner: LifecycleOwner) {
        _favorites.observe(owner) {
            val existsOrNot = _tempFavoriteList.any { it.title == currentStory?.title }
            _favoriteState = existsOrNot
            _isFavorite.postValue(existsOrNot)
        }
    }

    fun favoriteIconClicked() {
        if (_favoriteState) {
            deleteFromFavorites()
        } else {
            addToFavorites()
        }
    }

    override fun addToFavorites() {
        val uuid = authenticator.getActiveUser()?.uid.toString()
        viewModelScope.launch(Dispatchers.IO) {
            currentStory?.let {
                _tempFavoriteList.add(it)
            }

            dbRef.child(FAVORITES_DB_PATH).child(uuid).setValue(_tempFavoriteList).await()
            getUserFavorites()
        }
    }

    override fun deleteFromFavorites() {
        val uuid = authenticator.getActiveUser()?.uid.toString()
        viewModelScope.launch(Dispatchers.IO) {
            _tempFavoriteList.removeAll { it.title == currentStory?.title }
            dbRef.child(FAVORITES_DB_PATH).child(uuid).setValue(_tempFavoriteList).await()
            getUserFavorites()
        }
    }

    override fun getUserFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllFavorites().collect { list ->
                _tempFavoriteList.clear()
                _tempFavoriteList.addAll(list)
                _favorites.postValue(_tempFavoriteList)
            }
        }
    }
}