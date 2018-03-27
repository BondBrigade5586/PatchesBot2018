package org.usfirst.frc.team5586.robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Robot extends IterativeRobot {
 
    private static final int LEFT_FRONT_TALON_PORT = 0;
    private static final int LEFT_BACK_TALON_PORT = 0;
    private static final int RIGHT_FRONT_TALON_PORT = 1;
    private static final int RIGHT_BACK_TALON_PORT = 1;
    
    private static final int SHIFTER_SERVO_PORT = 2;
    private static final int RT_RT_ARM_PORT = 4;
    
    private static final int GRABBER_LEFT_PORT = 5;
    private static final int GRABBER_RIGHT_PORT = 6;
    
    private static final int LIFT_1_PORT = 7;
    private static final int LIFT_2_PORT = 8;
    private static final int LIFT_3_PORT = 9;
 /*
     * Drive control handled on Joystick
     * 
     * Lifter controls on XBox 
     * ---------------------------
     * GearBox Clutch   A & B
     * Lifter           Stick
     * RtRt             X & Y
     * Grabber          LB & RB
     */
    
// private Joystick joyStick = new Joystick(0);
 private XboxController xbox = new XboxController(0);
 private XboxController xbox2 = new XboxController(1);
 
 private Timer timer = new Timer();
 String gameData;
 String position; 
 double autoDelay;
 double autoDrive;
 
 /*
  * Drive-train Motors
  */
 private DifferentialDrive differentialDrive;
 private Talon leftMotors;
// private Talon leftBackMotor;
 private Talon rightMotors;
// private Talon rightBackMotor;
 private SpeedControllerGroup leftDrive;
 private SpeedControllerGroup rightDrive;
 
 /*
  * Lifter/Manipulator motors 
  */
 private Victor liftMotor1;
 private Victor liftMotor2;
 private Victor liftMotor3;
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
  leftMotors = new Talon(LEFT_FRONT_TALON_PORT);
//  leftBackMotor = new Talon(LEFT_BACK_TALON_PORT);
     rightMotors = new Talon(RIGHT_FRONT_TALON_PORT);
//  rightBackMotor = new Talon(RIGHT_BACK_TALON_PORT);
  
        leftDrive = new SpeedControllerGroup(leftMotors);
        rightDrive = new SpeedControllerGroup(rightMotors);
        differentialDrive = new DifferentialDrive(leftDrive, rightDrive);
  
        // *************************
  // Lifter/Manipulator motors
        // *************************
        
        liftMotor1 = new Victor(LIFT_1_PORT);
        liftMotor2 = new Victor(LIFT_2_PORT);
        liftMotor3 = new Victor(LIFT_3_PORT);
        liftDrive = new SpeedControllerGroup(liftMotor1, liftMotor2, liftMotor3);
        grabberMotorLeft = new Talon(GRABBER_LEFT_PORT);
        grabberMotorRight = new Talon(GRABBER_RIGHT_PORT);
       
        gearboxClutchServo = new Servo(SHIFTER_SERVO_PORT);
//        rtRtArmServo = new Servo(RT_RT_ARM_PORT);
        
 }
 /*
  * This function is run once each time the robot enters autonomous mode.
  */
 @Override
 public void autonomousInit() {
  timer.reset();
  timer.start();
  gameData = DriverStation.getInstance().getGameSpecificMessage();
  position = SmartDashboard.getString("POSITION", "L");
  autoDelay = SmartDashboard.getNumber("AUTO_DELAY", 0);
  autoDrive = SmartDashboard.getNumber("AUTO_DRIVE", 2.5);
 }
 /**
  * This function is called periodically during autonomous.
  */
 @Override
 public void autonomousPeriodic() {
  if (timer.get() < autoDelay) {
   
  } else if (timer.get() < autoDelay + autoDrive + 3.5 && timer.get() > autoDelay) {
   autonomousDrive(); 
  } else {
   differentialDrive.stopMotor(); // stop robot
  }
 }
 
