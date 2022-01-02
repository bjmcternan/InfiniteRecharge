package frc.robot.classes;

import static frc.robot.Constants.AutonomousCommandConstants.*;
import frc.robot.subsystems.Launcher;

public class AutonomousEnableLauncher implements SPIKE293AutonomousCommand 
{
  private Launcher m_launcher;

  public AutonomousEnableLauncher(Launcher launcher)
  {
    m_launcher = launcher;
  }

  @Override
  public void execute() 
  {
    //Turn on launcher
    m_launcher.setRpm(AUTO_LAUNCHER_RPM);
  }

  @Override
  public boolean isDone() {
    return true;
  }
}
