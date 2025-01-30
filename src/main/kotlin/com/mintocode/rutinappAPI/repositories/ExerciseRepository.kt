package com.mintocode.rutinappAPI.repositories

import com.mintocode.rutinappAPI.entities.ExerciseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExerciseRepository : JpaRepository<ExerciseEntity, Long> {
    abstract fun findByUserId(userId: Long): List<ExerciseEntity>?


    fun existsByUserId(userId: Long): Boolean


    fun existsByUserIdAndExerciseId(userId: Long, exerciseId: Long): Boolean
}