package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.hopper;

public class hopperForward extends Command {

    public hopper m_Hopper;

    public hopperForward(hopper subsystem){
        
        m_Hopper = subsystem;
        addRequirements(m_Hopper);
    }

    @Override
    public void execute() {
        m_Hopper.runHopper(.9);
    }

    @Override
    public void end(boolean interrupted) {
        m_Hopper.runHopper(0);
    }

    public boolean isFinished(){
        return false;
    }

}
