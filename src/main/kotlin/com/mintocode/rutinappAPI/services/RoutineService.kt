package com.mintocode.rutinappAPI.services

import com.mintocode.rutinappAPI.entities.RoutineEntity
import com.mintocode.rutinappAPI.repositories.RoutineRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class RoutineService (@Autowired private val routineRepository :RoutineRepository){

    fun addRoutine(routineEntity: RoutineEntity) : RoutineEntity?{
        return try {
            routineRepository.save(routineEntity)
        }catch (e : Exception){
            null
        }
    }

    fun fetchAllRoutines():List<RoutineEntity>?{
        return try {
            routineRepository.findAll()
        }catch (e:Exception){
            null
        }
    }

    fun fetchRoutineById(id:Long) : RoutineEntity?{
        return try {
            routineRepository.findById(id).get()
        }catch(e:Exception){
            null
        }
    }

    fun updateRoutine(routineEntity: RoutineEntity): RoutineEntity? {
        return try {
            routineRepository.save(routineEntity)
        }catch (e:Exception){
            null
        }
    }

    fun deleteRoutine(exerciseEntity: RoutineEntity): Boolean {
        try {
            routineRepository.delete(exerciseEntity)
            return true
        }catch (e:Exception){
            return false
        }
    }

}