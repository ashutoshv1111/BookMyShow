package com.social.bookmyshow.service;

import com.social.bookmyshow.model.Seat;
import com.social.bookmyshow.payload.TicketDTO;
import com.social.bookmyshow.payload.TicketResponseDTO;

import java.util.List;

public interface TicketService {

    TicketResponseDTO addBooking(TicketDTO ticketDTO);
    TicketDTO getTicket(String id);
}
