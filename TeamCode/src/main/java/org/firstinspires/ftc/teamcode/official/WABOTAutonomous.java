package org.firstinspires.ftc.teamcode.official;

import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.teamcode.RobotInstance;
import org.firstinspires.ftc.teamcode.RobotParameters;

import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

/**
 * OFFICIAL AUTONOMOUS CODE
 *
 * WRIGHT ANGLE ROBOTICS (Skystone 2019-2020)
 *
 * Contributor:
 * Owen Boseley (2016-2021)
 *
 * NOTES:
 * - Anything marked with the comment (// DO NOT TOUCH) should not be touched xD
 *
 *
 */
@Autonomous(name="WABOTAutonomous", group="WABOT")
public class WABOTAutonomous extends LinearOpMode {


    // Percent representing decrease in speed for driver controls
    private final double PRECISION_SPEED_MODIFIER = 0.5;

    private ThreeTrackingWheelLocalizer localizer;

    // Hardware map object
    private WABOTHardware h;

    private RobotInstance robot;


    /*
    VVVV SETTINGS VVVV
     */


    // Parameters for initializing vuforia
    // NOTE: If Webcam: Direction = BACK, isPortrait = true;


    // Initializi1ng robot here!
    // DO NOT TOUCH unless necessary
    @Override
    public void runOpMode() {

        robot = new RobotInstance("WABOT");

        RobotParameters parameters = new RobotParameters();
        parameters.map = hardwareMap;
        parameters.isAutonomous = true;
        parameters.gamepad1 = gamepad1;
        parameters.gamepad2 = gamepad2;

        robot.setParameters(parameters);

        // Accessing our path builder from our robot instance
        TrajectoryBuilder trajectoryBuilder = robot.getDriveController().getPathBuilder();

        // Go forward 10 units
        Trajectory trajectory = trajectoryBuilder.forward(10).build();

        // Setting our path we want to follow in our robot instance
        robot.getDriveController().setPath(trajectory);

        waitForStart();

        robot.getDriveController().followPath();

        run();
    }







    // Actual instructions for robot! All autonomous code goes here!!!
    private void run(){
    }



    /*public void goToHeading(double heading){
        while (Math.abs(imu.getHeading()-heading) > 1.5) {
            double power = Math.pow(3, 0.01*Math.abs(imu.getHeading()-heading))-0.87;
            telemetry.addData("DIFFERENCE:", Math.abs(imu.getHeading()-heading));
            telemetry.update();
            if (imu.getHeading() > heading) {
                turn(1, power);
            }
            if (imu.getHeading() < heading) {
                turn(-1, power);
            }
        }
        stopMotors();

    }


    private void runDistanceOdometer(double distanceINCH, double power){
        double revs = distanceINCH*2.2/CIRCUMFERENCE;
        int ticksToRun = (int)(revs * ENCODER_TICK);

        while(Math.abs(h.getRightEncoderPos()-ticksToRun) > 300){
            h.FLMotor.setPower(power*(ticksToRun/Math.abs(ticksToRun)));
            h.FRMotor.setPower(power*(ticksToRun/Math.abs(ticksToRun)));
            h.BLMotor.setPower(power*(ticksToRun/Math.abs(ticksToRun)));
            h.BRMotor.setPower(power*(ticksToRun/Math.abs(ticksToRun)));
        }
    }

    private double getNetForward(){
        double net = (h.getLeftEncoderPos()+h.getRightEncoderPos()) / 2;

        return net;
    }


    // DO NOT TOUCH
    // Maintains heading and adjusts if pushed: NEED GYRO
    private void driveStraight(int targetHeading, double startSpeed){
        double heading = imu.getHeading();

        telemetry.addData("Heading: ", heading);
        telemetry.update();

        double difference = heading - targetHeading;
        double power = difference/90.0;

        h.FLMotor.setPower(startSpeed + power);
        h.FRMotor.setPower(startSpeed - power);
        h.BLMotor.setPower(startSpeed + power);
        h.BRMotor.setPower(startSpeed - power);

    }


    // DO NOT TOUCH
    // Usable is there is a gyro installed
    private void turnByDegree (int degree) {
        double currentPower = 0.5;
        boolean right;
        double turnTo;

        if(degree < 0){
            right = false;
            turnTo = convertedHeading((degree*-1)) + convertedHeading(imu.getHeading());
        } else {
            right = true;
            turnTo = convertedHeading(degree) + convertedHeading(imu.getHeading());
        }

        // if to the right, turn right, vise versa
        if (right) {
            while (convertedHeading(imu.getHeading()) < turnTo) {

                double difference = turnTo - convertedHeading(imu.getHeading());

                telemetry.addData("Difference: ", difference);
                telemetry.addData("Heading: ", convertedHeading(imu.getHeading()));
                telemetry.addData("CurrentPower: ", currentPower);
                telemetry.update();


                h.FLMotor.setPower(currentPower);
                h.FRMotor.setPower(-currentPower);
                h.BLMotor.setPower(currentPower);
                h.BRMotor.setPower(-currentPower);

                if(difference <= 3){
                    currentPower *= (difference / 3);
                }
            }
        } else if (!right) {
            while (convertedHeading(imu.getHeading()) < turnTo) {

                double difference = turnTo - convertedHeading(imu.getHeading());

                telemetry.addData("Difference: ", difference);
                telemetry.addData("Heading: ", convertedHeading(imu.getHeading()));
                telemetry.addData("CurrentPower: ", currentPower);
                telemetry.update();

                h.FLMotor.setPower(-currentPower);
                h.FRMotor.setPower(currentPower);
                h.BLMotor.setPower(-currentPower);
                h.BRMotor.setPower(currentPower);

                if(difference <= 3){
                    currentPower *= (difference / 3);
                }
            }
        }

        stopMotors();
    }














*/


    // DO NOT TOUCH
    // Used for "turn by degree" ONLY
    public double convertedHeading(double h){
        // TODO Check this for imu


        /* double heading = h;
        while(heading > 180){
            heading -= (2*(heading-180));

            if(heading < 0){
                heading *= -1;
            }
        } */
        return Math.abs(h);
    }

    // Clamp function
    public double clamp(double min, double max, double value){
        if(value < min){
            value = min;
        } else if(value > max){
            value = max;
        }

        return value;
    }
}
