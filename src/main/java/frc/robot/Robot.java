// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.HootAutoReplay;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.LimelightHelpers.LimelightResults;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.turret;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;
    private final RobotContainer m_robotContainer;
    private final Field2d m_field = new Field2d();

    
    

    

    /* log and replay timestamp and joystick data */
    private final HootAutoReplay m_timeAndJoystickReplay = new HootAutoReplay()
        .withTimestampReplay()
        .withJoystickReplay();

    private final boolean kUseLimelight = true;

    
    public Robot() {
        m_robotContainer = new RobotContainer();
        SmartDashboard.putData("Field", m_field);
    }

    @Override
    public void robotInit(){
            LimelightHelpers.SetIMUMode("limelight", 0);
            LimelightHelpers.setCameraPose_RobotSpace("limelight",
            -0.4699,    // Forward offset (meters)
            0.2032,    // Side offset (meters)
            0.381,    // Height offset (meters)
            0.0,    // Roll (degrees)
            15.0,   // Pitch (degrees)
            180.0     // Yaw (degrees)
        );
    }

    @Override
    public void robotPeriodic() {
        m_timeAndJoystickReplay.update();
        CommandScheduler.getInstance().run();

        if (kUseLimelight) {
        var driveState = m_robotContainer.drivetrain.getState();
        double headingDeg = driveState.Pose.getRotation().getDegrees();
        double omegaRps = Units.radiansToRotations(driveState.Speeds.omegaRadiansPerSecond);

        LimelightHelpers.SetRobotOrientation("limelight", headingDeg, 0, 0, 0, 0, 0);
        var llMeasurement = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight");
        m_robotContainer.drivetrain.setVisionMeasurementStdDevs(VecBuilder.fill(.7,.7,9999999));
        if (llMeasurement != null && llMeasurement.tagCount > 0 && Math.abs(omegaRps) < 2.0) {
            m_robotContainer.drivetrain.addVisionMeasurement(llMeasurement.pose, llMeasurement.timestampSeconds);
            }
        }

        m_field.setRobotPose(m_robotContainer.drivetrain.getState().Pose);   

    }





    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void disabledExit() {}

    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().schedule(m_autonomousCommand);
        }
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void autonomousExit() {}

    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().cancel(m_autonomousCommand);
        }
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void teleopExit() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void testExit() {}

    @Override
    public void simulationPeriodic() {}
}
