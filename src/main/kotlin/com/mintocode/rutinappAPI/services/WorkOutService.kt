package com.mintocode.rutinappAPI.services

import com.mintocode.rutinappAPI.entities.WorkOutEntity
import com.mintocode.rutinappAPI.repositories.WorkoutRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class WorkOutService (@Autowired private val workoutRepository : WorkoutRepository){

    fun addExercise(workOutEntity: WorkOutEntity) : WorkOutEntity?{
        return try {
            workoutRepository.save(workOutEntity)
        }catch (e : Exception){
            null
        }
    }

    fun fetchAllExercises():List<WorkOutEntity>?{
        return try {
            workoutRepository.findAll()
        }catch (e:Exception){
            null
        }
    }

    fun fetchExerciseById(id:Long) : WorkOutEntity?{
        return try {
            workoutRepository.findById(id).get()
        }catch(e:Exception){
            null
        }
    }

    fun updateExercise(workOutEntity: WorkOutEntity): WorkOutEntity? {
        return try {
            workoutRepository.save(workOutEntity)
        }catch (e:Exception){
            null
        }
    }

    fun deleteExercise(workOutEntity: WorkOutEntity): Boolean {
        try {
            workoutRepository.delete(workOutEntity)
            return true
        }catch (e:Exception){
            return false
        }
    }

}