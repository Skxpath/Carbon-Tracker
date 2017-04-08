package alex.carbon_tracker.Model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Created by Sachin on 2017-03-27.
 *
 * DateManager class to manage different dates.
 *
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

    public boolean deleteJourney(Journey journey) {
        DateYMD dateYM = DateManager.getYMDFormat(journey.getDate());
        for (int i = 0; i < getJourneyDateList().size(); i++) {
            if (dateYM.getDay() == getJourneyDateList().get(i).getDay() && dateYM.getMonth() == getJourneyDateList().get(i).getMonth()
                    && dateYM.getYear() == getJourneyDateList().get(i).getYear()) {
                for (int j = 0; j < journeyDateList.get(i).getJourneys().getSize(); j++) {
                    Journey journey1 = journeyDateList.get(i).getJourney(j);
                    if (journey.equals(journey1)) {
                        journeyDateList.get(i).getJourneys().delete(j);
                        for (int g = 0; g < journeyDateList.get(i).getJourneys().getSize(); g++) {
                            Log.i("list123", journeyDateList.get(i).getJourney(g).getCarbonEmitted() + " ");
                        }
                        Log.i("xx", "deleted ");
                        return true;
                    }
                }
            }
        }

        return false;
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

    public static DateYMD getSmallestDateFor28Days() {
        DateYMD todayDate = DateManager.getYMDFormat(new Date());
        DateYMD smallestDate = todayDate;
        if (smallestDate.getDay() - 28 <= 0) {
            smallestDate.setMonth(smallestDate.getMonth() - 1);
            if (smallestDate.getMonth() <= 0) {
                smallestDate.setMonth(12);
                smallestDate.setYear(smallestDate.getYear() - 1);
            }
            smallestDate.setDay(smallestDate.getDay() - 28 + 30);
        } else {
            smallestDate.setDay(smallestDate.getDay() - 28);
        }

        return smallestDate;
    }

    public static List<DateYMD> datefilterfor28Days(DateYMD smallestDate) {
        Log.i("smalle", smallestDate.getDay() + " " + smallestDate.getMonth());

        List<DateYMD> dateYMDList = new ArrayList<>();
        DateYMD currentDate = DateManager.getYMDFormat(new Date());
        Log.i("smalles", currentDate.getDay() + " " + currentDate.getMonth());

        int x = 0;
        for (int i = 0; i < 28; i++) {
            if (smallestDate.getYear() == currentDate.getYear()) {
                if (smallestDate.getMonth() == currentDate.getMonth()) {
                    DateYMD dateYMD = new DateYMD(smallestDate.getYear(), smallestDate.getMonth(), smallestDate.getDay());
                    dateYMD.setDay(dateYMD.getDay() + i);
                    Log.i("qwer", dateYMD.getDay() + " " + dateYMD.getMonth() + " " + dateYMD.getYear());
                    dateYMDList.add(dateYMD);
                } else {
                    DateYMD dateYMD = new DateYMD(currentDate.getYear(), currentDate.getMonth(), currentDate.getDay());
                    dateYMD.setMonth(dateYMD.getMonth() - 1);
                    if (dateYMD.getMonth() <= 0) {
                        dateYMD.setYear(dateYMD.getYear() - 1);
                    }
                    dateYMD.setDay(dateYMD.getDay() + 2 + i);
                    if (dateYMD.getDay() > 30) {
                        x++;
                        dateYMD.setDay(x);
                        dateYMD.setMonth(dateYMD.getMonth() + 1);
                    }
                    Log.i("qwer1", dateYMD.getDay() + " " + dateYMD.getMonth() + " " + dateYMD.getYear());
                    dateYMDList.add(dateYMD);
                }

            } else {
                DateYMD dateYMD = new DateYMD(currentDate.getYear() - 1, currentDate.getMonth(), currentDate.getDay());
                dateYMD.setMonth(12);
                dateYMD.setDay(dateYMD.getDay() + 2 - i);
                if (dateYMD.getDay() > 30) {
                    dateYMD.setDay(1);
                    dateYMD.setMonth(dateYMD.getMonth() + 1);
                }
                Log.i("qwer2", dateYMD.getDay() + " " + dateYMD.getMonth() + " " + dateYMD.getYear());
                dateYMDList.add(dateYMD);
            }
        }
        return dateYMDList;
    }

    public static DateYMD getSmallestDateMonth(DateYMD currentDate) {
        DateYMD smallestDate = currentDate;
        smallestDate.setMonth(smallestDate.getMonth() - 1);
        if (smallestDate.getMonth() <= 0) {
            smallestDate.setMonth(12);
            smallestDate.setYear(smallestDate.getYear() - 1);
        }
        return smallestDate;
    }


    public static List<DateYMD> datefilterforMonth(DateYMD currentDate, boolean isItForYearGraph) {
        List<DateYMD> dateYMDList = new ArrayList<>();
        Log.i("current", currentDate.getDay() + " " + currentDate.getMonth() + " " + currentDate.getYear() + " ");
        int x = 0;
        for (int i = 0; i < 30; i++) {
            DateYMD dateYMD = new DateYMD(currentDate.getYear(), currentDate.getMonth(), currentDate.getDay());
            dateYMD.setDay(dateYMD.getDay() - i);
            if (dateYMD.getDay() <= 0) {
                if (isItForYearGraph) {
                    break;
                } else {
                    dateYMD.setMonth(dateYMD.getMonth() - 1);
                    if (dateYMD.getMonth() <= 0) {
                        dateYMD.setMonth(12);
                        dateYMD.setYear(dateYMD.getYear() - 1);
                    }
                    dateYMD.setDay(30 - x);
                    x++;

                }
            }
            dateYMDList.add(dateYMD);
        }
        for (int i = 0; i < dateYMDList.size(); i++) {
            Log.i("date123", dateYMDList.get(i).getDay() + " " + dateYMDList.get(i).getMonth() + " " + dateYMDList.get(i).getYear() + " " + i);
        }


        return dateYMDList;
    }
}