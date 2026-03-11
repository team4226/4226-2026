package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake;

public class intakeReverse extends Command {
    
    public intake m_Intake;

    public intakeReverse(intake subsystem){
        
        m_Intake = subsystem;
        addRequirements(m_Intake);
    }

    @Override
    public void execute() {
        m_Intake.runIntake(0.7);
    }

    @Override
    public void end(boolean interrupted) {
        m_Intake.runIntake(0);
    }

    public boolean isFinished(){
        return false;
    }

}
