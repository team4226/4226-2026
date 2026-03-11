package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.uptake;

public class uptakeReverse extends Command {
    
    public uptake m_Uptake;

    public uptakeReverse(uptake subsystem){
        
        m_Uptake = subsystem;
        addRequirements(m_Uptake);
    }

    @Override
    public void execute() {
        m_Uptake.runUptake(1);
    }

    @Override
    public void end(boolean interrupted) {
        m_Uptake.runUptake(0);
    }

    public boolean isFinished(){
        return false;
    }

}
