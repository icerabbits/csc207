package logger;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    private Logger logger;
    private FileHandler fh;

    public Log(String fileName) throws SecurityException, IOException {
        File file = new File(fileName);
        if(!file.exists()) {
            file.createNewFile();
        }

        fh = new FileHandler(fileName, true);
        logger = Logger.getLogger("test");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    public Logger getLogger() {
        return logger;
    }

    public FileHandler getFh() {
        return fh;
    }
}
