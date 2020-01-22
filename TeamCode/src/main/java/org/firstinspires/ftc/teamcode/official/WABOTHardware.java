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

    final double ARMSERVO_IN = 0.8;
    final double ARMSERVO_OUT = 0.6;

    final double LEFTFOUND_DOWN = 1f;
    final double LEFTFOUND_UP = 0.5f;
    final double RIGHTFOUND_DOWN = 0.5f;
    final double RIGHTFOUND_UP = 1f;

    final double FRONTARMARMSERVO_IN = 0.5;
    final double FRONTARMARMSERVO_OUT = 1;

    final double BACKARMSERVO_IN = 0.9;
    final double BACKARMSERVO_OUT = 0;

    final double CAPSERVO_IN = 0.5;
    final double CAPSERVO_OUT = 1.0; //todo find positions

    public DcMotor FLMotor;
    public DcMotor FRMotor;
    public DcMotor BLMotor;
    public DcMotor BRMotor;
    public DcMotor LIntake;
    public DcMotor RIntake;
    public Servo leftFound;
    public Servo rightFound;
    public DcMotor slideArm; //horizontal slide
    public Servo armServo; //stone grabber servo
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
        armServo = getInternalMap().get(Servo.class, "armServo");
        leftFound = getInternalMap().get(Servo.class, "leftFound");
        rightFound = getInternalMap().get(Servo.class, "rightFound");
        capServo = getInternalMap().get(Servo.class, "capServo");
    }

    @Override
    public void setMotorDirection() {

        // CONVENTION: This original setting is considered DEFAULT forward
        //robot.getDriveController().setPreferredMotorDir(FLMotor, DcMotorSimple.Direction.FORWARD);
        //robot.getDriveController().setPreferredMotorDir(FRMotor, DcMotorSimple.Direction.REVERSE);
        //robot.getDriveController().setPreferredMotorDir(BLMotor, DcMotorSimple.Direction.FORWARD);
        //robot.getDriveController().setPreferredMotorDir(BRMotor, DcMotorSimple.Direction.REVERSE);
        FLMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        FRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        BLMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        BRMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void setMotorBrakeMode() {
        //robot.getDriveController().setPreferredMotorBrakeMode(FLMotor, DcMotor.ZeroPowerBehavior.BRAKE);
        //robot.getDriveController().setPreferredMotorBrakeMode(FRMotor, DcMotor.ZeroPowerBehavior.BRAKE);
        //robot.getDriveController().setPreferredMotorBrakeMode(BLMotor, DcMotor.ZeroPowerBehavior.BRAKE);
        //robot.getDriveController().setPreferredMotorBrakeMode(BRMotor, DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void setMotorMode() {
        if(isAutonomous){
            // NOTE: Only reset encoders if you want the relative encoder value to change!!!!
            //robot.getDriveController().setPreferredMotorMode(FLMotor, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //robot.getDriveController().setPreferredMotorMode(FRMotor, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //robot.getDriveController().setPreferredMotorMode(BLMotor, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //robot.getDriveController().setPreferredMotorMode(BRMotor, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //robot.getDriveController().setPreferredMotorMode(FLMotor, DcMotor.RunMode.RUN_USING_ENCODER);
            //robot.getDriveController().setPreferredMotorMode(FRMotor, DcMotor.RunMode.RUN_USING_ENCODER);
            //robot.getDriveController().setPreferredMotorMode(BLMotor, DcMotor.RunMode.RUN_USING_ENCODER);
            //robot.getDriveController().setPreferredMotorMode(BRMotor, DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            FLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            FRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            BLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            BRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RIntake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void setStartingPositions() {
        armServo.setPosition(ARMSERVO_OUT);
        capServo.setPosition(CAPSERVO_IN);
    }

    public int getLeftEncoderPos(){
        return liftMotor.getCurrentPosition();
    }

    public int getRightEncoderPos(){
        return slideArm.getCurrentPosition();
    }

    public int getStrafeEncoderPos(){
        return RIntake.getCurrentPosition();
    }
}