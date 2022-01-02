package frc.robot.classes;

import frc.robot.subsystems.BallPickup;

public class AutonomousEnableBallPickup implements SPIKE293AutonomousCommand 
{
  private BallPickup m_ballPickup;

  public AutonomousEnableBallPickup(BallPickup ballPickup)
  {
    m_ballPickup = ballPickup;
  }

  @Override
  public void execute() 
  {
    //Enable the gecko
    m_ballPickup.geckoToggle(true);
  }

  @Override
  public boolean isDone() {
    return true;
  }
}
