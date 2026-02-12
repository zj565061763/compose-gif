package com.sd.demo.compose.gif

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sd.demo.compose.gif.theme.AppTheme
import com.sd.lib.compose.gif.GifView
import com.sd.lib.compose.gif.rememberGifControllerWithResource

class SampleActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AppTheme {
        Content()
      }
    }
  }
}

@Composable
private fun Content(
  modifier: Modifier = Modifier,
) {
  val controller = rememberGifControllerWithResource(R.drawable.anim_role_blink) {
    // 停止播放
    stopPlay()
    // 设置循环次数，默认0-无限循环
    setLoopCount(3)
  }

  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    GifView(
      modifier = Modifier
        .size(100.dp)
        .background(Color.Black),
      controller = controller,
    )

    Button(onClick = {
      // 开始播放
      controller.startPlay()
    }) {
      Text(text = "start")
    }

    Button(onClick = {
      // 停止播放
      controller.stopPlay()
    }) {
      Text(text = "stop")
    }
  }
}

@Preview
@Composable
private fun Preview() {
  AppTheme {
    Content()
  }
}