package com.mintocode.rutinappAPI.services

import com.mintocode.rutinappAPI.entities.UserEntity
import com.mintocode.rutinappAPI.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired private val userRepository: UserRepository) {

    fun addUser(userEntity: UserEntity) : UserEntity?{
        return userRepository.save(userEntity)
    }

    fun tokenIsAuthorized(token:String):Boolean{
        return userRepository.existsByAuthIdLike(token)
    }

    fun idOfToken(token : String):Long{
        return userRepository.findByAuthIdLike(token).id
    }

}