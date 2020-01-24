package org.firstinspires.ftc.teamcode.official;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="EncoderTester", group="Linear Opmode")
public class EncoderTelemetry extends LinearOpMode {

    //Declaring Motors
    public DcMotorEx leftbackMotor;
    public DcMotorEx rightbackMotor;
    public DcMotorEx leftfrontMotor;
    public DcMotorEx rightfrontMotor;


    @Override
    public void runOpMode() {

        // Set up detector


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftbackMotor = hardwareMap.get(DcMotorEx.class, "BLMotor");
        rightbackMotor = hardwareMap.get(DcMotorEx.class, "LIntake");
        leftfrontMotor = hardwareMap.get(DcMotorEx.class, "FLMotor");
        rightfrontMotor = hardwareMap.get(DcMotorEx.class, "FRMotor");

        leftbackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftfrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightbackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightfrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        leftbackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightbackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftfrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightfrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftbackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightbackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftfrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightfrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        waitForStart();

        if(isStopRequested()) return;
        while(!isStopRequested()) {


            telemetry.addData("Right Back Position: ", rightbackMotor.getCurrentPosition());
            telemetry.addData("Left Back Position: ", leftbackMotor.getCurrentPosition());
            telemetry.addData("Right Front Position: ", rightfrontMotor.getCurrentPosition());
            telemetry.addData("Left Front Position: ", leftfrontMotor.getCurrentPosition());
            telemetry.update();


        }
    }
}