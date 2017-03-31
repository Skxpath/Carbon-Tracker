package alex.carbon_tracker.Model;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Created by Sachin on 2017-03-27.
 */

public class DateManager {
    private List<DateYMD> journeyDateList;

    public DateManager() {
        journeyDateList = new ArrayList<>();
    }

    public DateYMD getDate(int index) {
        return journeyDateList.get(index);
    }


    public List<DateYMD> getJourneyDateList() {
        return journeyDateList;
    }


    public void setJourneyDateList(List<DateYMD> journeyDateList) {
        this.journeyDateList = journeyDateList;
    }

    public void addDateJourney(Date date, Journey journey) {
        DateYMD dateYMD = getYMDFormat(date);
        dateYMD.setJourney(journey);
        journeyDateList.add(dateYMD);
    }

    public void deleteDate(int dateNumber) {
        journeyDateList.remove(dateNumber);
    }


    public static DateYMD getYMDFormat(Date date) {

        // takes  in a date and format into the  custom DateYMD class format to add to the list.

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String dateYMDString = f.format(date);
        String s[] = dateYMDString.split("-");
        int day = Integer.parseInt(s[2]);
        int month = Integer.parseInt(s[1]);
        int year = Integer.parseInt(s[0]);
        DateYMD dateYMD = new DateYMD(year, month, day);
        return dateYMD;
    }

    public DateYMD getSmallestDateFor28Days() {
        DateYMD todayDate = DateManager.getYMDFormat(new Date());
        DateYMD smallestDate = todayDate;
        if (smallestDate.getDay() - 28 <= 0) {
            smallestDate.setMonth(smallestDate.getMonth() - 1);
            if (smallestDate.getMonth() <= 0) {
                smallestDate.setMonth(12);
            }
            smallestDate.setDay(smallestDate.getDay() - 28 + 30);
        } else {
            smallestDate.setDay(smallestDate.getDay() - 28);
        }

        return smallestDate;
    }
    public List<DateYMD> datefilterfor28Days(DateYMD smallestDate){
        List<DateYMD> dateYMDList = new ArrayList<>();
        DateYMD currentDate = DateManager.getYMDFormat(new Date());
        for(int i = 0; i <28;i++){
            if(smallestDate.getYear() == currentDate.getYear()){
                if(smallestDate.getMonth() == currentDate.getMonth()){
                    DateYMD dateYMD = new DateYMD(smallestDate.getYear(),smallestDate.getMonth(),smallestDate.getDay());
                    dateYMD.setDay(dateYMD.getDay()+i);
                    dateYMDList.add(dateYMD);
                    Log.i("date1", dateYMD.getDay()+" "+dateYMD.getMonth()+" "+dateYMD.getYear() +" "+ i);
                }
                else{
                    DateYMD dateYMD = new DateYMD(currentDate.getYear(),currentDate.getMonth(),currentDate.getDay());
                    dateYMD.setMonth(dateYMD.getMonth()-1);
                    dateYMD.setDay(dateYMD.getDay()+2+i);
                    if(dateYMD.getDay() >30){
                        dateYMD.setDay(1);
                        dateYMD.setMonth(dateYMD.getMonth()+1);
                    }
                    dateYMDList.add(dateYMD);
                    Log.i("date2", dateYMD.getDay()+" "+dateYMD.getMonth()+" "+dateYMD.getYear());
                }

            }
        }
        return dateYMDList;
    }
}