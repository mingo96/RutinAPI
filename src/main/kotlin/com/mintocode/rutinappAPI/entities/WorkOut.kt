package com.mintocode.rutinappAPI.entities

import com.mintocode.rutinappAPI.controllers.WorkoutModel
import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import java.util.*


@Entity(name = "WorkOuts")
data class WorkOutEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val workOutId: Long,
    val date: Long,
    var title: String,
    var isFinished: Boolean,
    @ManyToOne(
        targetEntity = RoutineEntity::class,
        optional = true,
        cascade = [CascadeType.PERSIST]
    ) var routineEntity: RoutineEntity? = null,
    @OneToMany(
        targetEntity = SetEntity::class,
        mappedBy = "workoutDone",
        cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH]
    ) var sets: List<SetEntity>? = null,
    var userId: Long
) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as WorkOutEntity

        return workOutId == other.workOutId
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(  workOutId = $workOutId )"
    }

    /**se separan los ejercicios de los sets, se les dan sus datos adicionales, se guardan con sus sets*/
    fun toModel(requestUserId: Long): WorkoutModel {
        val exercises = sets.orEmpty().map { it.exerciseDone!!.toModel(it.exerciseDone!!.userId) }.distinctBy { it.realId }
        exercises.forEach { exercise ->
            val foundRelation = routineEntity?.exerciseRelations?.find { it.exercise.exerciseId == exercise.realId }
            exercise.setsAndReps = foundRelation?.setsAndReps ?: ""
            exercise.observations = foundRelation?.observations ?: ""
        }
        return WorkoutModel(
            id = 0, baseRoutine = routineEntity?.toModel(requestUserId), exercisesAndSets = exercises.map { exercise ->
                exercise to sets.orEmpty().filter { set -> set.exerciseDone!!.exerciseId == exercise.realId }
                    .map { it.toModel() }.toMutableList()
            }.toMutableList(), date = Date(date), title = title, isFinished = isFinished
        )
    }
}