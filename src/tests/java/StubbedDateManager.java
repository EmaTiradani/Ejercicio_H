package tests.java;

import main.java.utils.DateManager;

import java.util.Date;

public class StubbedDateManager implements DateManager {
    Date fixedDate;

    @Override
    public Date getDate() {
        return fixedDate;
    }

    StubbedDateManager(Date date){
        fixedDate = date;
    }


}
