package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.turret;

public class manuelTurret extends Command {
    
    public turret m_turret;
    public CommandXboxController m_controller;

    public manuelTurret(turret subsystem, CommandXboxController controller){
        m_turret = subsystem;
        m_controller = controller;
    }

    @Override
    public void execute() {

        if (Math.abs(m_controller.getRightX()) > 0.01){

            m_turret.manualTurret(m_controller.getRightX());

        }

    }

    @Override
    public void end(boolean interrupted) {
     //   m_turret.manualTurret(0);
    }

    public boolean isFinished(){
        return false;
    }
















}
