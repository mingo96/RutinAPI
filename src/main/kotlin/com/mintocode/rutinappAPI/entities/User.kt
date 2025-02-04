package com.mintocode.rutinappAPI.entities

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy

@Entity(name = "users") // This tells Hibernate to make a table out of this class

data class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0L,
    var name: String? = null,
    var email: String? = null,
    var authId: String,

) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as UserEntity

        return id == other.id
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(  id = $id )"
    }
}