package com.social.bookmyshow.Transformers;

import com.social.bookmyshow.model.Movie;
import com.social.bookmyshow.model.Show;
import com.social.bookmyshow.model.Theatre;
import com.social.bookmyshow.model.Ticket;
import com.social.bookmyshow.payload.TicketResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TicketTransformer {


    public static TicketResponseDTO toTicketResponseDTO(Show show,Movie movie,Theatre theatre,String seats,Integer totalAmount) {
        return TicketResponseDTO.builder()
                .date(show.getDate())
                .movieTitle(movie.getTitle())
                .theatreName(theatre.getTheatreName())
                .address(theatre.getAddress())
                .bookedSeats(seats)
                .price(totalAmount)
                .build();
    }
}
