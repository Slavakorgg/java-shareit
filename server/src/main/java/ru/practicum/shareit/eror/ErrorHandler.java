package ru.practicum.shareit.eror;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.*;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationError(final ValidationException e) {
        log.error("Ошибка валидации {}", e.getMessage());
        return new ErrorResponse(
                "Ошибка валидации",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final NotFoundException e) {
        log.error("Искомый объект не найдена {}", e.getMessage());
        return new ErrorResponse(
                "Искомый объект не найден",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNoDataFound(final NoDataFoundException e) {
        log.error("Ошибка заполнения данных {}", e.getMessage());
        return new ErrorResponse(
                "Ошибка заполнения данных",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAvailableError(final NotAvailableItemException e) {
        log.error("Ошибка с доступностью вещи {}", e.getMessage());
        return new ErrorResponse(
                "Ошибка с доступностью вещи",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNoAccessException(final NoAccessException e) {
        log.error("Ошибка доступа {}", e.getMessage());
        return new ErrorResponse(
                "Ошибка доступа",
                e.getMessage()
        );
    }
}
