//package com.social.bookmyshow.Transformers;
//
//import com.social.bookmyshow.model.Movie;
//import com.social.bookmyshow.model.Show;
//import com.social.bookmyshow.model.Theatre;
//import com.social.bookmyshow.model.Ticket;
//import com.social.bookmyshow.payload.TicketDTO;
//import com.social.bookmyshow.payload.TicketResponseDTO;
//import com.social.bookmyshow.repositories.MovieRepository;
//import com.social.bookmyshow.repositories.ShowRepository;
//import com.social.bookmyshow.repositories.TheatreRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TicketTransformer {
//
//    @Autowired
//    private ShowRepository showRepository;
//
//    @Autowired
//    private TheatreRepository theatreRepository;
//
//    @Autowired
//    private MovieRepository movieRepository;
//
//    public static TicketResponseDTO toTicketResponseDTO(Ticket ticket) {
//        Show show = showRepository.findById(ticket.getShowId())
//                .orElseThrow(() -> new RuntimeException("Show not found"));
//
//        Theatre theatre = theatreRepository.findById(show.getTheatreId())
//                .orElseThrow(() -> new RuntimeException("Theatre not found"));
//
//        Movie movie = movieRepository.findById(show.getMovieId())
//                .orElseThrow(() -> new RuntimeException("Movie not found"));
//
//        return TicketResponseDTO.builder()
//                .bookedSeats(ticket.getBookedSeats())
//                .address(theatre.getAddress())
//                .theatreName(theatre.getTheatreName())
//                .movieTitle(movie.getTitle())
//                .date(show.getDate())
//                .price(ticket.getPrice())
//                .build();
//    }
//}
