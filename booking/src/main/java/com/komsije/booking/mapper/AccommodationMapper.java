package com.komsije.booking.mapper;

import com.komsije.booking.dto.*;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Address;
import com.komsije.booking.model.TimeSlot;
import com.komsije.booking.repository.AccommodationRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, TimeSlotMapper.class, HostMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public abstract class AccommodationMapper {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private HostMapper hostMapper;
    @Autowired
    private TimeSlotMapper timeSlotMapper;
    @Autowired
    private AccommodationRepository accommodationRepository;
    public abstract AccommodationDto toDto(Accommodation accommodation);

    public Accommodation fromDto(AccommodationDto accommodationDto) {
        if ( accommodationDto == null ) {
            return null;
        }
        if (accommodationDto.getId()!= null && accommodationRepository.existsById(accommodationDto.getId())){
            return accommodationRepository.findById(accommodationDto.getId()).orElse(null);
        }

        Accommodation accommodation = new Accommodation();

        accommodation.setId( accommodationDto.getId() );
        accommodation.setName( accommodationDto.getName() );
        accommodation.setDescription( accommodationDto.getDescription() );
        accommodation.setAddress( addressMapper.fromDto( accommodationDto.getAddress() ) );
        accommodation.setAccommodationType( accommodationDto.getAccommodationType() );
        Set<String> set = accommodationDto.getAmenities();
        if ( set != null ) {
            accommodation.setAmenities( new LinkedHashSet<String>( set ) );
        }
        accommodation.setAvailability( timeSlotDtoSetToTimeSlotList( accommodationDto.getAvailability() ) );
        accommodation.setMaxGuests( accommodationDto.getMaxGuests() );
        accommodation.setHost( hostMapper.fromDto( accommodationDto.getHost() ) );
        accommodation.setMinGuests( accommodationDto.getMinGuests() );
        Set<String> set1 = accommodationDto.getPhotos();
        if ( set1 != null ) {
            accommodation.setPhotos( new LinkedHashSet<String>( set1 ) );
        }
        accommodation.setPricePerGuest( accommodationDto.isPricePerGuest() );
        accommodation.setCancellationDeadline( accommodationDto.getCancellationDeadline() );
        accommodation.setAutoApproval( accommodationDto.isAutoApproval() );
        accommodation.setAverageGrade( accommodationDto.getAverageGrade() );
        accommodation.setStatus( accommodationDto.getStatus() );

        return accommodation;
    }

    public abstract List<AccommodationDto> toDto(List<Accommodation> accommodationList);
    public abstract void update(@MappingTarget Accommodation accommodation, AccommodationDto accommodationDto);

    public SearchResponseDto toSearchedDto(Accommodation accommodation){
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        searchResponseDto.setId(accommodation.getId());
        searchResponseDto.setName(accommodation.getName());
        searchResponseDto.setDescription(accommodation.getDescription());
        AddressDto addressDto = new AddressDto();
        Address address = accommodation.getAddress();
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setNumber(address.getNumber());
        addressDto.setId(address.getId());
        searchResponseDto.setAddress(addressDto);
        searchResponseDto.setAccommodationType(accommodation.getAccommodationType());
        searchResponseDto.setAmenities(accommodation.getAmenities());
        searchResponseDto.setMaxGuests(accommodation.getMaxGuests());
        searchResponseDto.setMinGuests(accommodation.getMinGuests());
        searchResponseDto.setPhotos(accommodation.getPhotos());
        searchResponseDto.setCancellationDeadline(accommodation.getCancellationDeadline());
        DecimalFormat df = new DecimalFormat("#.##");
        searchResponseDto.setAverageGrade(Double.parseDouble(df.format(accommodation.getAverageGrade())));
        return searchResponseDto;
    }

    protected List<TimeSlot> timeSlotDtoSetToTimeSlotList(Set<TimeSlotDto> set) {
        if ( set == null ) {
            return null;
        }

        List<TimeSlot> list = new ArrayList<TimeSlot>( set.size() );
        for ( TimeSlotDto timeSlotDto : set ) {
            list.add( timeSlotMapper.fromDto( timeSlotDto ) );
        }

        return list;
    }
    public void update(@MappingTarget Accommodation accommodation, AvailabilityDto availabilityDto) {
        if (availabilityDto.getCancellationDeadline() != null)
            accommodation.setCancellationDeadline(availabilityDto.getCancellationDeadline());
        for (TimeSlotDto timeSlotDto : availabilityDto.getAvailability()) {
            accommodation.getAvailability().add(new TimeSlot(null, timeSlotDto.getStartDate().toLocalDate(), timeSlotDto.getEndDate().toLocalDate(), timeSlotDto.getPrice(), timeSlotDto.isOccupied()));
        }
    }
}
