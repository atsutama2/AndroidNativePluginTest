package jp.co.test.nativespeechrecognizer;

import android.content.Context;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import com.unity3d.player.UnityPlayerActivity;

import static com.unity3d.player.UnityPlayer.UnitySendMessage;

public class NativeSpeechRecognizer {
    public static void staticFunction(final String callbackTarget, final String callbackMethod){
        Log.d("デバッグ1：", callbackTarget ) ;
        Log.e("[Unity Test]","Static Method has been called!");
        UnitySendMessage(callbackTarget, callbackMethod,"はい！");
    }

    public void nonStaticFunction(final String callbackTarget, final String callbackMethod){
        Log.e("[Unity Test]","Non static Method has been called!");
        UnitySendMessage(callbackTarget, callbackMethod,"いいえ！");
    }

    public static void StartRecognizer(Context context, final String callbackTarget, final String callbackMethod)
    {
        Log.e("デバッグ1： ", callbackTarget ) ;
        Log.e("デバッグ2： ", callbackMethod ) ;

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        Log.e("デバッグ3： ", callbackTarget ) ;

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        Log.e("デバッグ4： ", callbackMethod ) ;
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());
        Log.e("デバッグ5： ", callbackMethod ) ;

        SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(context);
        Log.e("デバッグ6： ", callbackMethod ) ;
        recognizer.setRecognitionListener(new RecognitionListener()
        {
            @Override
            public void onReadyForSpeech(Bundle params)
            {
                Log.e("デバッグ8： ", callbackMethod ) ;
                // On Ready for speech.
                UnitySendMessage(callbackTarget, callbackMethod, "onReadyForSpeech");
            }

            @Override
            public void onBeginningOfSpeech()
            {
                Log.e("デバッグ9： ", callbackMethod ) ;
                // On begining of speech.
                UnitySendMessage(callbackTarget, callbackMethod, "OnBeginningOfSpheech");
            }

            @Override
            public void onRmsChanged(float rmsdB)
            {
                Log.e("デバッグ10： ", callbackMethod ) ;
                // On Rms changed.
                UnitySendMessage(callbackTarget, callbackMethod, "onRmsChanged");
            }

            @Override
            public void onBufferReceived(byte[] buffer)
            {
                Log.e("デバッグ11： ", callbackMethod ) ;
                // On buffer received.
                UnitySendMessage(callbackTarget, callbackMethod, "onBufferReceived");
            }

            @Override
            public void onEndOfSpeech()
            {
                Log.e("デバッグ12： ", callbackMethod ) ;
                // On end of speech.
                UnitySendMessage(callbackTarget, callbackMethod, "onEndOfSpeech");
            }

            @Override
            public void onError(int error)
            {
                Log.e("デバッグ13： ", callbackMethod ) ;
                // On error.
                UnitySendMessage(callbackTarget, callbackMethod, "onError");
            }

            @Override
            public void onResults(Bundle results)
            {
                Log.e("デバッグ14： ", callbackMethod ) ;
                ArrayList<String> list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String str = "";
                for (String s : list)
                {
                    if (str.length() > 0)
                    {
                        str += "\n";
                    }
                    str += s;
                }

                UnitySendMessage(callbackTarget, callbackMethod, "onResults\n" + str);
            }

            @Override
            public void onPartialResults(Bundle partialResults)
            {
                Log.e("デバッグ15： ", callbackMethod ) ;
                // On partial results.
                UnitySendMessage(callbackTarget, callbackMethod, "onPartialResults");
            }

            @Override
            public void onEvent(int eventType, Bundle params)
            {
                Log.e("デバッグ16： ", callbackMethod ) ;
                // On event.
                UnitySendMessage(callbackTarget, callbackMethod, "onEvent");
            }
        });

        recognizer.startListening(intent);
    }
}
