package com.mintocode.rutinappAPI.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity(name = "Plannings")
data class PlanningEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,
    val date: Long,
    @ManyToOne(optional = true)
    var routineId: PlanningEntity?,
    var bodyPart: String?
)