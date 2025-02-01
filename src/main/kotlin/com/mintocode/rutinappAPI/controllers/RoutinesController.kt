package com.mintocode.rutinappAPI.controllers

import com.mintocode.rutinappAPI.entities.ExerciseRoutine
import com.mintocode.rutinappAPI.entities.ExerciseRoutineId
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
        @RequestBody routineEntity: RoutineModel, @RequestHeader("Authorization") token: String
    ): ResponseEntity<RoutineModel?> {

        val isAuthorized = userService.tokenIsAuthorized(token)

        if (!isAuthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() // Return 401 Unauthorized
        }
        val userId = userService.idOfToken(token)

        val response = routineService.addRoutine(routineEntity.toEntity(userId)) ?: return ResponseEntity.status(
            HttpStatus.CONFLICT
        ).build()

        return ResponseEntity.ok(response.toModel(userId))

    }

    @GetMapping("/myroutines")
    fun getRoutines(@RequestHeader("Authorization") token: String): ResponseEntity<List<RoutineModel>?> {
        val isAuthorized = userService.tokenIsAuthorized(token)

        if (!isAuthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() // Return 401 Unauthorized
        }
        val userId = userService.idOfToken(token)

        val response = routineService.fetchByUserId(userId) ?: return ResponseEntity.status(HttpStatus.CONFLICT).build()

        return ResponseEntity.ok(response.map { it.toModel(userId) })

    }

    @PutMapping("/editroutine")
    fun editRoutine(
        @RequestBody routineEntity: RoutineModel, @RequestHeader("Authorization") token: String
    ): ResponseEntity<RoutineModel?> {

        val isAuthorized = userService.tokenIsAuthorized(token)

        if (!isAuthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() // Return 401 Unauthorized
        }

        val userId = userService.idOfToken(token)

        val response = routineService.addRoutine(routineEntity.toEntity(userId)) ?: return ResponseEntity.status(
            HttpStatus.CONFLICT
        ).build()

        return ResponseEntity.ok(response.toModel(userId))

    }

}

data class RoutineModel(
    var id: Int = 0,
    var name: String,
    var targetedBodyPart: String,
    var exercises: MutableList<ExerciseModel> = mutableListOf(),
    var realId: Int = 0,
    var isFromThisUser: Boolean = true
) {
    fun toEntity(userId: Long = 0L): RoutineEntity {
        return RoutineEntity(
            routineId = id.toLong(),
            name = name,
            targetedBodyPart = targetedBodyPart,
            exerciseRelations = exercises.map {
                ExerciseRoutine(
                    id = ExerciseRoutineId(it.realId, id.toLong()),
                    observations = it.observations,
                    setsAndReps = it.setsAndReps
                )
            },
            userId = userId
        )
    }
}