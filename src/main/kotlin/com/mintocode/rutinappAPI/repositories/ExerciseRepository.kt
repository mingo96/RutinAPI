package com.mintocode.rutinappAPI.repositories

import com.mintocode.rutinappAPI.entities.ExerciseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ExerciseRepository : JpaRepository<ExerciseEntity, Long> {
    fun findByUserId(userId: Long): List<ExerciseEntity>?

    fun existsByUserId(userId: Long): Boolean

    fun existsByUserIdAndExerciseId(userId: Long, exerciseId: Long): Boolean

    fun findByExerciseIdAndUserId(exerciseId: Long, userId: Long): ExerciseEntity?

    fun findTop10ByTargetedBodyPartContainingIgnoreCase(targetedBodyPart: String): List<ExerciseEntity>

}