package com.devlopershankar.videostreamingapp.uiscreen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devlopershankar.videostreamingapp.RTSPViewModel
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.MimeTypes

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("RememberReturnType")
@Composable
fun RTSPStreamingScreen(viewModel: RTSPViewModel = viewModel(), modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var player by remember { mutableStateOf<ExoPlayer?>(null) }
    var rtspUrl by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Get RTSP URL from ViewModel
    LaunchedEffect(viewModel.rtspUrl.collectAsState().value) {
        rtspUrl = viewModel.rtspUrl.value
    }

    DisposableEffect(Unit) {
        player = initializePlayer(context, rtspUrl)

        onDispose {
            releasePlayer()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        OutlinedTextField(
            value = rtspUrl,
            onValueChange = { rtspUrl = it },
            label = { Text("Enter RTSP URL") },
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions(onDone = {
                viewModel.saveRtspUrl(rtspUrl)
                keyboardController?.hide()
            }),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(onClick = {
                releasePlayer()
                player = initializePlayer(context, rtspUrl)
            }) { Text("Play") }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { player?.pause() }) { Text("Pause") }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                releasePlayer()
            }) { Text("Stop") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (player != null) {
            AndroidView(
                factory = { PlayerView(context).apply { this.player = player } },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

// Global player instance
var player: ExoPlayer? = null

// Initialize ExoPlayer
fun initializePlayer(context: Context, url: String): ExoPlayer {
    if (player == null) {
        val loadControl = DefaultLoadControl.Builder()
            .setBufferDurationsMs(
                DefaultLoadControl.DEFAULT_MIN_BUFFER_MS,
                DefaultLoadControl.DEFAULT_MAX_BUFFER_MS,
                DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS,
                DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS
            )
            .build()

        player = ExoPlayer.Builder(context)
            .setLoadControl(loadControl)
            .setSeekBackIncrementMs(5000)
            .setSeekForwardIncrementMs(5000)
            .build()
    }

    val mediaItem = MediaItem.Builder()
        .setUri(Uri.parse(url))
        .setMimeType(MimeTypes.APPLICATION_RTSP)
        .build()

    player?.setMediaItem(mediaItem)
    player?.prepare()
    player?.playWhenReady = true

    player?.repeatMode = Player.REPEAT_MODE_OFF
    player?.addListener(object : Player.Listener {
        override fun onPlaybackStateChanged(state: Int) {
            when (state) {
                Player.STATE_BUFFERING -> Log.d("Player", "Buffering...")
                Player.STATE_READY -> Log.d("Player", "Playing...")
                Player.STATE_ENDED -> Log.d("Player", "Playback Ended")
                Player.STATE_IDLE -> Log.d("Player", "Player Idle")
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            Log.e("Player", "Error: ${error.message}")
        }
    })

    return player!!
}

// Release ExoPlayer
fun releasePlayer() {
    player?.release()
    player = null
}