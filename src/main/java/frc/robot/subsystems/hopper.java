package frc.robot.subsystems;

import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class hopper extends SubsystemBase {

    public SparkMax m_motor;
    public SparkClosedLoopController m_controller;
    public SparkMaxConfig m_config;


    public hopper(){
        m_motor = new SparkMax(Constants.MotorIDS.hopper, MotorType.kBrushless);
        m_controller = m_motor.getClosedLoopController();
        m_config = new SparkMaxConfig();


        m_config.idleMode(IdleMode.kCoast);

        m_config.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            // Set PID values for position control. We don't need to pass a closed
            // loop slot, as it will default to slot 0.
            .p(Constants.hopperConst.kP)
            .i(Constants.hopperConst.kI)
            .d(Constants.hopperConst.kD)
            .outputRange(Constants.minMaxOutputConstants.kMinOutput, Constants.minMaxOutputConstants.kMaxOutput, ClosedLoopSlot.kSlot1);
        m_config.closedLoop.maxMotion
            // Set MAXMotion parameters for position control. We don't need to pass
            // a closed loop slot, as it will default to slot 0.
            .cruiseVelocity(Constants.hopperConst.kMaxMotionVelocity)
            .maxAcceleration(Constants.hopperConst.kMaxMotionAcceleration)
            .allowedProfileError(1);

        //m_config.softLimit.forwardSoftLimit(4.21).reverseSoftLimit(-7.76).forwardSoftLimitEnabled(true).reverseSoftLimitEnabled(true);




        try{
        m_motor.configure(m_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        System.out.println("!!Successfully configured climber motor!!");
        }
        catch (Exception e1){
        System.err.println("Failed to apply climber motor configurations: "+e1.toString());
        DriverStation.reportWarning("Failed to apply climber motor configuration",true);
        }
    }

    public void runHopper(double speed){
        m_motor.set(speed);
    }



}
