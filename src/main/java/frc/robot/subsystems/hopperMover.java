package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class hopperMover extends SubsystemBase {

    public SparkMax m_motor;

    
    public hopperMover(){
    
        m_motor = new SparkMax(Constants.MotorIDS.hopperMover, MotorType.kBrushless);


    }

    public void moveHopper(double speed){
        m_motor.set(speed);
    }





}
