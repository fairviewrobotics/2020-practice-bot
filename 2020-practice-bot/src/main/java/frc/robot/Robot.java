/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */

  XboxController controller;
  DifferentialDrive drivetrain;
  WPI_VictorSPX climber0;
  WPI_VictorSPX climber1;
  

  @Override
  public void robotInit() {
    controller = new XboxController(0);
    drivetrain = new DifferentialDrive(
      new SpeedControllerGroup(new WPI_VictorSPX(4), new WPI_VictorSPX(11)),
      new SpeedControllerGroup(new WPI_VictorSPX(2), new WPI_VictorSPX(1))
    );
    climber0 = new WPI_VictorSPX(3);
    climber1 = new WPI_VictorSPX(5);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */



  @Override
  public void autonomousInit() {
  
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }

  /* translate a joystick position to a turn speed */
    public double joystickToSpeed(double joystickPos) {
        double gainSwitchThresh = 0.15;
        double gainSwitchPos = 0.25;
        if (joystickPos < gainSwitchThresh) {
            return joystickPos * (gainSwitchPos / gainSwitchThresh);
        } else {
            return ((joystickPos - gainSwitchThresh) * (joystickPos - gainSwitchThresh) * (joystickPos - gainSwitchThresh) * (joystickPos - gainSwitchThresh) * (joystickPos - gainSwitchThresh) * 0.75) + gainSwitchPos;
        }
    }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    double xSpeed = controller.getX(Hand.kRight);
    double ySpeed = controller.getY(Hand.kLeft);
    drivetrain.arcadeDrive(ySpeed * Math.abs(ySpeed), joystickToSpeed(xSpeed), false);
    climber0.set(controller.getY(Hand.kRight));
    if(controller.getTriggerAxis(Hand.kLeft) > 0.2) {
      climber1.set(controller.getTriggerAxis(Hand.kLeft) * 0.75);
    } else if (controller.getTriggerAxis(Hand.kRight) > 0.2) {
    climber1.set(-controller.getTriggerAxis(Hand.kRight) * 0.75);
  } else {
      climber1.set(0.0);
    }
}

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
