package com.example.m_hiker.Home;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechRecognizer {
    public android.speech.SpeechRecognizer speechRecognizer;
    public Intent recognizerIntent;
    // Used for converting speech to text later
    public SpeechRecognizer(FragmentActivity activity){
        speechRecognizer = android.speech.SpeechRecognizer.createSpeechRecognizer(activity);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle results) {
                // Called when the recognition process is complete.
                ArrayList<String> result = results.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION);
                if (result != null) {
                    String recognizedText = result.get(0); // Get the first result
                    Log.d("debug", "TEXT => " + recognizedText);
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    }

}
