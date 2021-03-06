package logging;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerP {
    public static Logger logger;
    private static String logfileName = "PDE-";

    static {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy hh-mm-ss");
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

//  LoggerP.logger.log(Level.SEVERE, "Starting application", "LoggerP");
//Error:error: pathspec 'pde/src/main/java/trestview/tasks/conveyorPDE/V?onConveyorPdeModel.java' did not match any file(s) known to git.
//        during executing git "C:\Program Files\Git\cmd\git.exe" -c core.quotepath=false checkout HEAD -- pde/src/main/java/trestview/tasks/conveyorPDE/VСonConveyorPdeModel.java