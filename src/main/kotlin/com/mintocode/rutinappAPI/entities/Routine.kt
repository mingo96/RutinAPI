package com.mintocode.rutinappAPI.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany


@Entity(name = "Routines")
data class RoutineEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val routineId: Long,
    var name: String,
    var targetedBodyPart: String,
    @OneToMany(cascade = [CascadeType.ALL], targetEntity = ExerciseEntity::class)
    var exercises : List<ExerciseEntity>?
)