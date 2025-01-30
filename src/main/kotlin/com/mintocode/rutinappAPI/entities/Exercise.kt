package com.mintocode.rutinappAPI.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity(name = "Exercises")
class ExerciseEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val exerciseId: Long = 0,
    var exerciseName: String = "",
    var exerciseDescription: String = "",
    var targetedBodyPart: String = "",
    var userId: Long = 0
)