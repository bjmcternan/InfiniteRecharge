package frc.robot.classes;

import frc.robot.subsystems.BallPickup;

public class AutonomousDisableBallPickup implements SPIKE293AutonomousCommand 
{
  private BallPickup m_ballPickup;

  public AutonomousDisableBallPickup(BallPickup ballPickup)
  {
    m_ballPickup = ballPickup;
  }

  @Override
  public void execute() 
  {
    //Disable the gecko
    m_ballPickup.geckoToggle(false);
  }

  @Override
  public boolean isDone() {
    return true;
  }
}
