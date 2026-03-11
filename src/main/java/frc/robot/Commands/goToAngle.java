package frc.robot.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.turret;

public class goToAngle extends Command {

    public turret m_Turret;
    public double angle;

    public goToAngle(turret subsystem){
        
        m_Turret = subsystem;
        addRequirements(m_Turret);
    }

    @Override
    public void execute() {
        m_Turret.setTurretPosition(m_Turret.getPosition());

    }

    @Override
    public void end(boolean interrupted) {
        m_Turret.stop();
    }

    public boolean isFinished(){
        return false;
    }
}
