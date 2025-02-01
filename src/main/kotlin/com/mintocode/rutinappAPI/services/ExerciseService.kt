package com.mintocode.rutinappAPI.services

import com.mintocode.rutinappAPI.entities.ExerciseEntity
import com.mintocode.rutinappAPI.repositories.ExerciseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ExerciseService(@Autowired private val exerciseRepository: ExerciseRepository) {

    fun addExercise(exerciseEntity: ExerciseEntity): ExerciseEntity? {
        return try {
            exerciseRepository.save(exerciseEntity)
        } catch (e: Exception) {
            null
        }
    }

    fun exercisesOfUser(userId: Long): List<ExerciseEntity>? {
        return try {
            exerciseRepository.findByUserId(userId)
        } catch (e: Exception) {
            null
        }
    }

    fun fetchAllExercises(): List<ExerciseEntity>? {
        return try {
            exerciseRepository.findAll()
        } catch (e: Exception) {
            null
        }
    }

    fun fetchExerciseById(id: Long): ExerciseEntity? {
        return try {
            exerciseRepository.findById(id).get()
        } catch (e: Exception) {
            null
        }
    }

    fun updateExercise(exerciseEntity: ExerciseEntity, userId: Long): ExerciseEntity? {
        return try {
            if (exerciseRepository.existsByUserIdAndExerciseId(
                    userId,
                    exerciseEntity.exerciseId
                )
            ) {
                exerciseRepository.save(exerciseEntity)
            }
            else null
        } catch (e: Exception) {
            null
        }
    }

    fun deleteExercise(exerciseEntity: ExerciseEntity): Boolean {
        try {
            exerciseRepository.delete(exerciseEntity)
            return true
        } catch (e: Exception) {
            return false
        }
    }

}