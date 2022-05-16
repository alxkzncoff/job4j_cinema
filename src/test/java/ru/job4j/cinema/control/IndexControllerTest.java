package ru.job4j.cinema.control;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

    @Test
    public void whenIndex() {
        List<Session> sessions = Arrays.asList(
                new Session(1, "Матрица"),
                new Session(2, "Властелин колец"),
                new Session(3, "Мстители")
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        SessionService sessionService = mock(SessionService.class);
        when(sessionService.findAll()).thenReturn(sessions);
        IndexController indexController = new IndexController(sessionService);
        String page = indexController.index(model, session, false);
        verify(model).addAttribute("sessions", sessions);
        assertThat(page, is("index"));
    }
}