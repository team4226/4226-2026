package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.subsystems.climber;
import frc.robot.subsystems.flywheel;
import frc.robot.subsystems.turret;

public class rightShoot extends Command {

    public flywheel m_Flywheel;
    public turret m_Turret;

    public rightShoot(flywheel flywheel, turret turret){

        m_Flywheel = flywheel;
        m_Turret = turret;
        addRequirements(m_Flywheel);
    }

    @Override
    public void execute() {
        m_Turret.setTurretPosition(Constants.turretConst.rightPos);
        m_Flywheel.flywheelSpeed(2800);
    }

    @Override
    public void end(boolean interrupted) {
        m_Flywheel.flywheelSpeed(0);
    }

    public boolean isFinished(){
        return false;
    }

}
