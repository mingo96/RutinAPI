package com.mintocode.rutinappAPI.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity(name = "SavedExercisesList")
data class SavedExercisesList(
    @Id
    val userId: Long,
    @OneToMany(targetEntity = ExerciseEntity::class)
    val exercises : List<ExerciseEntity>? = null
)