package com.example.myutils.utilities;

public final class DateUtilities {

    public static String getMonthInWords(int month) {
        String monthValue;
        switch (month) {
            case 0:
                monthValue = "January";
                break;
            case 1:
                monthValue = "February";
                break;
            case 2:
                monthValue = "March";
                break;
            case 3:
                monthValue = "April";
                break;
            case 4:
                monthValue = "May";
                break;
            case 5:
                monthValue = "June";
                break;
            case 6:
                monthValue = "July";
                break;
            case 7:
                monthValue = "August";
                break;
            case 8:
                monthValue = "September";
                break;
            case 9:
                monthValue = "October";
                break;
            case 10:
                monthValue = "November";
                break;
            default:
                monthValue = "December";
                break;
        }
        return monthValue;
    }

    public static String getMonthInTwoDigits(int month) {
        String monthValue;
        switch (month) {
            case 0:
                monthValue = "01";
                break;
            case 1:
                monthValue = "02";
                break;
            case 2:
                monthValue = "03";
                break;
            case 3:
                monthValue = "04";
                break;
            case 4:
                monthValue = "05";
                break;
            case 5:
                monthValue = "06";
                break;
            case 6:
                monthValue = "07";
                break;
            case 7:
                monthValue = "08";
                break;
            case 8:
                monthValue = "09";
                break;
            default:
                monthValue = String.valueOf(month + 1);
                break;
        }
        return monthValue;
    }

    public static String getDayInTwoDigits(int day) {
        String dayValue;
        switch (day) {
            case 1:
                dayValue = "01";
                break;
            case 2:
                dayValue = "02";
                break;
            case 3:
                dayValue = "03";
                break;
            case 4:
                dayValue = "04";
                break;
            case 5:
                dayValue = "05";
                break;
            case 6:
                dayValue = "06";
                break;
            case 7:
                dayValue = "07";
                break;
            case 8:
                dayValue = "08";
                break;
            case 9:
                dayValue = "09";
                break;
            default:
                dayValue = String.valueOf(day);
                break;
        }
        return dayValue;
    }
}
