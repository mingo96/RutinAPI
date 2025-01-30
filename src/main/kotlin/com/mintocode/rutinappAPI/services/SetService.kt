package com.mintocode.rutinappAPI.services

import com.mintocode.rutinappAPI.entities.SetEntity
import com.mintocode.rutinappAPI.repositories.SetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class SetService (@Autowired private val setRepository : SetRepository){

    fun addSet(setEntity: SetEntity) : SetEntity?{
        return try {
            setRepository.save(setEntity)
        }catch (e : Exception){
            null
        }
    }

    fun fetchAllSets():List<SetEntity>?{
        return try {
            setRepository.findAll()
        }catch (e:Exception){
            null
        }
    }

    fun fetchSetById(id:Long) : SetEntity?{
        return try {
            setRepository.findById(id).get()
        }catch(e:Exception){
            null
        }
    }

    fun updateSet(setEntity: SetEntity): SetEntity? {
        return try {
            setRepository.save(setEntity)
        }catch (e:Exception){
            null
        }
    }

    fun deleteSet(setEntity: SetEntity): Boolean {
        try {
            setRepository.delete(setEntity)
            return true
        }catch (e:Exception){
            return false
        }
    }

}