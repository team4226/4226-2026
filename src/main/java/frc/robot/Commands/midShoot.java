package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climber;
import frc.robot.subsystems.flywheel;
import frc.robot.subsystems.turret;

public class midShoot extends Command {

    public flywheel m_Flywheel;
    public turret m_Turret;


    public midShoot(flywheel flywheel, turret turret){

        m_Flywheel = flywheel;
        m_Turret = turret;
        addRequirements(m_Flywheel);
    }

    @Override
    public void execute() {
        //m_Turret.setTurretPosition(0);
        m_Flywheel.flywheelSpeed(2550);
    }

    @Override
    public void end(boolean interrupted) {
        m_Flywheel.flywheelSpeed(0);
    }

    public boolean isFinished(){
        return false;
    }

}
