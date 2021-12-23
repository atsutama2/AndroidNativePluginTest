package jp.co.test.nativespeechrecognizer;

import android.content.Context;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

import static com.unity3d.player.UnityPlayer.UnitySendMessage;

public class NativeSpeechRecognizer {
    private static final String TAG = "LOGTAG: ";

    public static void StartRecognizer(final Context context, final String callbackTarget, final String callbackResults, final String callbackStateResults)
    {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());

        // 多言語設定
        String lang = Locale.getDefault().toLanguageTag();
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, lang);
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, lang);
        intent.putExtra(RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT, lang);

        Log.d(TAG, lang);

        SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(context);
        recognizer.setRecognitionListener(new RecognitionListener()
        {
            @Override
            public void onReadyForSpeech(Bundle bundle)
            {
                // 音声認識の準備ができた時呼び出される
                Log.d(TAG, "onReadyForSpeech:" + bundle.toString());
                UnitySendMessage(callbackTarget, callbackStateResults, "onReadyForSpeech");
            }

            @Override
            public void onBeginningOfSpeech()
            {
                // マイクに向かってしゃべり始めると呼び出される
                Log.d(TAG, "onBeginningOfSpeech:");
                UnitySendMessage(callbackTarget, callbackStateResults, "onBeginningOfSpeech");
            }

            @Override
            public void onRmsChanged(float rmsdB)
            {
                // On Rms changed.
                Log.d(TAG, "ボリューム" + rmsdB);
                UnitySendMessage(callbackTarget, callbackResults, "onRmsChanged");
            }

            @Override
            public void onBufferReceived(byte[] buffer)
            {
                // On buffer received.
                Log.d(TAG, "音声データ");
                UnitySendMessage(callbackTarget, callbackStateResults, "onBufferReceived");
            }

            @Override
            public void onEndOfSpeech()
            {
                // On end of speech.
                Log.d(TAG, "onEndOfSpeech");
                UnitySendMessage(callbackTarget, callbackStateResults, "onEndOfSpeech");
            }

            @Override
            public void onError(int error)
            {
                String errorMessage;

                switch (error) {
                    case SpeechRecognizer.ERROR_AUDIO:
                        // 音声データ保存失敗
                        Log.e(TAG, "ERROR_AUDIO");
                        errorMessage = "Audio recording error";
                        break;
                    case SpeechRecognizer.ERROR_CLIENT:
                        // Android端末内のエラー(その他)
                        Log.e(TAG, "ERROR_CLIENT");
                        errorMessage = "Client side error";
                        break;
                    case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                        // 権限無し
                        Log.e(TAG, "ERROR_INSUFFICIENT_PERMISSIONS");
                        errorMessage = "Insufficient permissions";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK:
                        // ネットワークエラー(その他)
                        Log.e(TAG, "ERROR_NETWORK");
                        errorMessage = "Network related error";
                        break;
                    case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                        // ネットワークタイムアウトエラー
                        Log.e(TAG, "ERROR_NETWORK_TIMEOUT");
                        errorMessage = "Network operation timeout";
                        break;
                    case SpeechRecognizer.ERROR_NO_MATCH:
                        // 音声認識結果無し
                        Log.e(TAG, "ERROR_NO_MATCH");
                        errorMessage = "No matching phrase";
                        break;
                    case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                        // RecognitionServiceへ要求出せず
                        Log.e(TAG, "ERROR_RECOGNIZER_BUSY");
                        errorMessage = "RecognitionServiceBusy";
                        break;
                    case SpeechRecognizer.ERROR_SERVER:
                        // Server側からエラー通知
                        Log.e(TAG, "ERROR_SERVER");
                        errorMessage = "Server sends error status";
                        break;
                    case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                        // 音声入力無し
                        Log.e(TAG, "ERROR_SPEECH_TIMEOUT");
                        errorMessage = "No input detected";
                        break;
                    default:
                        errorMessage = "ASR error";
                        break;
                }
                // On error.
                UnitySendMessage(callbackTarget, callbackStateResults, errorMessage);
            }

            @Override
            public void onResults(Bundle results)
            {
                Log.d(TAG, "onResults");

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
                Log.d(TAG, str ) ;

                UnitySendMessage(callbackTarget, callbackResults, "onResults\n" + str);
            }

            @Override
            public void onPartialResults(Bundle partialResults)
            {
                Log.d(TAG, "onPartialResults" ) ;
                // On partial results.
                UnitySendMessage(callbackTarget, callbackStateResults, "onPartialResults");
            }

            @Override
            public void onEvent(int eventType, Bundle params)
            {
                Log.d(TAG, "onEvent" ) ;
                // On event.
                UnitySendMessage(callbackTarget, callbackStateResults, "onEvent");
            }
        });

        recognizer.startListening(intent);
    }
}
