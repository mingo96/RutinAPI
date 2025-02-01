package com.mintocode.rutinappAPI.entities

import com.mintocode.rutinappAPI.controllers.ExerciseModel
import com.mintocode.rutinappAPI.controllers.RoutineModel
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import org.hibernate.proxy.HibernateProxy


@Entity(name = "Routines")
data class RoutineEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val routineId: Long,
    var name: String,
    var targetedBodyPart: String,
    @OneToMany(mappedBy = "routineEntity", cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE], targetEntity = ExerciseRoutine::class)
    var exerciseRelations : List<ExerciseRoutine>?,
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
        other as RoutineEntity

        return routineId == other.routineId
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(  routineId = $routineId )"
    }

    fun toModel(requestUserId: Long): RoutineModel {
        val exercises = exerciseRelations?.map { it.exerciseEntity!!.toModel(requestUserId, it) }?.toMutableList()
        return RoutineModel(
            id = 0,
            name = name,
            targetedBodyPart = targetedBodyPart,
            exercises = exercises?: mutableListOf(),
            realId = routineId.toInt(),
            isFromThisUser = requestUserId == userId
        )
    }
}