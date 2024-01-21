package com.komsije.booking.dto;

import com.komsije.booking.model.*;
import com.komsije.booking.validators.IdentityConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDto {
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    private LocalDate dateCreated;
    @Min(value = 1, message = "Number of days must be greater or equal to 1")
    private int days;
    @Min(1)
    private double price;
    @NotNull
    private ReservationStatus reservationStatus;
    @IdentityConstraint
    private Long accommodationId;
    @IdentityConstraint
    private Long guestId;
    @IdentityConstraint
    private Long hostId;
    @Min(value = 1, message = "Number of guests must be greater or equal to 1")
    private Integer numberOfGuests;


//    public ReservationDto(Reservation reservation){
//        this.id=reservation.getId();
//        this.startDate=reservation.getStartDate();
//        this.days=reservation.getDays();
//        this.price=reservation.getPrice();
//        this.reservationStatus=reservation.getReservationStatus();
//        this.accommodationId=reservation.getAccommodation().getId();
//    }

}
