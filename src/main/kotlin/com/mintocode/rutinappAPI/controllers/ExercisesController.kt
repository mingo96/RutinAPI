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
class ExercisesController(@Autowired private val exerciseService: ExerciseService, @Autowired private val userService: UserService, @Autowired private val savedListService: SavedListService) {

    @PostMapping("/newexercise")
    fun addExercise(@RequestBody newExercise: ExerciseEntity, @RequestHeader("Authorization") token:String ): ResponseEntity<ExerciseEntity?> {
        val isAuthorized = userService.tokenIsAuthorized(token)

        if (!isAuthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() // Return 401 Unauthorized
        }
        val userId = userService.idOfToken(token)

        newExercise.userId = userId

        val response = exerciseService.addExercise(newExercise)

        return ResponseEntity.ok(response)

    }

    @GetMapping("/myexercises")
    fun allExercises(@RequestHeader("Authorization") token:String):ResponseEntity<List<ExerciseEntity>?>{

        val isAuthorized = userService.tokenIsAuthorized(token)

        if (!isAuthorized){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() // Return 401 Unauthorized
        }
        val userId = userService.idOfToken(token)

        val createdExercises = exerciseService.exercisesOfUser(userId)

        val savedExercises = savedListService.getSavedExercises(userId)

        return ResponseEntity.ok((createdExercises?: listOf()) + (savedExercises?: emptyList()))
    }

    @PutMapping
    fun updateExercise(@RequestBody exerciseEntity: ExerciseEntity, @RequestHeader("Authorization") token:String): ResponseEntity<ExerciseEntity?> {
        val isAuthorized = userService.tokenIsAuthorized(token)

        if (!isAuthorized){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() // Return 401 Unauthorized
        }

        val userId = userService.idOfToken(token)

        val response = exerciseService.updateExercise(exerciseEntity, userId)

        return if(response!= null) ResponseEntity.ok(response) else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

    }


}