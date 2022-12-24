package com.stathis.unipiaudiostories.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import com.stathis.unipiaudiostories.util.DOT
import com.stathis.unipiaudiostories.util.TTS_GR
import timber.log.Timber
import java.util.*

class TextToSpeechUtil : MyTTsFeatures {

    private lateinit var tts: TextToSpeech

    /**
     * Initializes the TTS engine and sets the Greek language.
     */

    override fun initialize(context: Context) {
        tts = TextToSpeech(context) { initResult ->
            if (initResult == TextToSpeech.SUCCESS) {
                tts.language = Locale.forLanguageTag(TTS_GR)
            } else {
                Timber.d("Error init TTS")
            }
        }
    }

    /**
     * This method is responsible for the TextToSpeech speak feature.
     *
     * Since the stories are long strings and the TextToSpeech can't read  it
     * all at once, a workaround is to split the story and pass the substrings to TTS engine.
     */

    override fun speak(message: String) {
        message.split(DOT).forEach { substring ->
            tts.speak(substring, TextToSpeech.QUEUE_ADD, null, null)
        }
    }

    /**
     * Stops the TTS engine
     */

    override fun stop() {
        tts.stop()
    }

    /**
     * Terminates the TTS session.
     */

    override fun shutdown() {
        tts.stop()
        tts.shutdown()
    }
}