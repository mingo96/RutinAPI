package com.mintocode.rutinappAPI.controllers

import com.mintocode.rutinappAPI.entities.UserEntity
import com.mintocode.rutinappAPI.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/auth")
class AuthController(@Autowired private val userService: UserService) {

    @PostMapping("/newuser")
    fun addUser(@RequestBody newUser: UserEntity) : ResponseEntity<UserEntity?> {
        val response = userService.addUser(newUser)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/isAuthorized")
    fun isAuthorized(@RequestHeader("Authorization") token:String) : ResponseEntity<Boolean>{
        val response = userService.tokenIsAuthorized(token)
        return ResponseEntity.ok(response)
    }

}