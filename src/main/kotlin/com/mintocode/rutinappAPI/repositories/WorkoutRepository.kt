package com.mintocode.rutinappAPI.repositories

import com.mintocode.rutinappAPI.entities.WorkOutEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkoutRepository : JpaRepository<WorkOutEntity, Long> {
    fun findByUserId(userId: Long): List<WorkOutEntity>


    fun existsByWorkOutIdAndUserIdAllIgnoreCase(workOutId: Long, userId: Long): Boolean
}