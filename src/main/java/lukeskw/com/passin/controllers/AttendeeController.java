package lukeskw.com.passin.controllers;

import lombok.RequiredArgsConstructor;
import lukeskw.com.passin.dto.attendee.AttendeeBadgeResponseDTO;
import lukeskw.com.passin.dto.attendee.AttendeeIdDTO;
import lukeskw.com.passin.dto.attendee.AttendeeRequestDTO;
import lukeskw.com.passin.services.AttendeeService;
import lukeskw.com.passin.services.CheckInService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {
    private final AttendeeService attendeeService;

    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        AttendeeBadgeResponseDTO response = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity<?> registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
       this.attendeeService.checkInAttendee(attendeeId);

       var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

       return ResponseEntity.created(uri).build();
    }
}
