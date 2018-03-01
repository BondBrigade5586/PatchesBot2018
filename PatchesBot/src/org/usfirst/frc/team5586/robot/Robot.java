package org.usfirst.frc.team5586.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends IterativeRobot {
	
    private static final int LEFT_FRONT_TALON_PORT = 1;
    private static final int LEFT_BACK_TALON_PORT = 3;
    private static final int RIGHT_FRONT_TALON_PORT = 2;
    private static final int RIGHT_BACK_TALON_PORT = 4;
    
    private static final int SHIFTER_SERVO_PORT = 5;

	private DifferentialDrive differentialDrive;
	private Joystick joyStick = new Joystick(0);
	private Timer timer = new Timer();
	
	private Talon leftFront;
	private Talon leftBack;
	private Talon rightFront;
	private Talon rightBack;
	
	private SpeedControllerGroup leftDrive;
	private SpeedControllerGroup rightDrive;

	private Servo shifterServo;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		// Map motors & servos
	    leftFront = new Talon(LEFT_FRONT_TALON_PORT);
		leftBack = new Talon(LEFT_BACK_TALON_PORT);
	    rightFront = new Talon(RIGHT_FRONT_TALON_PORT);
		rightBack = new Talon(RIGHT_BACK_TALON_PORT);
        shifterServo = new Servo(SHIFTER_SERVO_PORT);
	    
        // Setup SppedController groups
        leftDrive = new SpeedControllerGroup(leftFront, leftBack);
        rightDrive = new SpeedControllerGroup(rightFront, rightBack);

        // Instantiate drive
        differentialDrive = new DifferentialDrive(leftDrive, rightDrive);
        
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		// Drive for 5 seconds
		if (timer.get() < 5.0) {
			differentialDrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
		} else {
			differentialDrive.stopMotor(); // stop robot
		}
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		
		// Drive the robot
		differentialDrive.arcadeDrive(joyStick.getY()*-1, joyStick.getX());
		
		// Servo control
		if (joyStick.getRawButton(3)) {
			shifterServo.set(0);
		}
		if (joyStick.getRawButton(4)) {
			shifterServo.set(0.5);
		}
		
		// Next...

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
