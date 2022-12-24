package com.stathis.unipiaudiostories.tts

import android.content.Context

interface MyTTsFeatures {

    /**
     * The basic functionalities for the project's TTS feature are the following:
     */

    fun initialize(context: Context)

    fun speak(message: String)

    fun stop()

    fun shutdown()
}