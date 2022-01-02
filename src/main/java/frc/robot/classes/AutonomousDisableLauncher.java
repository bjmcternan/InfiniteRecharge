package frc.robot.classes;

import static frc.robot.Constants.AutonomousCommandConstants.*;
import frc.robot.subsystems.Launcher;

public class AutonomousDisableLauncher implements SPIKE293AutonomousCommand 
{
  private Launcher m_launcher;

  public AutonomousDisableLauncher(Launcher launcher)
  {
    m_launcher = launcher;
  }

  @Override
  public void execute() 
  {
    //Turn off launcher
    m_launcher.setRpm(0);
  }

  @Override
  public boolean isDone() {
    return true;
  }
}
