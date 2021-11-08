package com.github.davidyzliu.pytapycharmplugin.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class PytaPluginUtils private constructor () {

    companion object {
        fun parsePytaOutputString(messageString: String): List<PytaIssue> {
            val gson = Gson()
            val issueListType: Type = object : TypeToken<List<PytaIssue>>() {}.type
            return gson.fromJson(messageString, issueListType)
        }
    }


}
