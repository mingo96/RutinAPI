package com.mintocode.rutinappAPI.repositories

import com.mintocode.rutinappAPI.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {

    fun existsByAuthIdLike(authId: String): Boolean

    fun findByAuthIdLike(authId: String): UserEntity

}