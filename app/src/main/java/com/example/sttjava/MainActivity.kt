package com.example.sttjava


import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

class MainActivity : ComponentActivity() {
    var txtView: TextView?=null
    var Button:Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitymain)
        txtView=findViewById(R.id.texto)
        Button=findViewById(R.id.BTEscuchar)
        Button?.setOnClickListener {
            habla()
        }
    }

    fun habla(){
        val intent=Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"hable ahora")
        startActivityForResult(intent,99)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==99&&resultCode== RESULT_OK){
            txtView?.setText(data!!.getStringArrayExtra(RecognizerIntent.EXTRA_RESULTS)!![0])
        }
    }
}
