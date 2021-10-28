// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  Joystick joystick = new Joystick(0);
  // drive motors
  /*
   * Hold up, these are not int, doubles,ect. There Victor, Compressor, and
   * Solenoid Dont worry they work the same except these dont hold values of
   * numbers or strings, but are motor controllers. Victor is a kind of motor
   * controller, and there are many kinds, so the variable type depends on what
   * you are using. After naming it the variable, continue with the syntax and in
   * the parenthesis the numerical value is what "port" the motor controller is
   * plugged into. If its on pwm for theroborio use the number it correspons to.
   * If its on CAN you will need to use Pheonix turner to determine this number.
   * The same thing applies to solenoids and Compressor, but these numerical
   * values, like the roborio are determined through the labled ports on the
   * pnuematic contol module
   */
  Victor LF = new Victor(1);
  Victor LB = new Victor(3);
  Victor RF = new Victor(5);
  Victor RB = new Victor(7);
  Victor flywheel = new Victor(0);
  // compressor, solenoids, cylinders
  Compressor compress = new Compressor(0);
  Solenoid rocketone = new Solenoid(4);
  Solenoid rockettwo = new Solenoid(5);
  Solenoid spider = new Solenoid(2);

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // drive
    final double leftspeed = -0.9 * joystick.getRawAxis(1);
   final double rightspeed = 0.9*joystick.getRawAxis(5); 
   //left drive
   LF.set(leftspeed); 
   LB.set(leftspeed);  
   //rigt drive
   RF.set(rightspeed); 
   RB.set(rightspeed);    

   //----------------------------------------------------------------------------------------------------- 
   /* Now its time to program the solenoids. They follow similar rules to motors. However since these are 
   being contolled by buttons not joysticks, we will be using aan if else staement. A solenoid can only be 
   on or off, so it will hold a bolean value. If its true the solenoid will be triggered, if false, it will
   not release air. The if stement looks for the condition of the joystick's button number 4 to be pressed
   (Botton values and joysticks are determined in Driver Station) until rocketone realeases air. else holds
   code that does not realse air. That means the condition for air to be realed is for Button 4 to be true 
   Look at the code for further clarity*/  

    // rocket1
   if(joystick.getRawButton(4)){ 
    rocketone.set(true); 
  } 
  else{ 
    rocketone.set(false); 
  }   
//------------------------------------------------------------------------------------------------------------ 
//see above
  //rocket2
  if(joystick.getRawButton(3)){ 
      rockettwo.set(true);
  }  
  else{ 
      rockettwo.set(false);  
  }
  //------------------------------------------------------------------------------------------------------------
  //see above
    if (joystick.getRawButton(1)) {
    spider.set(true); 
  } 
  else{ 
    spider.set(false); 
  }    
//----------------------------------------------------------------------------------------------------------------- 
/*This is very similar to the solenoid example. Except this time we have an added else if. That is because a motor 
does not hold a bolean value or even int as it hold value from -1 to 1. That emasn 0.9 is a possibility. This 
also ppens ths possibilty of the usage of several buttons to spin the motor at various speeds, either fowards or 
backwards. This if else statemnt is common among many usages of motors. Take a look at the code for clarity.*/
//Fly wheel
  if(joystick.getRawButton(5)){ 
     flywheel.set(1); 
  } 
  else if(joystick.getRawButton(6)) { 
  flywheel.set(-1);   
  }  
  else{ 
   flywheel.set(0); 
  } 
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
