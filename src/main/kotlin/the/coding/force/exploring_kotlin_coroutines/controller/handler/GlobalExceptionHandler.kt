package the.coding.force.exploring_kotlin_coroutines.controller.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import the.coding.force.exploring_kotlin_coroutines.exception.DataNotFoundException
import java.time.ZonedDateTime

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    private fun createNewResponseError(
        status: HttpStatus,
        exception: Throwable,
        request: ServletWebRequest
    ): ResponseError {
        return ResponseError(
            ZonedDateTime.now(),
            status.value(),
            status.name,
            exception.message ?: "default message",
            exception.javaClass.name,
            request.request.requestURI
        )
    }

    @ExceptionHandler(DataNotFoundException::class)
    fun handlerDataNotFoundException(
        exception: DataNotFoundException,
        request: ServletWebRequest
    ): ResponseEntity<ResponseError> {
        return ResponseEntity(
            createNewResponseError(
                HttpStatus.NOT_FOUND,
                exception,
                request
            ),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(
        exception: Exception,
        request: ServletWebRequest
    ): ResponseEntity<ResponseError> {
        return ResponseEntity(
            createNewResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception,
                request
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
