package com.mintocode.rutinappAPI.repositories

import com.mintocode.rutinappAPI.entities.RoutineEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoutineRepository : JpaRepository<RoutineEntity, Long> {
    fun findByUserId(userId: Long): List<RoutineEntity>?
}