package com.arya.server.speechtotext

import java.io.File

interface ISpeechToText {

    fun parse(wavFile: File): String

}