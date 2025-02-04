package com.mintocode.rutinappAPI.entities

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy

@Entity(name = "Plannings")
data class PlanningEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,
    val date: Long,
    @ManyToOne(optional = true, cascade = [CascadeType.MERGE, CascadeType.REMOVE])
    var routineId: PlanningEntity?,
    @ManyToOne(cascade = [CascadeType.MERGE, CascadeType.REMOVE])
    var userEntity: UserEntity,
    var bodyPart: String?
) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as PlanningEntity

        return id == other.id
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(  id = $id )"
    }
}