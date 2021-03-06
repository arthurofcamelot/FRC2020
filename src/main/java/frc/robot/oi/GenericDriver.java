package frc.robot.oi;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.logging.LoggerRelations;
import frc.robot.logging.SyncLogger;
import frc.robot.utilities.functionalinterfaces.AxisGetter;
import frc.robot.utilities.functionalinterfaces.ButtonGetter;

public abstract class GenericDriver {

	private DriverStation driverStation = DriverStation.getInstance();

	protected int port = 0;
	protected SyncLogger logger = null;

	protected GenericDriver(int port, SyncLogger logger) {
		this.port = port;
		this.logger = logger;
	}

	/**
     * Gets analog axis
	 * 
     * @param axis the axis number
     * @return the axis value
     */
    protected double getRawAxis(int axis) {
        return driverStation.getStickAxis(port, axis);
    }

    /**
     * Gets digital output
	 * 
     * @param button the button number
     * @return whether the output is on or off
     */
    protected boolean getRawButton(int button) {
        return driverStation.getStickButton(port, button);
	}

	/**
	 * Gets POV for an XBox controller (In this class because it requires DriverStation)
	 * 
	 * @return the POV
	 */
	protected int getPOV() {
		return driverStation.getStickPOV(port, 0);
	}
	
	/**
	 * Gets a getter function for a digital output
	 * 
	 * @param button the button number
	 * @return the getter function
	 */
	protected ButtonGetter getButtonGetter(int button) {
		return () -> getRawButton(button);
	}

	/**
	 * Gets a getter function for an analog output
	 * 
	 * @param axis the axis number
	 * @return the getter function
	 */
	protected AxisGetter getAxisGetter(int axis) {
		return () -> getRawAxis(axis);
	}

	/**
	 * Creates a logger button using a ButtonGetter and a log reference. If a logger has been defined,
	 * the button wil be automatically registered to the logger
	 * 
	 * @param getter the getter for the button
	 * @param logReference the desired log reference for the button
	 * @return the generated LoggerButton
	 */
	protected LoggerButton generateLoggerButton(ButtonGetter getter, LoggerRelations logReference) {
		if (logger != null) {
			return new LoggerButton(getter, logReference, logger);
		}

		return new LoggerButton(getter, logReference);
	}

	/**
	 * Creates a logger button using a port and a log reference. The getButtonGetter() method is automatically 
	 * used to get a ButtonGetter. If a logger has been defined, the button wil be automatically registered 
	 * to the logger
	 * 
	 * @param port the button port
	 * @param logReference the desired log reference for the button
	 * @return the generated LoggerButton
	 */
	protected LoggerButton generateLoggerButton(int port, LoggerRelations logReference) {
		if (logger != null) {
			return new LoggerButton(getButtonGetter(port), logReference, logger);
		}

		return new LoggerButton(getButtonGetter(port), logReference);
	}

	/**
	 * Creates a logger axis using an AxisGetter and a log reference. If a logger has been defined,
	 * the axis wil be automatically registered to the logger
	 * 
	 * @param getter the getter for the axis
	 * @param logReference the desired log reference for the button
	 * @return the generated axis
	 */
	protected LoggerAxis generateLoggerAxis(AxisGetter getter, LoggerRelations logReference) {
		if (logger != null) {
			return new LoggerAxis(getter, logReference, logger);
		}

		return new LoggerAxis(getter, logReference);
	}

	/**
	 * Creates a logger axis using an port and a log reference. The getAxisGetter() method is 
	 * automatically used to get an AxisGetter. If a logger has been defined, the axis wil be 
	 * automatically registered to the logger
	 * 
	 * @param port the axis port
	 * @param logReference the desired log reference for the button
	 * @return the generated axis
	 */
	protected LoggerAxis generateLoggerAxis(int port, LoggerRelations logReference) {
		if (logger != null) {
			return new LoggerAxis(getAxisGetter(port), logReference, logger);
		}

		return new LoggerAxis(getAxisGetter(port), logReference, logger);
	}
}