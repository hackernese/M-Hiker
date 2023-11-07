package com.example.m_hiker.Dialogs;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.speech.RecognizerIntent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class SpeechRecognizer extends AppCompatActivity {

    public static Intent intent;

    public static ActivityResultLauncher<Intent> launcher;
    private static SpeechRecognizer recognizer = null;
    public SpeechRecognizer init(ActivityResultLauncher<Intent> activity){
        if(recognizer==null){
            SpeechRecognizer.intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            SpeechRecognizer.intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            SpeechRecognizer.intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start speaking");
            SpeechRecognizer.launcher = activity;
        }
        return this.recognizer;
    }


}
