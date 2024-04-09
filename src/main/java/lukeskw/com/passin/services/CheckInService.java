package lukeskw.com.passin.services;

import lombok.RequiredArgsConstructor;
import lukeskw.com.passin.domain.attendee.Attendee;
import lukeskw.com.passin.domain.checkin.CheckIn;
import lukeskw.com.passin.domain.checkin.exceptions.AttendeeAlreadyCheckedInException;
import lukeskw.com.passin.repositories.CheckinRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckinRepository checkinRepository;

    public void registerCheckIn(Attendee attendee){
        this.verifyCheckInExists(attendee.getId());

        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());

        this.checkinRepository.save(newCheckIn);
    }

    private void verifyCheckInExists(String attendeeId){
        Optional<CheckIn> isCheckedIn = this.getCheckIn(attendeeId);

        if(isCheckedIn.isPresent()) {
            throw new AttendeeAlreadyCheckedInException("Attendee already checked in");
        }
    }

    public Optional<CheckIn> getCheckIn(String attendeeId){
        return this.checkinRepository.findByAttendeeId(attendeeId);
    }
}
