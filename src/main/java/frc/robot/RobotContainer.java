// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;
import java.util.function.Supplier;

import javax.xml.crypto.dsig.spec.HMACParameterSpec;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.jni.SwerveJNI.DriveState;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Commands.autoSpeed;
import frc.robot.Commands.climb;
import frc.robot.Commands.goToAngle;
import frc.robot.Commands.hopperForward;
import frc.robot.Commands.hopperReverse;
import frc.robot.Commands.intakeForward;
import frc.robot.Commands.intakeReverse;
import frc.robot.Commands.leftShoot;
import frc.robot.Commands.manuelTurret;
import frc.robot.Commands.midShoot;
import frc.robot.Commands.moveHopper;
import frc.robot.Commands.reverseClimb;
import frc.robot.Commands.reverseShoot;
import frc.robot.Commands.rightShoot;
import frc.robot.Commands.shoot;
import frc.robot.Commands.uptake2Forward;
import frc.robot.Commands.uptake2Reverse;
import frc.robot.Commands.uptakeForward;
import frc.robot.Commands.uptakeReverse;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.climber;
import frc.robot.subsystems.flywheel;
import frc.robot.subsystems.hopper;
import frc.robot.subsystems.hopperMover;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.turret;
import frc.robot.subsystems.uptake;
import frc.robot.subsystems.uptake2;


public class RobotContainer {
    private double MaxSpeed = .5 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    private hopper m_Hopper = new hopper();
    private uptake m_Uptake = new uptake();
    public intake m_Intake = new intake();
    private hopperMover m_mover = new hopperMover();
    private climber m_climber = new climber();
    private uptake2 m_Uptake2 = new uptake2();

    private final SendableChooser<Command> autoChooser;


    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController joystick = new CommandXboxController(0);
    private final CommandXboxController secondaryXbox = new CommandXboxController(1);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    private turret m_turret = new turret(drivetrain);
    private flywheel m_flywheel = new flywheel(drivetrain);


    
    public RobotContainer() {

    // For convenience a programmer could change this when going to competition.
    boolean isCompetition = true;

    // Build an auto chooser. This will use Commands.none() as the default option.
    // As an example, this will only show autos that start with "comp" while at
    // competition as defined by the programmer
    
    
    
    autoChooser = AutoBuilder.buildAutoChooser();


    NamedCommands.registerCommand("aim", new goToAngle(m_turret));
    NamedCommands.registerCommand("midshoot", new midShoot(m_flywheel, m_turret));
    NamedCommands.registerCommand("hopper", new hopperForward(m_Hopper));
    NamedCommands.registerCommand("uptake", new uptakeForward(m_Uptake));
    NamedCommands.registerCommand("uptake2", new uptake2Forward(m_Uptake2));
    NamedCommands.registerCommand("climb", new climb(m_climber));



    SmartDashboard.putData("Auto Chooser", autoChooser);



        configureBindings();
    }




    private void configureBindings() {

        //secondaryXbox.start().toggleOnTrue(new autoSpeed(m_flywheel, drivetrain.getState().Pose));
        //secondaryXbox.start().toggleOnTrue(new goToAngle(m_turret));
        

        secondaryXbox.leftTrigger().whileTrue(new hopperForward(m_Hopper));
        secondaryXbox.rightTrigger().whileTrue(new hopperReverse(m_Hopper));


        secondaryXbox.leftBumper().whileTrue(new uptakeForward(m_Uptake));
        secondaryXbox.rightBumper().whileTrue(new uptakeReverse(m_Uptake));

        secondaryXbox.leftBumper().whileTrue(new uptake2Forward(m_Uptake2));
        secondaryXbox.rightBumper().whileTrue(new uptake2Reverse(m_Uptake2));

        //secondaryXbox.povUp().whileTrue(new autoSpeed(m_flywheel));

        secondaryXbox.povUp().whileTrue(new intakeForward(m_Intake));
        secondaryXbox.povDown().whileTrue(new intakeReverse(m_Intake));

        secondaryXbox.leftStick().toggleOnTrue(new moveHopper(m_mover, secondaryXbox));


        secondaryXbox.b().whileTrue(new rightShoot(m_flywheel, m_turret));
        secondaryXbox.x().whileTrue(new leftShoot(m_flywheel, m_turret));
        secondaryXbox.a().whileTrue(new midShoot(m_flywheel, m_turret));
        secondaryXbox.y().toggleOnTrue(new goToAngle(m_turret));

        secondaryXbox.start().whileTrue(new climb(m_climber));
        secondaryXbox.back().whileTrue(new reverseClimb(m_climber));

        secondaryXbox.rightStick().toggleOnTrue(new manuelTurret(m_turret, joystick));

        joystick.rightBumper().whileTrue(new intakeForward(m_Intake));











        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {

        return autoChooser.getSelected();
    }



}
