package com.sd.lib.compose.gif

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.droidsonroids.gif.GifDrawable

@Composable
fun rememberGifController(
  @RawRes @DrawableRes resId: Int,
  onError: (Throwable) -> Unit = {},
  init: GifController.() -> Unit = {},
): GifController {
  val onErrorUpdated by rememberUpdatedState(onError)
  return remember(resId) {
    GifControllerResource(resId).apply(init)
  }.also { controller ->
    val context = LocalContext.current
    LaunchedEffect(controller, resId) {
      withContext(Dispatchers.IO) {
        runCatching { GifDrawable(context.resources, resId) }
      }.onFailure(onErrorUpdated).getOrNull().also { drawable ->
        controller.setDrawable(drawable)
      }
    }
    DisposableEffect(controller) {
      onDispose { controller.setDrawable(null) }
    }
  }
}

internal class GifControllerResource(
  val resId: Int,
) : GifControllerImpl()