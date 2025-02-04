package com.mintocode.rutinappAPI.entities

import com.mintocode.rutinappAPI.controllers.SetModel
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.hibernate.proxy.HibernateProxy
import java.util.*

@Entity(name = "Sets")
data class SetEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val setId: Long = 0,
    @ManyToOne(optional = false, cascade = [CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST])
    val exerciseDone: ExerciseEntity?,
    @ManyToOne(optional = false, cascade = [CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST])
    val workoutDone: WorkOutEntity?,
    val weight: Double,
    val reps: Int,
    val date: String,
    var observations: String,
    var userId: Long = 0
){

    fun toModel(): SetModel{
        return SetModel(
            id = 0,
            weight = weight,
            exercise = exerciseDone?.toModel(exerciseDone!!.userId),
            workoutDone = null,
            reps = reps,
            date = Date(date),
            observations = observations
        )
    }

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as SetEntity

        return setId == other.setId
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(  setId = $setId )"
    }

}