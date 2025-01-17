package se.grayson.sfibuddy.presentation.addword.ui

import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import se.grayson.sfibuddy.presentation.addword.model.DisplayImage
import se.grayson.sfibuddy.presentation.imagepicker.CameraManager
import se.grayson.sfibuddy.presentation.imagepicker.SharedImage


class AddWordViewModel : ViewModel() {

    // region field

    private val _isLaunchCamera: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLaunchCamera: StateFlow<Boolean> get() = _isLaunchCamera

    private val _imageList: MutableStateFlow<MutableList<DisplayImage>> = MutableStateFlow(mutableListOf())
    val imageList: StateFlow<List<DisplayImage>> get() = _imageList

    private val _showLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showLoading: StateFlow<Boolean> = _showLoading

    // endregion

    // region public

    fun updateLaunchCamera(isLaunch: Boolean) {
        _isLaunchCamera.value = isLaunch
    }

    fun addImage(image: SharedImage) {
        viewModelScope.launch {
            _showLoading.update { true }
            val bitmap = withContext(Dispatchers.IO) {
                image.toImageBitmap()
            }
            bitmap ?: return@launch
            _imageList.value = _imageList.value.toMutableList().apply {
                println("bitmap.HashCode:${bitmap.hashCode()}")
                add(DisplayImage(bitmap.hashCode(), bitmap))
            }
            _showLoading.update { false }
        }
    }

    fun deleteImage(index: Int) {
        if (index !in _imageList.value.indices) return
        _imageList.value = _imageList.value.toMutableList().apply {
            removeAt(index)
        }
    }

    // endregion

    // region private

    // endregion
}