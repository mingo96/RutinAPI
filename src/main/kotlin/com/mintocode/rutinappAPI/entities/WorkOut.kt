package com.mintocode.rutinappAPI.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany


@Entity(name = "WorkOuts")
data class WorkOutEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val workOutId: Long,
    val date: Long,
    var title: String,
    var isFinished: Boolean,
    @ManyToOne(targetEntity = RoutineEntity::class, optional = true)
    var routineEntity: RoutineEntity? = null,
    @OneToMany(targetEntity = ExerciseEntity::class)
    var exercises : List<ExerciseEntity>?=null,
    @OneToMany(targetEntity = SetEntity::class)
    var sets : List<SetEntity>? =null
)