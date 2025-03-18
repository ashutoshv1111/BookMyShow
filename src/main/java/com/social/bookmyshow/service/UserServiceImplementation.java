package com.social.bookmyshow.service;

import com.social.bookmyshow.Transformers.UserTransformer;
import com.social.bookmyshow.exceptionHandling.APIException;
import com.social.bookmyshow.exceptionHandling.ResourceNotFoundException;
import com.social.bookmyshow.model.*;
import com.social.bookmyshow.payload.TicketResponseDTO;
import com.social.bookmyshow.payload.UserDTO;
import com.social.bookmyshow.repositories.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TheatreRepository theatreRepository;
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @CacheEvict(value="User")
    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
     AppUser user=userRepository.findById(userId)
             .orElseThrow(() -> new ResourceNotFoundException("AppUser", "id", userId));
     AppUser newUser= UserTransformer.toUserTransformer(userDTO);
     userRepository.save(newUser);
     return modelMapper.map(newUser, UserDTO.class);
    }

    @Override
    @Cacheable(value="User")
    public UserDTO getUser(String userId) {
        if (userRepository.existsById(userId)) {
            return modelMapper.map(userRepository.findById(userId), UserDTO.class);
        }
        else
            throw new ResourceNotFoundException("AppUser", "id", userId);
    }

    @Override
    @CacheEvict(value="User")
    public UserDTO deleteUser(String userId) {
            AppUser user=userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("AppUser", "id", userId));
            userRepository.deleteById(userId);
            return modelMapper.map(user, UserDTO.class);
        }

    @Override
    @Cacheable(value="UserTickets")
    public List<TicketResponseDTO> getUserTickets(String userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<String> ticketIds = user.getTicketIds();
        if (ticketIds == null || ticketIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Ticket> tickets = ticketRepository.findAllById(ticketIds);
        List<TicketResponseDTO> ticketResponseDTOs = new ArrayList<>();

        for (Ticket ticket : tickets) {
            Show show = showRepository.findById(ticket.getShowId())
                    .orElseThrow(() -> new APIException("Show not found"));

//            System.out.println("Show Date from DB: " + show.getDate());

            Movie movie = movieRepository.findById(show.getMovieId())
                    .orElseThrow(() -> new APIException("Movie not found"));

            Theatre theatre = theatreRepository.findById(show.getTheatreId())
                    .orElseThrow(() -> new APIException("Theatre not found"));

            TicketResponseDTO responseDTO = TicketResponseDTO.builder()
                    .date(show.getDate())
                    .movieTitle(movie.getTitle())
                    .theatreName(theatre.getTheatreName())
                    .address(theatre.getAddress())
                    .bookedSeats(ticket.getBookedSeats())
                    .price(ticket.getPrice())
                    .build();

            ticketResponseDTOs.add(responseDTO);
        }


        return ticketResponseDTOs;
    }


    @Override
    public UserDTO addUser(UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail()))
            throw new APIException("User already exists");
        AppUser user=UserTransformer.toUserTransformer(userDTO);
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

}
