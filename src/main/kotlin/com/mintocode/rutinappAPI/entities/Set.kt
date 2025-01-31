package com.mintocode.rutinappAPI.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity(name = "Sets")
data class SetEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val setId: Long = 0,
    @ManyToOne(optional = false)
    val exerciseDone: ExerciseEntity,
    @ManyToOne(optional = false, cascade = [CascadeType.MERGE])
    val workoutDoneId: WorkOutEntity,
    val weight: Double,
    val reps: Int,
    val date: String,
    var observations: String
)