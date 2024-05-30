package xx.xx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProcessing {
    private String json;

    DataProcessing(String filename) {
        json="";
        try(
            InputStream inputStream=getClass().getResourceAsStream(filename)
        ) {
            if(inputStream != null){
                try(InputStreamReader inputStreamReader=new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                    Stream<String> lineStream=bufferedReader.lines()
                ){
                    json=lineStream.collect(Collectors.joining(""));
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }else{
                System.out.println(filename + " not found");
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public List<Ticket> getTickets() {
        List<Ticket> listTicket=new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        json=json.replace("\uFEFF", "");

        try {
            listTicket = mapper.readValue(json, Tickets.class).getTickets();
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

        return listTicket;
    }

    public HashMap<String,List<Long[]>> timeFlyGroupByCarrier(List<Ticket> tickets) throws ParseException {
        Long[] dateTimes;
        HashMap<String,List<Long[]>> carriers=new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for(Ticket ticket:tickets){
            dateTimes=new Long[]{0L, 0L};
            dateTimes[0]=format.parse(ticket.getArrival_date()+" "+ticket.getArrival_time()).getTime();
            dateTimes[1]=format.parse(ticket.getDeparture_date()+" "+ticket.getDeparture_time()).getTime();
            if(!carriers.containsKey(ticket.getCarrier())){
                carriers.put(ticket.getCarrier(),new ArrayList<>());
            }
            carriers.get(ticket.getCarrier()).add(dateTimes);
        }
        return carriers;
    }

    public static String fixTimeNum(Integer num){
        String fixNum;
        if(num<10){
            fixNum="0"+num;
        }else{
            fixNum=num+"";
        }
        return fixNum;
    }
}