package com.mintocode.rutinappAPI.entities

import jakarta.persistence.*


@Entity(name = "WorkOuts")
data class WorkOutEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val workOutId: Long,
    val date: Long,
    var title: String,
    var isFinished: Boolean,
    @ManyToOne(targetEntity = RoutineEntity::class, optional = true, cascade = [CascadeType.MERGE, CascadeType.REFRESH])
    var routineEntity: RoutineEntity? = null,
    @ManyToOne(targetEntity = ExerciseEntity::class, cascade = [CascadeType.MERGE, CascadeType.REFRESH])
    var exercises : List<ExerciseEntity>?=null,
    @OneToMany(targetEntity = SetEntity::class, mappedBy = "workoutDoneId", cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE])
    var sets : List<SetEntity>? =null
)