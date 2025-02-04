package com.mintocode.rutinappAPI.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import org.hibernate.proxy.HibernateProxy
import java.io.Serializable
import java.util.*

@Entity
data class ExerciseRoutine(

    @EmbeddedId
    @JsonIgnore
    val id: ExerciseRoutineId,
    @ManyToOne(optional = false, cascade = [CascadeType.ALL])
    @MapsId("routineId")
    @JoinColumn(name = "routine_id")
    val routine: RoutineEntity,
    @ManyToOne(optional = false)
    @MapsId("exerciseId")
    @JoinColumn(name = "exercise_id")
    val exercise: ExerciseEntity,
    var observations : String,
    var setsAndReps : String
) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as ExerciseRoutine

        return id == other.id
    }

    final override fun hashCode(): Int = Objects.hash(id);

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(EmbeddedId = $id )"
    }
}

@Embeddable
data class ExerciseRoutineId(
    val exerciseId: Long,
    val routineId: Long
) : Serializable{

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as ExerciseRoutineId

        return exerciseId == other.exerciseId && routineId == other.routineId
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

}