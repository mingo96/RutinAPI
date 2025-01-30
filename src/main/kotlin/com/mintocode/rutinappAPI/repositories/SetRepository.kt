package com.mintocode.rutinappAPI.repositories

import com.mintocode.rutinappAPI.entities.SetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SetRepository : JpaRepository<SetEntity, Long> {
}