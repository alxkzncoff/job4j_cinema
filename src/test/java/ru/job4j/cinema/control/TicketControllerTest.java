package ru.job4j.cinema.control;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Seat;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class TicketControllerTest {

    @Test
    public void whenAddTicket() {
        Ticket input = new Ticket(1, 1, 1, 1, 1);
        TicketService ticketService = mock(TicketService.class);
        SessionService sessionService = mock(SessionService.class);
        TicketController ticketController = new TicketController(sessionService, ticketService);
        ticketController.addTicket(input);
        verify(ticketService).add(input);
    }

    @Test
    public void whenTicketReserved() {
        Ticket input = new Ticket(1, 1, 1, 1, 1);
        TicketService ticketService = mock(TicketService.class);
        SessionService sessionService = mock(SessionService.class);
        TicketController ticketController = new TicketController(sessionService, ticketService);
        String page = ticketController.addTicket(input);
        verify(ticketService).add(input);
        assertThat(page, is("redirect:/index?fail=true"));
    }

    @Test
    public void whenSeats() {
        List<Seat> seats = Arrays.asList(
                new Seat(1, 2),
                new Seat(2, 1)
        );
        int id = 1;
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        TicketService ticketService = mock(TicketService.class);
        SessionService sessionService = mock(SessionService.class);
        when(sessionService.findFreeSeats(id)).thenReturn(seats);
        TicketController ticketController = new TicketController(sessionService, ticketService);
        String page = ticketController.seats(model, id, session);
        verify(model).addAttribute("seats", seats);
        assertThat(page, is("seats"));
    }

}