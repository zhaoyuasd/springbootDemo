package dubbo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author dongli
 * @create 2025/9/29 14:53
 * @desc
 */

public class Log4j2MainDemo {
    private static final Logger logger = LogManager.getLogger(Log4j2MainDemo.class);

    public static void main(String[] args) {
        logger.trace("trace message");
        logger.debug("debug message");
        logger.info("info message");
        logger.warn("warn message");
        logger.error("error message");
        logger.fatal("fatal message");

        System.out.println("Main 方法执行完成");
    }
}
