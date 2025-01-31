package com.mintocode.rutinappAPI.services

import com.mintocode.rutinappAPI.entities.RoutineEntity
import com.mintocode.rutinappAPI.repositories.ExerciseRepository
import com.mintocode.rutinappAPI.repositories.ExerciseRoutineRepository
import com.mintocode.rutinappAPI.repositories.RoutineRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class RoutineService(
    @Autowired private val routineRepository: RoutineRepository,
    @Autowired private val exerciseRoutineRepository: ExerciseRoutineRepository
) {

    fun addRoutine(routineEntity: RoutineEntity): RoutineEntity? {
        return try {
            routineEntity.exerciseRelations?.map { it.routineEntity = routineEntity;it }?.forEach {
                exerciseRoutineRepository.save(it)
            }
            if (routineEntity.exerciseRelations?.isEmpty() != true)
                routineRepository.save(routineEntity)
            routineEntity
        } catch (e: Exception) {
            null
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

}