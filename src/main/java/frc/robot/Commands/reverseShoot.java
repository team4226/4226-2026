package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.flywheel;

public class reverseShoot extends Command {
    
    public flywheel m_Flywheel;

    public reverseShoot(flywheel subsystem){
        
        m_Flywheel = subsystem;
        addRequirements(m_Flywheel);
    }

    @Override
    public void execute() {
        m_Flywheel.shoot(-0.7);
    }

    @Override
    public void end(boolean interrupted) {
        m_Flywheel.stop(0);
    }

    public boolean isFinished(){
        return false;
    }

}
