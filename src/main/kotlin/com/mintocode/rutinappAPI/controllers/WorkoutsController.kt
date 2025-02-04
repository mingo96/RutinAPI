package com.mintocode.rutinappAPI.controllers

import com.mintocode.rutinappAPI.entities.SetEntity
import com.mintocode.rutinappAPI.entities.WorkOutEntity
import com.mintocode.rutinappAPI.services.UserService
import com.mintocode.rutinappAPI.services.WorkOutService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("api/workouts")
class WorkoutsController(@Autowired private val workoutService: WorkOutService, @Autowired private val userService: UserService) {

    @PostMapping("/newworkout")
    fun createWorkOut(@RequestHeader("Authorization") token:String, workoutModel: WorkoutModel): ResponseEntity<WorkoutModel>{

        val isAuthorized = userService.tokenIsAuthorized(token)

        if(!isAuthorized) return ResponseEntity.status(401).build() // Return 401 Unauthorized

        val userId = userService.idOfToken(token)

        return ResponseEntity.ok(workoutService.addWorkOut(workoutModel, userId)?.toModel(userId))

    }

    @GetMapping("/myworkouts")
    fun allMyWorkOuts(@RequestHeader("Authorization") token:String): ResponseEntity<List<WorkoutModel>>{

        val isAuthorized = userService.tokenIsAuthorized(token)

        if(!isAuthorized) return ResponseEntity.status(401).build() // Return 401 Unauthorized

        val userId = userService.idOfToken(token)

        return ResponseEntity.ok(workoutService.getByUserId(userId).map { it.toModel(userId) })

    }

    @PutMapping("/updateworkout")
    fun updateWorkOut(@RequestHeader("Authorization") token:String, @RequestBody workoutModel: WorkoutModel): ResponseEntity<WorkoutModel>{

        val isAuthorized = userService.tokenIsAuthorized(token)

        if(!isAuthorized) return ResponseEntity.status(401).build() // Return 401 Unauthorized

        val userId = userService.idOfToken(token)

        return ResponseEntity.ok(workoutService.updateExercise(workoutModel, userId)?.toModel(userId))

    }

}

data class WorkoutModel(
    var id: Int = 0,
    var baseRoutine: RoutineModel? = null,
    var exercisesAndSets: MutableList<Pair<ExerciseModel, MutableList<SetModel>>> = mutableListOf(),
    val date: Date,
    var title: String,
    var isFinished: Boolean = false
) {
    /***/
    fun toEntity(userId: Long=0L): WorkOutEntity {
        return WorkOutEntity(
            workOutId = 0,
            date = date.time,
            title = title,
            isFinished = isFinished,
            userId = userId
        )
    }
}

data class SetModel(
    var id: Int = 0,
    var weight: Double,
    var exercise: ExerciseModel?,
    var workoutDone: WorkoutModel?,
    var reps: Int,
    val date: Date,
    var observations: String
){
    fun toEntity(userId: Long): SetEntity {
        return SetEntity(
            setId = 0,
            exerciseDone = null,
            workoutDone = null,
            weight = weight,
            reps = reps,
            date = date.time.toString(),
            observations = observations,
            userId = userId
        )
    }
}