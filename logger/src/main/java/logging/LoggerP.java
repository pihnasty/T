package logging;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Pihnastyi.O on 7/5/2016.
 */
public class LoggerP {
    public static Logger logger;
    private static String logfileName = "PDE-";

    static {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy hh-mm-ss");
            Date date = new Date();
            boolean append = true;
            FileHandler fh = new FileHandler("logger\\src\\main\\java\\logfiles\\"+logfileName+format.format(date)+".log", append);
            //fh.setFormatter(new XMLFormatter());
            fh.setFormatter(new SimpleFormatter());
            logger = Logger.getLogger(logfileName);
            logger.addHandler(fh);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}

//
//     LoggerP.logger.log(Level.SEVERE, "Starting application", "LoggerP");
