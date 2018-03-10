package org.usfirst.frc.team5586.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends IterativeRobot {
	
    private static final int LEFT_FRONT_TALON_PORT = 1;
    private static final int LEFT_BACK_TALON_PORT = 3;
    private static final int RIGHT_FRONT_TALON_PORT = 2;
    private static final int RIGHT_BACK_TALON_PORT = 4;
    
    private static final int SHIFTER_SERVO_PORT = 5;
    private static final int RT_RT_ARM_PORT = 5;
    
    private static final int GRABBER_LEFT_PORT = 6;
    private static final int GRABBER_RIGHT_PORT = 7;
    
    private static final int LIFT_1_PORT = 8;
    private static final int LIFT_2_PORT = 9;
    private static final int LIFT_3_PORT = 0;

	/*
     * Drive control handled on Joystick
     * 
     * XBox Lifter Controls
     * ---------------------------
     * GearBox Clutch   A & B
     * Lifter           Stick
     * RtRt             X & Y
     * Grabber          LB & RB
     */
    
	private Joystick joyStick = new Joystick(0);
	private XboxController xbox = new XboxController(1);
	
	private Timer timer = new Timer();
	
	// *****************
	// Drive base motors	
	// *****************
	private DifferentialDrive differentialDrive;
	private Talon leftFrontMotor;
	private Talon leftBackMotor;
	private Talon rightFrontMotor;
	private Talon rightBackMotor;
	private SpeedControllerGroup leftDrive;
	private SpeedControllerGroup rightDrive;
	
    // *************************
	// Lifter/Manipulator motors
    // *************************
	private Talon liftMotor1;
	private Talon liftMotor2;
	private Talon liftMotor3;
	private SpeedControllerGroup liftDrive;
	private Talon grabberMotorLeft;
	private Talon grabberMotorRight;
	private Servo gearboxClutchServo;
	private Servo rtRtArmServo;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		// *****************
		// Drive base motors
		// *****************

		leftFrontMotor = new Talon(LEFT_FRONT_TALON_PORT);
		leftBackMotor = new Talon(LEFT_BACK_TALON_PORT);
	    rightFrontMotor = new Talon(RIGHT_FRONT_TALON_PORT);
		rightBackMotor = new Talon(RIGHT_BACK_TALON_PORT);
		
        leftDrive = new SpeedControllerGroup(leftFrontMotor, leftBackMotor);
        rightDrive = new SpeedControllerGroup(rightFrontMotor, rightBackMotor);

        differentialDrive = new DifferentialDrive(leftDrive, rightDrive);
		
        // *************************
		// Lifter/Manipulator motors
        // *************************
        
        liftMotor1 = new Talon(LIFT_1_PORT);
        liftMotor2 = new Talon(LIFT_2_PORT);
        liftMotor3 = new Talon(LIFT_3_PORT);
        liftDrive = new SpeedControllerGroup(liftMotor1, liftMotor2, liftMotor3);

        grabberMotorLeft = new Talon(GRABBER_LEFT_PORT);
        grabberMotorRight = new Talon(GRABBER_RIGHT_PORT);
        
        gearboxClutchServo = new Servo(SHIFTER_SERVO_PORT);
        rtRtArmServo = new Servo(RT_RT_ARM_PORT);
        
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
		
		// *****************
		// Drive base motors
		// *****************
		
		// Drive the robot
		differentialDrive.arcadeDrive(joyStick.getY()*-1, joyStick.getX());
		
        // *************************
		// Lifter/Manipulator motors
        // *************************

		// GearBox clutch open
		if (xbox.getAButton()) {
			gearboxClutchServo.set(0);
		}
		// GearBox clutch close
		if (xbox.getBButton()) {
			gearboxClutchServo.set(0.5);
		}
		
		// Motors - Up/Down
		liftDrive.set(xbox.getRawAxis(1));
		
		// RtRt open
		if (xbox.getXButton()) {
			rtRtArmServo.set(0.5);
		}
		// RtRt close
		if (xbox.getYButton()) {
			rtRtArmServo.set(0.0);
		}
		
		// Crate pick up
		if (xbox.getBumper(Hand.kLeft)) {
			grabberMotorLeft.set(1.0);
			grabberMotorRight.set(-1.0);
		}		
		// Crate release
		if (xbox.getBumper(Hand.kRight)) {
			grabberMotorLeft.set(-1.0);
			grabberMotorRight.set(1.0);
		}

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