// public void autonomousDrive() {
//  if(gameData.length() > 0) {
//   if(gameData.charAt(0) == 'L') {
//    if(position.equalsIgnoreCase("L")) {
//     if(timer.get() < autoDelay + autoDrive) {
//      differentialDrive.tankDrive(0.6, 0.6); //drive forward at 60% for 2.5
//     }
//     if(timer.get() > autoDelay + autoDrive && timer.get() < autoDelay + autoDrive + 2.5) {
//      liftDrive.set(0.7); //lift up
//     }
//     if(timer.get() > autoDelay + autoDrive + 2.5 && timer.get() < autoDelay + autoDrive + 3.5) {
//      grabberMotorLeft.set(0.7);
//      grabberMotorRight.set(0.7);
//     }
//    } else {
//     differentialDrive.tankDrive(0.6, 0.6);
//    }
//   } else if(gameData.charAt(0) == 'R') {
//    if(position.equalsIgnoreCase("R")) {
//     if(timer.get() < autoDelay + autoDrive) {
//      differentialDrive.tankDrive(0.6, 0.6); //drive forward at 60% for 2.5
//     }
//     if(timer.get() > autoDelay + autoDrive && timer.get() < autoDelay + autoDrive + 2.5) {
//      liftDrive.set(0.7); //lift up
//     }
//     if(timer.get() > autoDelay + autoDrive + 2.5 && timer.get() < autoDelay + autoDrive + 3.5) {
//      grabberMotorLeft.set(0.7);
//      grabberMotorRight.set(0.7);
//     }
//    } else {
//     differentialDrive.tankDrive(0.6, 0.6);
//    }
//   }
//  }
// }
 
 public void autonomousDrive() { //better version of previous autonomousDrive
  if(gameData.length() > 0) {
   if(gameData.charAt(0) == 'L' && position.equalsIgnoreCase("L")) {
    if(timer.get() > autoDelay + autoDrive && timer.get() < autoDelay + autoDrive + 2.5) {
     liftDrive.set(0.7); //lift up
    }
    if(timer.get() > autoDelay + autoDrive + 2.5 && timer.get() < autoDelay + autoDrive + 3.5) {
     grabberMotorLeft.set(0.7); //spit out
     grabberMotorRight.set(0.7);
    }
   } else if(gameData.charAt(0) == 'R' && position.equalsIgnoreCase("R")) {
    if(timer.get() > autoDelay + autoDrive && timer.get() < autoDelay + autoDrive + 2.5) {
     liftDrive.set(0.7); //lift up
    }
    if(timer.get() > autoDelay + autoDrive + 2.5 && timer.get() < autoDelay + autoDrive + 3.5) {
     grabberMotorLeft.set(0.7); //spit out
     grabberMotorRight.set(0.7);
    }
   } else {
    
   }
  }
 }
 /*   
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
  differentialDrive.tankDrive(-xbox2.getRawAxis(1), -xbox2.getRawAxis(5));
  
        // *************************
  // Lifter/Manipulator motors
        // *************************
  // Motors - Up/Down
  liftDrive.set(-xbox.getRawAxis(1) * 0.7);
  
  // GearBox clutch open
  if (xbox.getAButton()) {
   gearboxClutchServo.set(0);
  }
  // GearBox clutch close
  if (xbox.getBButton()) {
   gearboxClutchServo.set(0.5);
  }
  
//  // RtRt open
//  if (xbox.getXButton()) {
//   rtRtArmServo.set(0.5);
//  }
//  // RtRt close
//  if (xbox.getYButton()) {
//   rtRtArmServo.set(0.0);
//  }
  
  // Crate pick up
  if (xbox.getBumper(Hand.kLeft)) {
   grabberMotorLeft.set(0.7);
   grabberMotorRight.set(0.7);
  } else if (xbox.getBumper(Hand.kRight)) {
   grabberMotorLeft.set(-0.7);
   grabberMotorRight.set(-0.7);
  } else {
   grabberMotorLeft.set(0);
   grabberMotorRight.set(0);
  }
  // Crate release
//  if (xbox.getBumper(Hand.kRight)) {
//   grabberMotorLeft.set(-0.1);
//   grabberMotorRight.set(0.1);
//  } else {
//   grabberMotorLeft.set(0);
//   grabberMotorRight.set(0);
//  }
 }
 /**
  * This function is called periodically during test mode.
  */
 @Override
 public void testPeriodic() {
 }
}

	Virus-free. www.avast.com
