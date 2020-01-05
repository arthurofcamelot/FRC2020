package frc.robot.utilities;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public enum LoggerRelations {
        LEFT_MOTOR_POWER(0),
        RIGHT_MOTOR_POWER(1),
        LEFT_MOTOR_TARGET(2),
        RIGHT_MOTOR_TARGET(3),
        LEFT_MOTOR_POSISION(4),
        RIGHT_MOTOR_POSISION(5);

        public int value;
        private LoggerRelations(int value) {
            this.value = value;
        }
    }

    public final static int
    LOGGER_RATE = 1,
    
    //motors
    LEFT_DRIVE_0 = 20,
    LEFT_DRIVE_MAIN = 21,
    LEFT_DRIVE_1 = 22,
    
    RIGHT_DRIVE_0 = 30,
    RIGHT_DRIVE_MAIN = 31,
    RIGHT_DRIVE_1 = 32;

    public final static String
    LOG_FILE_PATH = "/home/admin/";
}
