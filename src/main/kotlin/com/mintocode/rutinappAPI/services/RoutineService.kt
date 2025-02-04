package com.mintocode.rutinappAPI.services

import com.mintocode.rutinappAPI.controllers.RoutineModel
import com.mintocode.rutinappAPI.entities.ExerciseRoutine
import com.mintocode.rutinappAPI.entities.ExerciseRoutineId
import com.mintocode.rutinappAPI.entities.RoutineEntity
import com.mintocode.rutinappAPI.repositories.ExerciseRepository
import com.mintocode.rutinappAPI.repositories.ExerciseRoutineRepository
import com.mintocode.rutinappAPI.repositories.RoutineRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class RoutineService(
    @Autowired private val routineRepository: RoutineRepository,
    @Autowired private val exerciseRoutineRepository: ExerciseRoutineRepository,
    private val exerciseRepository: ExerciseRepository
) {

    fun addRoutine(enteredRoutine: RoutineModel, userId: Long): RoutineEntity? {
        try {
            var routineEntity = routineRepository.save(enteredRoutine.toEntity(userId))

            enteredRoutine.exercises.forEach { exerciseModel ->
                val exerciseEntity = exerciseRepository.findById(exerciseModel.realId).orElseGet {
                    null
                }

                if (exerciseEntity == null) return@forEach

                val exerciseRoutine = ExerciseRoutine(
                    id = ExerciseRoutineId(exerciseEntity.exerciseId, routineEntity.routineId),
                    routine = routineEntity,   // Set routine directly
                    exercise = exerciseEntity, // Set exercise directly
                    observations = exerciseModel.observations,
                    setsAndReps = exerciseModel.setsAndReps
                )

                routineEntity.exerciseRelations =
                    routineEntity.exerciseRelations.orEmpty() + exerciseRoutineRepository.save(exerciseRoutine) // Use the mutable list add method
            }

            routineEntity = routineRepository.save(routineEntity)

            return routineEntity // Return the saved routineEntity

        } catch (e: Exception) {
            println("Error in addRoutine: ${e.message}")
            e.printStackTrace()
            return null
        }
    }


    fun fetchAllRoutines(): List<RoutineEntity>? {
        return try {
            routineRepository.findAll()
        } catch (e: Exception) {
            null
        }
    }

    fun fetchRoutineById(id: Long): RoutineEntity? {
        return try {
            routineRepository.findById(id).get()
        } catch (e: Exception) {
            null
        }
    }

    fun deleteRoutine(exerciseEntity: RoutineEntity): Boolean {
        try {
            routineRepository.delete(exerciseEntity)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun fetchByUserId(userId: Long): List<RoutineEntity>? {

        return try {
            routineRepository.findByUserId(userId)
        } catch (e: Exception) {
            null
        }
    }

    fun updateRoutine(routineEntity: RoutineModel, userId: Long): RoutineEntity? {

        return try {
            //if (routineEntity.realId == 0) return addRoutine(routineEntity, userId)

            val foundItem = routineRepository.findByUserIdAndRoutineId(routineEntity.realId.toLong(), userId)

            if (foundItem == null) {
                return addRoutine(routineEntity, userId)
            } else {
                foundItem.name = routineEntity.name
                foundItem.targetedBodyPart = routineEntity.targetedBodyPart

                val oldListOfIds = foundItem.exerciseRelations.orEmpty().map { it.id }

                val addedExercises = routineEntity.exercises.filter {
                    !oldListOfIds.contains(
                        ExerciseRoutineId(
                            it.realId, foundItem.routineId
                        )
                    )
                }

                addedExercises.forEach { exerciseModel ->
                    val exerciseEntity = exerciseRepository.findById(exerciseModel.realId).orElseGet {
                        null
                    }

                    if (exerciseEntity == null) return@forEach

                    val exerciseRoutine = ExerciseRoutine(
                        id = ExerciseRoutineId(exerciseEntity.exerciseId, foundItem.routineId),
                        routine = foundItem,   // Set routine directly
                        exercise = exerciseEntity, // Set exercise directly
                        observations = exerciseModel.observations,
                        setsAndReps = exerciseModel.setsAndReps
                    )
                    foundItem.exerciseRelations =
                        foundItem.exerciseRelations.orEmpty() + exerciseRoutineRepository.save(exerciseRoutine)
                }

                routineRepository.save(foundItem)
            }

        } catch (e: Exception) {
            println("Error in updateRoutine: ${e.message}")
            e.printStackTrace()
            null
        }

    }

}