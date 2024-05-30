package xx.xx;

import java.text.ParseException;
import java.util.*;

public class App
{
    public static void main(String[] args) {
        DataProcessing dataProcessing = new DataProcessing("/tickets.json");
        List<Ticket> tickets = dataProcessing.getTickets();
        HashMap<String,List<Long[]>> carriers = new HashMap<>();

        try {
            carriers = dataProcessing.timeFlyGroupByCarrier(tickets);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        String resultFirstQuestion = Calculate.minFlyTimeCarrier(carriers);
        Double resultTwoQuestion = Calculate.diffAvgAndMedianPrice(tickets);

        System.out.println(
            "Минимальное время полёта между городами Владивосток и Тель-Авив \nдля каждого авиаперевозчика:\n"+
            resultFirstQuestion+"\n\n"+
            "Разница между средней ценой и медианой для полёта \nмежду городами Владивосток и Тель-Авив:\n"+
            resultTwoQuestion+"руб"
        );

    }
}