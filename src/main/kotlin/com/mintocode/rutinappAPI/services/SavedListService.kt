package com.mintocode.rutinappAPI.services

import com.mintocode.rutinappAPI.entities.ExerciseEntity
import com.mintocode.rutinappAPI.entities.SavedExercisesList
import com.mintocode.rutinappAPI.repositories.SavedListRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SavedListService(@Autowired private val savedListRepository: SavedListRepository) {

    fun addToSavedList(exerciseEntity: ExerciseEntity, userId: Long) {
        if (savedListRepository.findById(userId).isEmpty) {
            savedListRepository.save(SavedExercisesList(userId, listOf(exerciseEntity)))
        } else {
            val actualvalue = savedListRepository.getReferenceById(userId)
            savedListRepository.updateExerciseList((actualvalue.exercises ?: listOf()) + exerciseEntity, userId)
        }
    }

    fun addToSavedList(exercises: List<ExerciseEntity>, userId: Long) {
        if (savedListRepository.findById(userId).isEmpty) {
            savedListRepository.save(SavedExercisesList(userId, exercises))
        } else {

            val actualvalue = savedListRepository.getReferenceById(userId)
            savedListRepository.updateExerciseList((actualvalue.exercises ?: listOf()) + exercises, userId)
        }
    }

    fun getSavedExercises(userId: Long): List<ExerciseEntity>? {
        if (savedListRepository.findById(userId).isEmpty) {
            savedListRepository.save(SavedExercisesList(userId, listOf()))
            return savedListRepository.getReferenceById(userId).exercises
        } else return savedListRepository.getReferenceById(userId).exercises
    }

}