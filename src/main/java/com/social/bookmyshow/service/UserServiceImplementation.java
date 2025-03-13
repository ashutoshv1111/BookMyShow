package com.social.bookmyshow.service;

import com.social.bookmyshow.Transformers.UserTransformer;
import com.social.bookmyshow.exceptionHandling.APIException;
import com.social.bookmyshow.exceptionHandling.ResourceNotFoundException;
import com.social.bookmyshow.model.*;
import com.social.bookmyshow.payload.TicketResponseDTO;
import com.social.bookmyshow.payload.UserDTO;
import com.social.bookmyshow.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO updateUser(String userId, UserDTO userDTO) {
     AppUser user=userRepository.findById(userId)
             .orElseThrow(() -> new ResourceNotFoundException("AppUser", "id", userId));
     AppUser newUser= UserTransformer.toUserTransformer(userDTO);
     userRepository.save(newUser);
     return modelMapper.map(newUser, UserDTO.class);
    }

    @Override
    public UserDTO getUser(String userId) {
        if (userRepository.existsById(userId)) {
            return modelMapper.map(userRepository.findById(userId), UserDTO.class);
        }
        else
            throw new ResourceNotFoundException("AppUser", "id", userId);
    }

    @Override
    public UserDTO deleteUser(String userId) {
            AppUser user=userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("AppUser", "id", userId));
            userRepository.deleteById(userId);
            return modelMapper.map(user, UserDTO.class);
        }

    @Override
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
