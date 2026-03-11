package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climber;

public class reverseClimb extends Command {

    public climber m_climber;

    public reverseClimb(climber subsystem){
        
        m_climber = subsystem;
        addRequirements(m_climber);
    }

    @Override
    public void execute() {
        m_climber.runClimber(-0.7);
    }

    @Override
    public void end(boolean interrupted) {
        m_climber.runClimber(0);
    }

    public boolean isFinished(){
        return false;
    }

}
