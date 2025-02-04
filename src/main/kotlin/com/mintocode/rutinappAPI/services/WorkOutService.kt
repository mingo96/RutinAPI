package com.mintocode.rutinappAPI.services

import com.mintocode.rutinappAPI.controllers.WorkoutModel
import com.mintocode.rutinappAPI.entities.WorkOutEntity
import com.mintocode.rutinappAPI.repositories.ExerciseRepository
import com.mintocode.rutinappAPI.repositories.RoutineRepository
import com.mintocode.rutinappAPI.repositories.WorkoutRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class WorkOutService (@Autowired private val workoutRepository : WorkoutRepository, @Autowired private val routineRepository: RoutineRepository, @Autowired private val exerciseRepository: ExerciseRepository){

    /**el modelo de entrada no trae mucha info necesaria, así que necesitamos cargar la rutina original y crear
     * las entidades de set obteniendo los ejercicios a priori porque estos ya deberían de estar guardados en la DB*/
    fun addWorkOut(workoutModel: WorkoutModel, userId: Long) : WorkOutEntity?{
        return try {

            var workOutEntity = workoutModel.toEntity(userId)

            val routine = if(workoutModel.baseRoutine != null) routineRepository.findById(workoutModel.baseRoutine!!.realId.toLong()) else null

            workOutEntity.routineEntity = routine?.get()

            val exercises = workoutModel.exercisesAndSets.map {
                exerciseRepository.findById(it.first.realId).get()
            }

            val sets = workoutModel.exercisesAndSets.map { pair->
                pair.second.map {
                    it.toEntity(userId).copy(exerciseDone = exercises.find { it.exerciseId == pair.first.realId })
                }
            }.flatten()

            workOutEntity.sets = sets

            workOutEntity = workoutRepository.save(workOutEntity)

            workOutEntity
        }catch (e : Exception){
            null
        }
    }

    fun getByUserId(userId: Long): List<WorkOutEntity>{
        return try {
            workoutRepository.findByUserId(userId)
        }catch (e:Exception){
            emptyList()
        }
    }

    fun fetchAllWorkout():List<WorkOutEntity>?{
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

    fun updateExercise(workoutModel: WorkoutModel, userId: Long): WorkOutEntity? {
        return try {
            val userIsAbleToEdit = workoutRepository.existsByWorkOutIdAndUserIdAllIgnoreCase(workoutModel.id.toLong(), userId)

            if(!userIsAbleToEdit) return null

            addWorkOut(workoutModel, userId)
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