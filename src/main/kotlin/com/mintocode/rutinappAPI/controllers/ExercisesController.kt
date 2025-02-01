package com.mintocode.rutinappAPI.controllers

import com.mintocode.rutinappAPI.entities.ExerciseEntity
import com.mintocode.rutinappAPI.services.ExerciseService
import com.mintocode.rutinappAPI.services.SavedListService
import com.mintocode.rutinappAPI.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/exercises")
class ExercisesController(
    @Autowired private val exerciseService: ExerciseService,
    @Autowired private val userService: UserService,
    @Autowired private val savedListService: SavedListService
) {

    @PostMapping("/newexercise")
    fun addExercise(
        @RequestBody newExercise: ExerciseModel, @RequestHeader("Authorization") token: String
    ): ResponseEntity<ExerciseModel?> {
        val isAuthorized = userService.tokenIsAuthorized(token)

        if (!isAuthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() // Return 401 Unauthorized
        }
        val userId = userService.idOfToken(token)

        val response = exerciseService.addExercise(newExercise.toEntity(userId))

        return ResponseEntity.ok(response?.toModel(userId))

    }

    @GetMapping("/myexercises")
    fun allExercises(@RequestHeader("Authorization") token: String): ResponseEntity<List<ExerciseModel>?> {

        val isAuthorized = userService.tokenIsAuthorized(token)

        if (!isAuthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() // Return 401 Unauthorized
        }
        val userId = userService.idOfToken(token)

        val createdExercises = exerciseService.exercisesOfUser(userId)?.map { it.toModel(userId) }

        val savedExercises = savedListService.getSavedExercises(userId)?.map { it.toModel(userId) }

        return ResponseEntity.ok((createdExercises ?: listOf()) + (savedExercises ?: emptyList()))
    }

    @PutMapping
    fun updateExercise(
        @RequestBody exerciseEntity: ExerciseModel, @RequestHeader("Authorization") token: String
    ): ResponseEntity<ExerciseModel?> {
        val isAuthorized = userService.tokenIsAuthorized(token)

        if (!isAuthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() // Return 401 Unauthorized
        }

        val userId = userService.idOfToken(token)

        val response = exerciseService.updateExercise(exerciseEntity.toEntity(), userId)

        return if (response != null) ResponseEntity.ok(response.toModel(userId)) else ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .build()

    }


}

data class ExerciseModel(
    var id: String = "0",
    var realId: Long = 0L,
    var name: String,
    var description: String,
    var targetedBodyPart: String,
    var equivalentExercises: List<ExerciseModel> = emptyList(),
    var setsAndReps: String = "",
    var observations: String = "",
    val isFromThisUser: Boolean = true
) {
    fun toEntity(userId: Long = 0L): ExerciseEntity = ExerciseEntity(
        exerciseId = id.toLongOrNull() ?: 0L,
        exerciseName = name,
        exerciseDescription = description,
        targetedBodyPart = targetedBodyPart,
        userId = userId,
        relatedExercises = equivalentExercises.map { it.toEntity() })
}