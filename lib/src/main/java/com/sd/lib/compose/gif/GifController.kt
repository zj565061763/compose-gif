package com.sd.lib.compose.gif

import android.graphics.drawable.Drawable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import pl.droidsonroids.gif.GifDrawable

interface GifController {
  /** 是否正在播放 */
  fun isPlaying(): Boolean

  /** 开始播放 */
  fun startPlay()

  /** 停止播放 */
  fun stopPlay()

  /** 设置循环播放次数[0-65535]，默认0-无限循环 */
  fun setLoopCount(loopCount: Int)
}

internal open class GifControllerImpl : GifController {
  private var _gifDrawable by mutableStateOf<GifDrawable?>(null)

  @Volatile
  private var _shouldPlay = true

  @Volatile
  private var _loopCount = 0

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

  override fun setLoopCount(loopCount: Int) {
    val safeCount = loopCount.coerceAtMost(MAX_LOOP_COUNT)
    _loopCount = safeCount
    _gifDrawable?.loopCount = safeCount
  }

  private fun updatePlayState() {
    _gifDrawable?.loopCount = _loopCount
    if (_shouldPlay) {
      _gifDrawable?.start()
    } else {
      _gifDrawable?.stop()
      _gifDrawable?.seekToFrame(0)
    }
  }
}

private const val MAX_LOOP_COUNT = 65535