package org.firstinspires.ftc.teamcode.official;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.HardwareMapConfig;
import org.firstinspires.ftc.teamcode.RobotInstance;

import java.util.ArrayList;
import java.util.List;

public class WABOTHardware extends HardwareMapConfig {

    final double LEFTARMSERVO_IN = 0.5;
    final double LEFTARMSERVO_OUT = 0.7;
    final double RIGHTARMSERVO_IN = 0.8;
    final double RIGHTARMSERVO_OUT = 0.5;

    final double LEFTFOUND_DOWN = 1f;
    final double LEFTFOUND_UP = 0.5f;
    final double RIGHTFOUND_DOWN = 0.5f;
    final double RIGHTFOUND_UP = 1f;

    final double FRONTARMARMSERVO_IN = 0.5;
    final double FRONTARMARMSERVO_OUT = 1;

    final double BACKARMSERVO_IN = 0.9;
    final double BACKARMSERVO_OUT = 0;

    final double CAPSERVO_IN = 0.9;
    final double CAPSERVO_OUT = 0.4; //todo find positions

    public DcMotor FLMotor;
    public DcMotor FRMotor;
    public DcMotor BLMotor;
    public DcMotor BRMotor;
    public DcMotor LIntake;
    public DcMotor RIntake;
    public Servo leftFound;
    public Servo rightFound;
    public DcMotor slideArm; //horizontal slide
    public Servo LArmServo; //stone grabber servos
    public Servo RArmServo; //stone grabber servo
    public DcMotor liftMotor; //vertical slides
    public Servo capServo;

    public WABOTHardware(HardwareMap hardwareMap, boolean isAutonomous, RobotInstance robot) {
        super(hardwareMap, isAutonomous, robot);
        this.isAutonomous = isAutonomous;
    }


    public void initializeMap() {
        FLMotor = getInternalMap().get(DcMotor.class, "FLMotor");
        FRMotor = getInternalMap().get(DcMotor.class, "FRMotor");
        BLMotor = getInternalMap().get(DcMotor.class, "BLMotor");
        BRMotor = getInternalMap().get(DcMotor.class, "BRMotor");
        slideArm = getInternalMap().get(DcMotor.class, "slideArm");
        liftMotor = getInternalMap().get(DcMotor.class, "liftMotor");
        LIntake = getInternalMap().get(DcMotor.class, "LIntake");
        RIntake = getInternalMap().get(DcMotor.class, "RIntake");
        LArmServo = getInternalMap().get(Servo.class, "LArmServo");
        RArmServo = getInternalMap().get(Servo.class, "RArmServo");
        leftFound = getInternalMap().get(Servo.class, "leftFound");
        rightFound = getInternalMap().get(Servo.class, "rightFound");
        capServo = getInternalMap().get(Servo.class, "capServo");
    }

    @Override
    public void setMotorDirection() {

        // CONVENTION: This original setting is considered DEFAULT forward
        robot.getDriveController().setPreferredMotorDir(FLMotor, DcMotorSimple.Direction.FORWARD);
        robot.getDriveController().setPreferredMotorDir(FRMotor, DcMotorSimple.Direction.REVERSE);
        robot.getDriveController().setPreferredMotorDir(BLMotor, DcMotorSimple.Direction.FORWARD);
        robot.getDriveController().setPreferredMotorDir(BRMotor, DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void setMotorBrakeMode() {
        robot.getDriveController().setPreferredMotorBrakeMode(FLMotor, DcMotor.ZeroPowerBehavior.BRAKE);
        robot.getDriveController().setPreferredMotorBrakeMode(FRMotor, DcMotor.ZeroPowerBehavior.BRAKE);
        robot.getDriveController().setPreferredMotorBrakeMode(BLMotor, DcMotor.ZeroPowerBehavior.BRAKE);
        robot.getDriveController().setPreferredMotorBrakeMode(BRMotor, DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void setMotorMode() {
        if(isAutonomous){
            // NOTE: Only reset encoders if you want the relative encoder value to change!!!!
            robot.getDriveController().setPreferredMotorMode(FLMotor, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.getDriveController().setPreferredMotorMode(FRMotor, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.getDriveController().setPreferredMotorMode(BLMotor, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.getDriveController().setPreferredMotorMode(BRMotor, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            robot.getDriveController().setPreferredMotorMode(FLMotor, DcMotor.RunMode.RUN_USING_ENCODER);
            robot.getDriveController().setPreferredMotorMode(FRMotor, DcMotor.RunMode.RUN_USING_ENCODER);
            robot.getDriveController().setPreferredMotorMode(BLMotor, DcMotor.RunMode.RUN_USING_ENCODER);
            robot.getDriveController().setPreferredMotorMode(BRMotor, DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            robot.getDriveController().setPreferredMotorMode(FLMotor, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.getDriveController().setPreferredMotorMode(FRMotor, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.getDriveController().setPreferredMotorMode(BLMotor, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.getDriveController().setPreferredMotorMode(BRMotor, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    @Override
    public void setStartingPositions() {
        LArmServo.setPosition(LEFTARMSERVO_IN);
        RArmServo.setPosition(RIGHTARMSERVO_IN);
    }
}