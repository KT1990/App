package xx.xx;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
public class Tickets {
    final private List<Ticket> tickets;

    private Tickets() {
        tickets=new ArrayList<>();
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}
