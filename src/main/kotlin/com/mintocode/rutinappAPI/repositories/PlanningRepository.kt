package com.mintocode.rutinappAPI.repositories

import com.mintocode.rutinappAPI.entities.PlanningEntity
import com.mintocode.rutinappAPI.entities.RoutineEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlanningRepository : JpaRepository<PlanningEntity, Long> {
}