package frc.robot.Commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.flywheel;
import frc.robot.subsystems.turret;

public class autoSpeed extends Command {
    
    public flywheel m_flywheel;
    public RobotContainer m_Container;
    public Pose2d currentPose;
    public turret m_Turret;



    public autoSpeed(flywheel subsystem){
        
        m_flywheel = subsystem;
        addRequirements(m_flywheel);
    }

    @Override
    public void execute() {
        m_flywheel.flywheelSpeed(m_flywheel.getRPM());
    }

    @Override
    public void end(boolean interrupted) {
        m_flywheel.flywheelSpeed(0);
    }


    @Override

    public boolean isFinished(){
        return false;
    }






}
