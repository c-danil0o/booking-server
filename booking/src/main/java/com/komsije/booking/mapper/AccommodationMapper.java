package com.komsije.booking.mapper;

import com.komsije.booking.dto.*;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Address;
import com.komsije.booking.model.TimeSlot;
import com.komsije.booking.repository.AccommodationRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

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
        if (accommodationRepository.existsById(accommodationDto.getId())){
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

    public SearchedAccommodationDto toSearchedDto(Accommodation accommodation){
        SearchedAccommodationDto searchedAccommodationDto = new SearchedAccommodationDto();
        searchedAccommodationDto.setId(accommodation.getId());
        searchedAccommodationDto.setName(accommodation.getName());
        searchedAccommodationDto.setDescription(accommodation.getDescription());
        AddressDto addressDto = new AddressDto();
        Address address = accommodation.getAddress();
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setNumber(address.getNumber());
        addressDto.setId(address.getId());
        searchedAccommodationDto.setAddress(addressDto);
        searchedAccommodationDto.setAccommodationType(accommodation.getAccommodationType());
        searchedAccommodationDto.setAmenities(accommodation.getAmenities());
        searchedAccommodationDto.setMaxGuests(accommodation.getMaxGuests());
        searchedAccommodationDto.setMinGuests(accommodation.getMinGuests());
        searchedAccommodationDto.setPhotos(accommodation.getPhotos());
        searchedAccommodationDto.setCancellationDeadline(accommodation.getCancellationDeadline());
        searchedAccommodationDto.setAverageGrade(accommodation.getAverageGrade());
        return searchedAccommodationDto;
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

    /*public void update(@MappingTarget Accommodation accommodation, AccommodationDto accommodationDto) {
        if (accommodationDto == null) {
            return;
        }

        accommodation.setId(accommodationDto.getId());
        accommodation.setName(accommodationDto.getName());
        accommodation.setDescription(accommodationDto.getDescription());
        if (accommodationDto.getAddress() != null) {
            accommodation.setAddress(new Address());

            addressMapper.update(accommodation.getAddress(), accommodationDto.getAddress());
        }
        accommodation.setAccommodationType(accommodationDto.getAccommodationType());
        if (accommodation.getAmenities() != null) {
            Set<String> set = accommodationDto.getAmenities();
            if (set != null) {
                accommodation.getAmenities().clear();
                accommodation.getAmenities().addAll(set);
            } else {
                accommodation.setAmenities(null);
            }
        } else {
            Set<String> set = accommodationDto.getAmenities();
            if (set != null) {
                accommodation.setAmenities(new LinkedHashSet<String>(set));
            }
        }
        if (accommodation.getAvailability() != null) {
            Set<TimeSlot> set1 = timeSlotDtoSetToTimeSlotSet(accommodationDto.getAvailability());
            if (set1 != null) {
                accommodation.getAvailability().clear();
                accommodation.getAvailability().addAll(set1);
            } else {
                accommodation.setAvailability(null);
            }
        } else {
            Set<TimeSlot> set1 = timeSlotDtoSetToTimeSlotSet(accommodationDto.getAvailability());
            if (set1 != null) {
                accommodation.setAvailability(set1);
            }
        }
        accommodation.setMaxGuests(accommodationDto.getMaxGuests());
        accommodation.setMinGuests(accommodationDto.getMinGuests());
        if (accommodation.getPhotos() != null) {
            Set<String> set2 = accommodationDto.getPhotos();
            if (set2 != null) {
                accommodation.getPhotos().clear();
                accommodation.getPhotos().addAll(set2);
            } else {
                accommodation.setPhotos(null);
            }
        } else {
            Set<String> set2 = accommodationDto.getPhotos();
            if (set2 != null) {
                accommodation.setPhotos(new LinkedHashSet<String>(set2));
            }
        }
        accommodation.setPricePerGuest(accommodationDto.isPricePerGuest());
        accommodation.setCancellationDeadline(accommodationDto.getCancellationDeadline());
        accommodation.setAutoApproval(accommodationDto.isAutoApproval());
        accommodation.setAverageGrade(accommodationDto.getAverageGrade());
        accommodation.setApproved(accommodationDto.isApproved());
    }
    protected Set<TimeSlotDto> timeSlotSetToTimeSlotDtoSet(Set<TimeSlot> set) {
        if ( set == null ) {
            return null;
        }

        Set<TimeSlotDto> set1 = new LinkedHashSet<TimeSlotDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( TimeSlot timeSlot : set ) {
            set1.add( timeSlotMapper.toDto( timeSlot ) );
        }

        return set1;
    }

    protected Set<TimeSlot> timeSlotDtoSetToTimeSlotSet(Set<TimeSlotDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<TimeSlot> set1 = new LinkedHashSet<TimeSlot>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( TimeSlotDto timeSlotDto : set ) {
            set1.add( timeSlotMapper.fromDto( timeSlotDto ) );
        }

        return set1;
    }*/

    public void update(@MappingTarget Accommodation accommodation, AvailabilityDto availabilityDto) {
        if (availabilityDto.getCancellationDeadline() != null)
            accommodation.setCancellationDeadline(availabilityDto.getCancellationDeadline());
        for (TimeSlotDto timeSlotDto : availabilityDto.getAvailability()) {
            accommodation.getAvailability().add(new TimeSlot(null, timeSlotDto.getStartDate().toLocalDate(), timeSlotDto.getEndDate().toLocalDate(), timeSlotDto.getPrice(), timeSlotDto.isOccupied()));
        }
    }
}
