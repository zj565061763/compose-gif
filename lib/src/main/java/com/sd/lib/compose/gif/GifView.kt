package com.sd.lib.compose.gif

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource

@Composable
fun GifView(
  modifier: Modifier = Modifier,
  controller: GifController,
  contentDescription: String? = null,
  contentScale: ContentScale = ContentScale.Fit,
  @DrawableRes previewResId: Int = 0,
) {
  require(controller is GifControllerImpl)

  if (LocalInspectionMode.current) {
    val resId = previewResId.takeIf { it != 0 } ?: (controller as? GifControllerResource)?.resId
    if (resId != null) {
      Image(
        modifier = modifier,
        painter = painterResource(resId),
        contentDescription = contentDescription,
        contentScale = contentScale,
      )
    } else {
      Box(modifier = modifier)
    }
    return
  }

  Image(
    modifier = modifier,
    painter = rememberDrawablePainter(controller.getDrawable()),
    contentDescription = contentDescription,
    contentScale = contentScale,
  )
}