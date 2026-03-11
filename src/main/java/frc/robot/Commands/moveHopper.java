package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.hopperMover;

public class moveHopper extends Command {

    public hopperMover m_mover;
    public CommandXboxController m_controller;

    public moveHopper(hopperMover subsystem, CommandXboxController controller){
        m_mover = subsystem;
        m_controller = controller;
    }

    @Override
    public void execute() {

        if (Math.abs(m_controller.getLeftY()) > 0.01){
                m_mover.moveHopper(m_controller.getLeftY());
        }

    }

    @Override
    public void end(boolean interrupted) {
        m_mover.moveHopper(0);
    }

    public boolean isFinished(){
        return false;
    }    
    
}
