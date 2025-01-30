package com.mintocode.rutinappAPI.services

import com.mintocode.rutinappAPI.entities.PlanningEntity
import com.mintocode.rutinappAPI.repositories.PlanningRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class PlanningService (@Autowired private val planningRepository : PlanningRepository){

    fun addPlanning(planningEntity: PlanningEntity) : PlanningEntity?{
        return try {
            planningRepository.save(planningEntity)
        }catch (e : Exception){
            null
        }
    }

    fun fetchAllPlannings():List<PlanningEntity>?{
        return try {
            planningRepository.findAll()
        }catch (e:Exception){
            null
        }
    }

    fun fetchPlanningById(id:Long) : PlanningEntity?{
        return try {
            planningRepository.findById(id).get()
        }catch(e:Exception){
            null
        }
    }

    fun updatePlanning(planningEntity: PlanningEntity): PlanningEntity? {
        return try {
            planningRepository.save(planningEntity)
        }catch (e:Exception){
            null
        }
    }

    fun deletePlanning(planningEntity: PlanningEntity): Boolean {
        try {
            planningRepository.delete(planningEntity)
            return true
        }catch (e:Exception){
            return false
        }
    }

}