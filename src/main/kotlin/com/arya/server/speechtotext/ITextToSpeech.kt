package com.arya.server.speechtotext

import java.io.File

interface ITextToSpeech {

    fun parse(wavFile: File): String

}