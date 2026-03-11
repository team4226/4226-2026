package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.uptake2;

public class uptake2Reverse extends Command {
    
    public uptake2 m_Uptake2;

    public uptake2Reverse(uptake2 subsystem){
        
        m_Uptake2 = subsystem;
        addRequirements(m_Uptake2);
    }

    @Override
    public void execute() {
        m_Uptake2.runUptake(1);
    }

    @Override
    public void end(boolean interrupted) {
        m_Uptake2.runUptake(0);
    }

    public boolean isFinished(){
        return false;
    }

}
