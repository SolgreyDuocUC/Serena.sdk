package com.duocuc.serena.repository

import com.duocuc.serena.data.simpleData.emotionsList
import com.duocuc.serena.model.Emotion

class EmotionRepository {

    fun getAllEmotions(): List<Emotion> {
        return emotionsList
    }

    fun getEmotionById(id: Int): Emotion? {
        return emotionsList.find { it.id == id }
    }
}
