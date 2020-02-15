// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
/**
 *
 */

public class Drivetrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private WPI_TalonFX leftTalonLead;
private WPI_TalonFX leftTalonFollower;
private WPI_TalonFX rightTalonLead;
private WPI_TalonFX rightTalonFollower;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    private double kF = 0.04759;
    private double kP = 0.01461;
    private double kI = 0.0;
    private double kD = 0.0;

    private double Ldeadband = .15;
    private double Rdeadband = .15;

    private final double CLOSED_LOOP_RAMP = 0.5;
    private final double MAX_VELOCITY = 21549;
    private final double VELOCITY_LIMIT_PERCENTAGE = 0.5;
    private final double VELOCITY_SLOWDOWN_MODIFIER = 0.5;

    private final double INVALID_INPUT = -99;

    public Drivetrain() {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
leftTalonLead = new WPI_TalonFX(0);


        
leftTalonFollower = new WPI_TalonFX(1);


        
rightTalonLead = new WPI_TalonFX(2);


        
rightTalonFollower = new WPI_TalonFX(3);


        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    
    leftTalonLead.clearStickyFaults();
    leftTalonFollower.clearStickyFaults();
    rightTalonLead.clearStickyFaults();
    rightTalonFollower.clearStickyFaults();

    leftTalonFollower.follow(leftTalonLead);
    rightTalonFollower.follow(rightTalonLead);

    leftTalonLead.setInverted(true);
    leftTalonFollower.setInverted(InvertType.FollowMaster);
    rightTalonLead.setInverted(false);
    rightTalonFollower.setInverted(InvertType.FollowMaster);

    leftTalonLead.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 4000);
    rightTalonLead.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 4000);
    
    //Set facotry defaults for onboard PID

    leftTalonLead.configFactoryDefault();
    rightTalonLead.configFactoryDefault();

    //Configure PID

    leftTalonLead.config_kF(0,kF,10);
    leftTalonLead.config_kP(0,kP,10);
    leftTalonLead.config_kI(0,kI,10);
    leftTalonLead.config_kD(0,kD,0);
    leftTalonLead.configClosedloopRamp(CLOSED_LOOP_RAMP);
    leftTalonLead.setNeutralMode(NeutralMode.Brake);

    rightTalonLead.config_kF(0,kF,10);
    rightTalonLead.config_kP(0,kP,10);
    rightTalonLead.config_kI(0,kI,10);
    rightTalonLead.config_kD(0,kD,0);
    rightTalonLead.configClosedloopRamp(CLOSED_LOOP_RAMP);
    rightTalonLead.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void initDefaultCommand() {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.

        // setDefaultCommand(new MySpecialCommand());

        setDefaultCommand(new VelocityDrive());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    // Converts joystick input adjusted for deadband to current for the motor

    public void dumbDrive(Joystick left, Joystick right) {

        double leftPos = left.getY();
        double rightPos = right.getY();
        double retval = 0.0;

        retval = calcMotorPower(leftPos, Ldeadband);
        if(INVALID_INPUT == retval) {
            System.out.println("Invalid left motor input" + leftPos);
        } else {
            leftTalonLead.set(TalonFXControlMode.PercentOutput,retval);    
        }

        retval = calcMotorPower(rightPos, Rdeadband);
        if(INVALID_INPUT == retval) {
            System.out.println("Invalid right motor input" + rightPos);
        } else {
            rightTalonLead.set(TalonFXControlMode.PercentOutput,retval);    
        }   
    }

    // Converts joystick input adjusted to a RPM for the Falcon's PIDF loop to aim for

    public void velocityDrive(Joystick left, Joystick right){
        double leftPos = left.getY();
        double rightPos = right.getY();
        double retval = 0.0;
        boolean useSlowModifier = false;

        if(Robot.oi.rightJoy.getTrigger()){
            useSlowModifier = true;
        }
        if(Robot.oi.leftJoy.getTrigger()){
            useSlowModifier = true;
        }

        retval = calcMotorPower(leftPos, Ldeadband);
        if(INVALID_INPUT == retval) {
            System.out.println("Invalid left motor input" + leftPos);
        } else {
            if(useSlowModifier){
                leftTalonLead.set(TalonFXControlMode.Velocity,(retval * MAX_VELOCITY * 
                    VELOCITY_LIMIT_PERCENTAGE * VELOCITY_SLOWDOWN_MODIFIER));    
            } else {
                leftTalonLead.set(TalonFXControlMode.Velocity,(retval * MAX_VELOCITY * 
                    VELOCITY_LIMIT_PERCENTAGE));
            }
            
        }

        retval = calcMotorPower(rightPos, Rdeadband);
        if(INVALID_INPUT == retval) {
            System.out.println("Invalid right motor input" + rightPos);
        } else {
            if(useSlowModifier){
                rightTalonLead.set(TalonFXControlMode.Velocity,(retval * MAX_VELOCITY * 
                    VELOCITY_LIMIT_PERCENTAGE * VELOCITY_SLOWDOWN_MODIFIER));    
            } else {
                rightTalonLead.set(TalonFXControlMode.Velocity,(retval * MAX_VELOCITY * 
                    VELOCITY_LIMIT_PERCENTAGE));
            }
        }
    }

    // Stops motor usually used after the drive command ends to prevent shenanigans

    public void stop() {
        leftTalonLead.set(TalonFXControlMode.Current,0);
        rightTalonLead.set(TalonFXControlMode.Current,0);
    }

    //Calculates the motor power to use based on a given deadband and 
    //joystick input from -1 to 1
    //Prevents spikes in motor power by calculating the line to use 
    //where 0 is the deadband and 1 is the max

    public double calcMotorPower(double input, double deadband) {
        double retval = 0.0;
        if(Math.abs(input) <= deadband) { //Check if input is inside the deadband
            return 0;
        }

        if((input < -1) || (input > 1)) { //input must be between -1 and 1
            return INVALID_INPUT;
        }
        
        retval = (1/(1 - deadband) * Math.abs(input) - (deadband/(1 - deadband)));

        if(input < 0) {
           return -1 * retval;
        } else {
            return retval;
        }
    }

}

