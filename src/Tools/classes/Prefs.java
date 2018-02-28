package Tools.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;

public class Prefs {

    public Prefs() {
    }

    public String convertDate(String strDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar c = Calendar.getInstance();

        Date d = dateFormat.parse(strDate);

        Date s = c.getTime();

        long diffInMs = ((s.getTime() - d.getTime())/1000)-3600;


        Long days = diffInMs / (60 * 60 * 24);
        //diffInMs -= days * (60 * 60 * 24);
        // Long hours = diffInMs / (60 * 60);

        Long hours =((60-(-diffInMs / 60))/60);
        // diffInMs -= hours * (60 * 60);
        Long minutes = 60-(-diffInMs / 60);
        // diffInMs -= minutes *60;
        Long seconds = -diffInMs / 60 /60;


        //  String temp = " j: "+days+ " / H: "+hours+ " / M: "+minutes+" / S: "+diffInMs;



        if (days>=1)

            return days+" jours";
        else if (hours>=1)

            return hours+" heures";
        else if (minutes>1 )

            return minutes+" minutes";
        else

            return "quelques instants";

//        return " j: "+days+ " / H: "+hours+ " / M: "+minutes+" / S: "+diffInMs;
    }


}
