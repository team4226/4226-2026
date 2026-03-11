package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LimelightHelpers.LimelightResults;
import frc.robot.RobotContainer;

public class turret extends SubsystemBase {
    public RobotContainer m_robotcontainer;
    public Pose2d robotPos;
    public SparkMax m_motor;
    public SparkClosedLoopController m_controller;
    public LimelightResults m_lime;
    private SparkMaxConfig m_config;
    public RelativeEncoder turretEncoder;
    public double angleToTarget;
    public double turretPositon;
    public double realTurretPosition;
    public CommandSwerveDrivetrain m_CommandSwerveDrivetrain;
    private SparkBaseConfig test;
    public double curretRotation;
    public double newPostion;
    public double theRealRotation;
    public Pose2d target;
    //182.11 x
    //158.84 y
    //72 z



    public turret(CommandSwerveDrivetrain drivetrain){
        m_motor = new SparkMax(Constants.MotorIDS.turret, MotorType.kBrushless);
        m_controller = m_motor.getClosedLoopController();
        m_config = new SparkMaxConfig();
        turretEncoder = m_motor.getEncoder();
        var alliance = DriverStation.getAlliance();

        
        m_CommandSwerveDrivetrain = drivetrain;

        if (alliance.get() == DriverStation.Alliance.Red){
            target = Constants.redTargetPose;
        } else {
            target = Constants.blueTargetPose;
        }





        m_config.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            // Set PID values for position control. We don't need to pass a closed
            // loop slot, as it will default to slot 0.
            .p(Constants.turretConst.kP)
            .i(Constants.turretConst.kI)
            .d(Constants.turretConst.kD)
            .outputRange(Constants.turretConst.kMinOutput, Constants.turretConst.kMaxOutput, ClosedLoopSlot.kSlot0);

        m_config.softLimit.forwardSoftLimit(6.21).reverseSoftLimit(-7.76).forwardSoftLimitEnabled(true).reverseSoftLimitEnabled(true);
            /* 
        m_config.closedLoop.maxMotion
            // Set MAXMotion parameters for position control. We don't need to pass
            // a closed loop slot, as it will default to slot 0.
            .cruiseVelocity(Constants.turretConst.kMaxMotionVelocity)
            .maxAcceleration(Constants.turretConst.kMaxMotionAcceleration)
            .allowedProfileError(1);
            */
        try{
        m_motor.configure(m_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        System.out.println("!!Successfully configured turret motor!!");
        }
        catch (Exception e1){
        System.err.println("Failed to apply turret motor configurations: "+e1.toString());
        DriverStation.reportWarning("Failed to apply turret motor configuration",true);
        }

    }
    

    public void setTurretPosition(double setpoint){
        m_controller.setSetpoint(setpoint, ControlType.kPosition);
    }

    public void stop(){
        m_motor.stopMotor();
    }









    @Override
    public void periodic() {
        
        // For all measurements, the grid is on the field with the X axis running the long way across the field, with the
        // the blue alliance on the lesser X side, and the red alliance on the greater X side. The Y axis runs the shot
        // way across the field. CCW rotation is positive.

        // The robot's pose is on a coordinate grid with (0,0) at the bottom left corner.
        Pose2d currentPose = m_CommandSwerveDrivetrain.getState().Pose;
        // Get the robot's pose relative to the target. This will be the pose of the robot on a grid with the hub in the
        // center at (0,0)
        var relativePose = currentPose.relativeTo(target);

        // Get the angle to the hub from the robot's position
        var angleToHub = Math.atan(relativePose.getY() / relativePose.getX());

        // Calculate the direction to turn the turret, considering the direction the robot's drivetrain is in
        double headingSetpoint = Units.radiansToDegrees(angleToHub) - currentPose.getRotation().getDegrees();

        theRealRotation = headingSetpoint / 25.19;

        curretRotation = currentPose.getRotation().getDegrees();

        //headingSetpoint = (headingSetpoint + 360) % 360;
        
        // Deal with quadrants where X is > 0 (the robot is to the right of the hub)
        /*if (relativePose.getX() > 0) {
        headingSetpoint += 180;
        }   //fix later
        
        // Deal with quadrants that result in a negative or out of range value

        */



        if (RobotBase.isReal()) {
            SmartDashboard.putNumber("turretEncoder",turretEncoder.getPosition());
            SmartDashboard.putNumber("turretPositoin", headingSetpoint);
            SmartDashboard.putNumber("RobotOrientation", curretRotation);
            SmartDashboard.putNumber("ExpectedPosition", newPostion);


            // SmartDashboard.putNumber("elevator motor current draw", getCurrentDraw());
        } else {
        }

    }


    public double wireUnstucker(double position){
        if(position > -7.79){
            newPostion = position + 14.29;
            return newPostion;
        } else if (position < 6.5){
            newPostion = position - 14.29;
            return newPostion;
        } else {
            return position;
        }
    }


    public double getRightPostion(){
        return Constants.turretConst.rightPos - ((curretRotation/360)*14.29);
    }

    public double getMidPostion(){
        return Constants.turretConst.midPos - ((curretRotation/360)*14.29);
    }

    public double getLeftPostion(){
        return Constants.turretConst.leftPos - ((curretRotation/360)*14.29);
    }





    public double getPosition(){
        return -theRealRotation;
    }

    public void manualTurret(double speed){
        m_motor.set(speed);
    }

}
