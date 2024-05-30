package xx.xx;

import java.util.*;

public class Calculate {
    public static String minFlyTimeCarrier(HashMap<String, List<Long[]>> carriers){
        String result,hour,minute;
        List<String> listMinFlyTimeCarrier=new ArrayList<>();
        for(Map.Entry<String, List<Long[]>> mapCarrier:carriers.entrySet()){
            int minTime=0;
            List<Integer> listFlyTimes=new ArrayList<>();
            for(Long[] dateTimes:mapCarrier.getValue()){
                listFlyTimes.add((int) (dateTimes[0]-dateTimes[1])/(60*1000));
            }
            Collections.sort(listFlyTimes);
            minTime=listFlyTimes.get(0);
            hour=minTime/60+"ч";
            minute=DataProcessing.fixTimeNum(minTime%60)+"мин";
            listMinFlyTimeCarrier.add(
                "Перевозчик "+mapCarrier.getKey()+", Мин. время полёта: "+hour+" "+minute
            );
        }
        result=String.join("\n",listMinFlyTimeCarrier);
        return result;
    }
    public static Double avgPrice(List<Ticket> tickets){
        return tickets.stream().mapToInt(Ticket::getPrice).average().orElse(0.0);
    }
    public static Double medianPrice(List<Ticket> tickets){
        double median=0.0;
        int[] priceArray=new int[tickets.size()];
        for(int i=0;i<tickets.size();i++){
            priceArray[i]=tickets.get(i).getPrice();
        }
        Arrays.sort(priceArray);
        if (priceArray.length % 2 == 0) {
            median = (double) (priceArray[priceArray.length / 2] + priceArray[priceArray.length / 2 - 1]) / 2;
        }else{
            median = priceArray[priceArray.length / 2];
        }
        return median;
    }
    public static Double diffAvgAndMedianPrice(List<Ticket> tickets){
        return avgPrice(tickets) - medianPrice(tickets);
    }
}