package com.mintocode.rutinappAPI.services

import com.mintocode.rutinappAPI.controllers.ExerciseModel
import com.mintocode.rutinappAPI.entities.ExerciseEntity
import com.mintocode.rutinappAPI.repositories.ExerciseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ExerciseService(@Autowired private val exerciseRepository: ExerciseRepository) {

    /**since [ExerciseModel] has just IDs we save the exercise first, then add the exercises*/
    fun addExercise(enteredExercise: ExerciseModel, userId: Long): ExerciseEntity? {
        return try {
            val newList = enteredExercise.equivalentExercises.map {
                exerciseRepository.findById(it.toLong()).get()
            }
            val entity = enteredExercise.toEntity(userId)

            val result = exerciseRepository.save(entity)

            result.relatedExercises = newList

            exerciseRepository.save(result)

            result
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

    /**we cant really update if the item isnt out of the db so we get it and update everything, including the relations given
     * the exercises ids*/
    fun updateExercise(exerciseModel: ExerciseModel, userId: Long): ExerciseEntity? {
        return try {
            val actualValue = exerciseRepository.findByExerciseIdAndUserId(
                exerciseModel.realId,userId
            )
            if (actualValue == null) return null
            actualValue.exerciseName = exerciseModel.name
            actualValue.exerciseDescription = exerciseModel.description
            actualValue.targetedBodyPart = exerciseModel.targetedBodyPart
            actualValue.relatedExercises = exerciseModel.equivalentExercises.map {
                exerciseRepository.findById(it.toLong()).get()
            }
            exerciseRepository.save(actualValue)
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

    fun fetchByBodyPart(bodyPart: String): List<ExerciseEntity>? {

        return try {
            exerciseRepository.findTop10ByTargetedBodyPartContainingIgnoreCase(bodyPart)
        }catch (e:Exception){
            null
        }

    }

}