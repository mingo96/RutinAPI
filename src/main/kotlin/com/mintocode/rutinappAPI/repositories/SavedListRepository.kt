package com.mintocode.rutinappAPI.repositories

import com.mintocode.rutinappAPI.entities.ExerciseEntity
import com.mintocode.rutinappAPI.entities.SavedExercisesList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface SavedListRepository : JpaRepository<SavedExercisesList, Long> {


    @Transactional
    @Modifying
    @Query("update SavedExercisesList s set s.exercises = ?1 where s.userId = ?2")
    fun updateExerciseList(exercises: List<ExerciseEntity>, userId: Long): Int


}