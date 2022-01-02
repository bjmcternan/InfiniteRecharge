package frc.robot.classes;

import frc.robot.subsystems.Drivetrain;
import static frc.robot.Constants.AutonomousCommandConstants.*;
import static frc.robot.Constants.DrivetrainConstants.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousMoveTo implements SPIKE293AutonomousCommand 
{
  private Drivetrain m_drivetrain;
  private SmoothControl m_smoothControl;
  private Position2D m_targePosition2d;
  private double m_desiredVelocity = 0.0d;

  /**
   * Returns the distance from the current pose to the target pose
   *
   * @param drivetrain    The drivetrain object
   * @param smoothControl The smoothControl object
   * @param targetPosition2d The target position to move to
   * @param desiredVelocity The desired velocity to move at
   * @param resetKinematics true if the kinematics needs to be reset. Typically done if this is the first position to move to
   */
  public AutonomousMoveTo(Drivetrain drivetrain, SmoothControl smoothControl, Position2D targetPosition2d, double desiredVelocity, boolean resetKinematics)
  {
    m_drivetrain = drivetrain;
    m_smoothControl = smoothControl;
    m_targePosition2d = targetPosition2d;
    m_desiredVelocity = desiredVelocity;

    //Should we intialize and reset the kinematics controller?
    if(true == resetKinematics)
    {
      m_drivetrain.resetKinematics();
      m_drivetrain.initAutonomous(new Position2D(0.0d, 0.0d, 0.0d));
    }

    SmartDashboard.putString("Next Target", m_targePosition2d.getX() + ", "+ m_targePosition2d.getY() + ", "+ m_targePosition2d.getHeadingDegrees());
  }

  @Override
  public void execute() 
  {
    double vR = 0.0d;
    double vL = 0.0d;

    //Compute turn rate and update range
    m_smoothControl.computeTurnRate(m_drivetrain.getPose(), m_targePosition2d, m_drivetrain.getRobotVelocity());
    
    //Calculate vR in feet per second
    vR = m_desiredVelocity - (TRACK_WIDTH_FEET/2)*m_smoothControl.getTurnRateRadians();
    //Calculate vL in feet per second
    vL = m_desiredVelocity + (TRACK_WIDTH_FEET/2)*m_smoothControl.getTurnRateRadians();
    
    //Converting ft/s equation output to controller velocity
    vR = SPIKE293Utils.feetPerSecToControllerVelocity(vR);
    vL = SPIKE293Utils.feetPerSecToControllerVelocity(vL);

    //Send vR and vL to velocity drive, units are in controller velocity
    m_drivetrain.velocityDrive(vL, vR);

    //Printout debug info
    SmartDashboard.putNumber("Desired Left Velocity (ft/s)", vL);
    SmartDashboard.putNumber("Desired Right Velocity (ft/s)", vR);
  }

  @Override
  public boolean isDone() {
    boolean isDone = false;
    double distanceToTarget = getDistanceToPose(m_drivetrain.getPose(), m_targePosition2d);

    //Printout debug info
    SmartDashboard.putNumber("Distane to target pose", distanceToTarget);

    if(TARGET_WITHIN_RANGE_FEET <= distanceToTarget)
    {
      isDone = true;
    }

    return isDone;
  }

  /**
   * Returns the distance from the current pose to the target pose
   *
   * @param currentPose The current pose
   * @param targetPose The target pose
   * @return The distance from the current pose to the target pose, units is the same as the input units
   */
  private double getDistanceToPose(Position2D currentPose, Position2D targetPose)
  {
    double dx = targetPose.getX() - currentPose.getX();
    double dy = targetPose.getY() - currentPose.getY();
    return(Math.sqrt(dx * dx + dy * dy));
  }
}
