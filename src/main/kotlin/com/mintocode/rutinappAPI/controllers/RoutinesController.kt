package com.mintocode.rutinappAPI.controllers

import com.mintocode.rutinappAPI.entities.RoutineEntity
import com.mintocode.rutinappAPI.services.RoutineService
import com.mintocode.rutinappAPI.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/routines")
class RoutinesController(
    @Autowired private val routineService: RoutineService, @Autowired private val userService: UserService
) {

    @PostMapping("/newroutine")
    fun persistRoutine(
        @RequestBody routineEntity: RoutineEntity, @RequestHeader("Authorization") token: String
    ): ResponseEntity<RoutineEntity?> {

        val isAuthorized = userService.tokenIsAuthorized(token)

        if (!isAuthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() // Return 401 Unauthorized
        }
        val userId = userService.idOfToken(token)

        routineEntity.userId = userId

        return ResponseEntity.ok(routineService.addRoutine(routineEntity))

    }

    @GetMapping("/myroutines")
    fun getRoutines(@RequestHeader("Authorization") token: String): ResponseEntity<List<RoutineEntity>?> {
        val isAuthorized = userService.tokenIsAuthorized(token)

        if (!isAuthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() // Return 401 Unauthorized
        }
        val userId = userService.idOfToken(token)

        return ResponseEntity.ok(routineService.fetchByUserId(userId))

    }

    @PutMapping("/editroutine")
    fun editRoutine(
        @RequestBody routineEntity: RoutineEntity, @RequestHeader("Authorization") token: String
    ): ResponseEntity<RoutineEntity?> {

        val isAuthorized = userService.tokenIsAuthorized(token)

        if (!isAuthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() // Return 401 Unauthorized
        }

        val userId = userService.idOfToken(token)

        routineEntity.userId = userId

        return ResponseEntity.ok(routineService.addRoutine(routineEntity))

    }

}