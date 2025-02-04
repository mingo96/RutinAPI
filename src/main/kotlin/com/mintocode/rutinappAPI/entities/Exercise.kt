package com.mintocode.rutinappAPI.entities

import com.mintocode.rutinappAPI.controllers.ExerciseModel
import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy


@Entity(name = "Exercises")
data class ExerciseEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(
        name = "exercise_id",
        unique = true,
        nullable = false
    ) val exerciseId: Long = 0,
    var exerciseName: String = "",
    var exerciseDescription: String = "",
    var targetedBodyPart: String = "",
    val userId: Long = 0,
    @ManyToMany(targetEntity = ExerciseEntity::class, cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH])
    var relatedExercises: List<ExerciseEntity>? = null
) {
    fun toModel(requestUserId: Long, exerciseRoutine: ExerciseRoutine? = null): ExerciseModel {
        return ExerciseModel(
            id = "0",
            realId = exerciseId,
            name = exerciseName,
            description = exerciseDescription,
            isFromThisUser = userId == requestUserId,
            targetedBodyPart = targetedBodyPart,
            equivalentExercises = relatedExercises?.map { it.exerciseId.toInt() } ?: emptyList(),
            observations = exerciseRoutine?.observations ?: "",
            setsAndReps = exerciseRoutine?.setsAndReps ?: "")
    }

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as ExerciseEntity

        return exerciseId == other.exerciseId
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(  exerciseId = $exerciseId )"
    }
}
