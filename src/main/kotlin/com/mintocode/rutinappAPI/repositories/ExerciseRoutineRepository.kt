package com.mintocode.rutinappAPI.repositories

import com.mintocode.rutinappAPI.entities.ExerciseEntity
import com.mintocode.rutinappAPI.entities.ExerciseRoutine
import com.mintocode.rutinappAPI.entities.ExerciseRoutineId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface ExerciseRoutineRepository : JpaRepository<ExerciseRoutine, Long> {


    @Transactional
    @Modifying
    @Query("delete from ExerciseRoutine e where e.id = ?1")
    fun deleteById(id: ExerciseRoutineId)

}