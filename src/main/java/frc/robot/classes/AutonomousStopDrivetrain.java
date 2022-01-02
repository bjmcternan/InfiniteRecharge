package frc.robot.classes;

import frc.robot.subsystems.Drivetrain;

public class AutonomousStopDrivetrain implements SPIKE293AutonomousCommand 
{
  private Drivetrain m_drivetrain;

  public AutonomousStopDrivetrain(Drivetrain drivetrain)
  {
    m_drivetrain = drivetrain;
  }

  @Override
  public void execute() 
  {
    //Turn off drivetrain motors
    m_drivetrain.stop();
  }

  @Override
  public boolean isDone() {
    return true;
  }
}
