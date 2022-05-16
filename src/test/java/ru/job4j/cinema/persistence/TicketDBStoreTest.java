package ru.job4j.cinema.persistence;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TicketDBStoreTest {

    @Before
    public void wipeTableTicket() throws SQLException {
        try (PreparedStatement statement = new Main().loadPool().getConnection()
                .prepareStatement("DELETE FROM ticket")) {
            statement.execute();
        }
    }

    @Before
    public void wipeTableUsers() throws SQLException {
        try (PreparedStatement statement = new Main().loadPool().getConnection()
                .prepareStatement("DELETE FROM users")) {
            statement.execute();
        }
    }

    @Test
    public void whenAdd() {
        TicketDBStore store = new TicketDBStore(new Main().loadPool());
        UserDBStore userDBStore = new UserDBStore(new Main().loadPool());
        User user = new User("user", "email", "phone", "password");
        userDBStore.add(user);
        user = userDBStore.findUserByNameAndPwd("email", "password").get();
        Ticket ticket = new Ticket(1, 1, 2, 1, user.getId());
        assertThat(store.add(ticket).orElse(null), is(ticket));
    }
}