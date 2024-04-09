package lukeskw.com.passin.config;

import lukeskw.com.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import lukeskw.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import lukeskw.com.passin.domain.checkin.exceptions.AttendeeAlreadyCheckedInException;
import lukeskw.com.passin.domain.event.exceptions.EventIsFullException;
import lukeskw.com.passin.domain.event.exceptions.EventNotFoundException;
import lukeskw.com.passin.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<?> handleEventNotFound(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity<?> handleAttendeeNotFound(AttendeeNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyRegisteredException.class)
    public ResponseEntity<?> handleAttendeeAlreadyExists(AttendeeAlreadyRegisteredException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(AttendeeAlreadyCheckedInException.class)
    public ResponseEntity<?> handleAttendeeAlreadyCheckedIn(AttendeeAlreadyCheckedInException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(EventIsFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventIsFull(EventIsFullException exception){
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }
}
