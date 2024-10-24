package com.example.sttjava

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SpeakNowApp() }
    }
}

@Composable
fun SpeakNowApp() {
    var text by remember { mutableStateOf("hola mundo") }

    val speechLauncher = rememberSpeechRecognizerLauncher { result -> result?.let { text = it } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { speechLauncher.launch(RecognizerIntent()) },
            modifier = Modifier.padding(8.dp)
        ) { Text(text = "hable ahora", fontSize = 20.sp) }
    }
}

fun RecognizerIntent(): Intent {
    return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_PROMPT, "hable ahora")
    }
}

@Composable
fun rememberSpeechRecognizerLauncher(onResult: (String?) -> Unit): ActivityResultLauncher<Intent> {
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        val speechResult = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
        onResult(speechResult?.getOrNull(0))
    }
    return launcher
}