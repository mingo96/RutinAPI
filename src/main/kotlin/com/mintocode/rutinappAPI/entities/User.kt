package com.mintocode.rutinappAPI.entities

import jakarta.persistence.*

@Entity(name = "users") // This tells Hibernate to make a table out of this class

data class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0L,
    var name: String? = null,
    var email: String? = null,
    var authId: String
)