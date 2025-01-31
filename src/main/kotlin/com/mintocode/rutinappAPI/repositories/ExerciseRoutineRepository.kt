package com.mintocode.rutinappAPI.repositories

import com.mintocode.rutinappAPI.entities.ExerciseEntity
import com.mintocode.rutinappAPI.entities.ExerciseRoutine
import org.springframework.data.jpa.repository.JpaRepository

interface ExerciseRoutineRepository : JpaRepository<ExerciseRoutine, Long> {

}