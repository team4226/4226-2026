package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants;

public class flywheel extends SubsystemBase {
    private SparkMax m_motor;
    private SparkClosedLoopController m_controller;
    private SparkMaxConfig m_config;
    public CommandSwerveDrivetrain m_CommandSwerveDrivetrain;
    public double finalRPM;
    public double m_encoder;
    
    public flywheel(CommandSwerveDrivetrain drivetrain){
        m_motor = new SparkMax(Constants.MotorIDS.flywheel, MotorType.kBrushless);
        m_controller = m_motor.getClosedLoopController();
        m_config = new SparkMaxConfig();
        m_CommandSwerveDrivetrain = drivetrain;
        

        m_config.idleMode(IdleMode.kCoast);

        m_config.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            // Set PID values for position control. We don't need to pass a closed
            // loop slot, as it will default to slot 0.
            .p(Constants.flywheelConst.kP)
            .i(Constants.flywheelConst.kI)
            .d(Constants.flywheelConst.kD)
            .outputRange(Constants.flywheelConst.kMinOutput, Constants.flywheelConst.kMaxOutput, ClosedLoopSlot.kSlot1);
        m_config.closedLoop.maxMotion
            // Set MAXMotion parameters for position control. We don't need to pass
            // a closed loop slot, as it will default to slot 0.
            .cruiseVelocity(Constants.flywheelConst.kMaxMotionVelocity)
            .maxAcceleration(Constants.flywheelConst.kMaxMotionAcceleration)
            .allowedProfileError(1);
        try{
        m_motor.configure(m_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        System.out.println("!!Successfully configured climber motor!!");
        }
        catch (Exception e1){
        System.err.println("Failed to apply climber motor configurations: "+e1.toString());
        DriverStation.reportWarning("Failed to apply climber motor configuration",true);
        } 

    }


    @Override
    public void periodic(){

        Pose2d curretPose2d = m_CommandSwerveDrivetrain.getState().Pose;
 


        double range = curretPose2d.getTranslation().getDistance(Constants.redTargetPose.getTranslation());    // meters
        double angleDeg = Constants.flywheelConst.angleDeg;
        double g = Constants.flywheelConst.g;
        double radius = Constants.flywheelConst.radius;
        double heightMeters = 1.8288;

/* 
        double theta = Math.toRadians(angleDeg); // Launch angle in radians

        // Calculate initial velocity using the formula:
        // v0 = sqrt( g * x^2 / (2 * cos^2(theta) * (x * tan(theta) - y)) )
        double cosTheta = Math.cos(theta);
        double tanTheta = Math.tan(theta);

        double numerator = g * Math.pow(range, 2);
        double denominator = 2 * Math.pow(cosTheta, 2) * (range * tanTheta - heightMeters);

        double velocity = Math.sqrt(numerator / denominator);

*/
        double velocity = 13.4;

        double rpm = (velocity * 60.0) / (2 * Math.PI * radius);
        
        
        //900 * range;

        finalRPM = rpm;
        m_encoder = m_motor.getEncoder().getVelocity();

        SmartDashboard.putNumber("DistanceToTarget", range);
        SmartDashboard.putNumber("FlywheelRPM", rpm);
        SmartDashboard.putNumber("flywheelEncoder", m_encoder);
    }

    public double getRPM(){
        return finalRPM;
    }



    public void flywheelSpeed(double speed){
        m_controller.setSetpoint(speed, ControlType.kVelocity);
    }

    public void shoot(double speed){
        m_motor.set(speed);
    }

        public void stop(double speed){
        m_controller.setSetpoint(0, ControlType.kVelocity);
    }
}
