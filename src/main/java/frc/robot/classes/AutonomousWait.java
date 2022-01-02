package frc.robot.classes;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.BallPickup;

public class AutonomousWait implements SPIKE293AutonomousCommand {
  private Timer m_timer;
  private double m_secondsToWait = 0.0d;

  public AutonomousWait(double secondsToWait)
  {
    m_secondsToWait = secondsToWait;

    //Init timer
    m_timer = new Timer();
    m_timer.stop();
    m_timer.reset();
    
  }

  @Override
  public void execute() 
  {
    //Calling start once a timer has already been started results in a noop
    m_timer.start(); 
  }

  @Override
  public boolean isDone() 
  {
    return m_timer.hasElapsed(m_secondsToWait);
  }
}
