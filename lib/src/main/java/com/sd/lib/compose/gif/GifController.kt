package com.sd.lib.compose.gif

import android.graphics.drawable.Drawable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import pl.droidsonroids.gif.GifDrawable

interface GifController {
  fun isPlaying(): Boolean
  fun startPlay()
  fun stopPlay()
}

internal open class GifControllerImpl : GifController {
  private var _gifDrawable by mutableStateOf<GifDrawable?>(null)

  @Volatile
  private var _shouldPlay = true

  fun getDrawable(): Drawable? {
    return _gifDrawable
  }

  fun setDrawable(drawable: GifDrawable?) {
    if (_gifDrawable != drawable) {
      runCatching { _gifDrawable?.recycle() }
      _gifDrawable = drawable
      updatePlayState()
    }
  }

  override fun isPlaying(): Boolean {
    return _gifDrawable?.isPlaying == true
  }

  override fun startPlay() {
    _shouldPlay = true
    updatePlayState()
  }

  override fun stopPlay() {
    _shouldPlay = false
    updatePlayState()
  }

  private fun updatePlayState() {
    if (_shouldPlay) {
      _gifDrawable?.start()
    } else {
      _gifDrawable?.stop()
      _gifDrawable?.seekToFrame(0)
    }
  }
}