package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.climber;
import frc.robot.subsystems.flywheel;
import frc.robot.subsystems.turret;

public class leftShoot extends Command {

    public flywheel m_Flywheel;
    public turret m_Turret;


    public leftShoot(flywheel flywheel, turret turret){

        m_Flywheel = flywheel;
        m_Turret = turret;
        addRequirements(m_Flywheel);
    }

    @Override
    public void execute() {
        m_Turret.setTurretPosition(m_Turret.wireUnstucker(Constants.turretConst.leftPos));
        m_Flywheel.flywheelSpeed(2800);
    }

    @Override
    public void end(boolean interrupted) {
        m_Flywheel.stop(0);;
    }

    public boolean isFinished(){
        return false;
    }

}
