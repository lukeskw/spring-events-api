package lukeskw.com.passin.services;

import lombok.RequiredArgsConstructor;
import lukeskw.com.passin.domain.attendee.Attendee;
import lukeskw.com.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import lukeskw.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import lukeskw.com.passin.domain.checkin.CheckIn;
import lukeskw.com.passin.dto.attendee.*;
import lukeskw.com.passin.repositories.AttendeeRepository;
import lukeskw.com.passin.repositories.CheckinRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){

        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);

            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public void registerAttendee(Attendee newAttendee){
        this.attendeeRepository.save(newAttendee);
    }

    public void verifyAttendeeSubscription(String email, String eventId){
        Optional<Attendee> attendee = this.attendeeRepository.findByEventIdAndEmail(eventId, email);

        if(attendee.isPresent()){
            throw new AttendeeAlreadyRegisteredException("Attendee is already registered");
        }
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = this.getAttendee(attendeeId);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri , attendee.getEvent().getId());

        return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);
    }

    public void checkInAttendee(String attendeeId){
        Attendee attendee = this.getAttendee(attendeeId);
        this.checkInService.registerCheckIn(attendee);
    }

    private Attendee getAttendee(String attendeeId){
        return this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID:" + attendeeId));
    }
}
