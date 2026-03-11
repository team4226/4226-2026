package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

public class Constants {
    
    public static final Pose2d blueTargetPose = new Pose2d(Units.inchesToMeters(182.11),Units.inchesToMeters(158.84),new Rotation2d());
    public static final Pose2d redTargetPose = new Pose2d(Units.inchesToMeters(469.11),Units.inchesToMeters(158.84),new Rotation2d());

    public static class minMaxOutputConstants {
        public static final double kMinOutput = -0.1;
        public static final double kMaxOutput = 0.1;
    }

    public static final class MotorIDS{
        public static final int turret = 15;
        public static final int flywheel = 11;
        public static final int hopper = 12;
        public static final int intake = 10;
        public static final int hopperMover = 9;
        public static final int uptake = 13;
        public static final int uptake2 = 17;
        public static final int climber = 14;
    }

    public static final class flywheelConst{
        public static final double angleDeg = 60.0;  // degrees
        public static final double g = 9.81;        //gravity
        public static final double radius = 0.051;  // wheel radius in meters

        public static final double kP = 0.0004;
        public static final double kI = 0.000001;
        public static final double kD = 0.000;
        public static final double kFF = 0;

        public static final double kMaxMotionVelocity = 100000;
        public static final double kMaxMotionAcceleration = 1000000;

        public static final double kMinOutput = -1;
        public static final double kMaxOutput = 1;
    }


    public static final class hopperConst{
        public static final double kP = 0;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kFF = 0;

        public static final double kMaxMotionVelocity = 100000;
        public static final double kMaxMotionAcceleration = 1000000;
    }

    public static final class uptakeConst{
        public static final double kP = 0.03;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kFF = 0;

        public static final double kMaxMotionVelocity = 100000;
        public static final double kMaxMotionAcceleration = 1000000;
    }

    public static final class intakeConst{
        public static final double kP = 1;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kFF = 0;

        public static final double kMaxMotionVelocity = 1;
        public static final double kMaxMotionAcceleration = 1;
    }

    public static final class turretConst{

        public static final double kP = 0.3;
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kFF = 0;


        public static final double kMaxMotionVelocity = 1;
        public static final double kMaxMotionAcceleration = 1;

        public static final double kforwardSoftLimit = 0;
        public static final double kreverseSoftLimit = 0;

        public static final double kMinOutput = -0.1;
        public static final double kMaxOutput = 0.1;

        public static final double rightPos = 4.21;
        public static final double midPos = 0;
        public static final double leftPos = -4.5;
     



    }







}
