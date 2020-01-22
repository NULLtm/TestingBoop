package org.firstinspires.ftc.teamcode.official;

/*
 * Wright Angle Robotics #6427 2019-2020
 */

import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.RobotInstance;
import org.firstinspires.ftc.teamcode.RobotParameters;
import org.firstinspires.ftc.teamcode.angleDrifter.RoutableRobot;
import org.firstinspires.ftc.teamcode.angleDrifter.Vector2D;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;


@TeleOp(name="WABOTTeleop", group="WABOT")
public class  WABOTTeleop extends OpMode {


    // Declare OpMode members.
    private RobotInstance robot;

    private boolean hasStartedCap = false;
    private long capTime;
    private float intakePow = 0;
    private final double PRECISION_SPEED_MODIFIER = 0.5;

    @Override
    public void init() {
        robot = new RobotInstance("WABOT");

        RobotParameters parameters = new RobotParameters();
        parameters.map = hardwareMap;
        parameters.isAutonomous = false;
        parameters.gamepad1 = gamepad1;
        parameters.gamepad2 = gamepad2;
        parameters.useRevIMU = false;
        parameters.useVuforia = false;
        parameters.console = telemetry;

        robot.setParameters(parameters);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        // Starting Positions for Servos
        robot.getConfig().setStartingPositions();
    }

    @Override
    public void loop() {
        // Gamepad input
        input();

        // Drive train controls
        robot.printToConsole("Left Encoder: ", robot.getConfig().getLeftEncoderPos());
        robot.printToConsole("Right Encoder: ", robot.getConfig().getRightEncoderPos());
        robot.printToConsole("Strafe Encoder: ", robot.getConfig().getStrafeEncoderPos());
        //robot.printToConsole("LiftMotor Encoder Value: ", robot.getConfig().liftMotor.getCurrentPosition());
        robot.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        // Updating to log
        robot.printToConsole("Status:", "Stopped");

        // Stops motors just in case
        robot.getDriveController().stopMotors();
    }

    private void input(){
        // Triggers control intake/outtake
        if(gamepad1.right_trigger > 0){
            intakePow = gamepad1.right_trigger;
        }else if(gamepad1.left_trigger > 0){
            intakePow = -gamepad1.left_trigger;
        } else {
            intakePow = 0;
        }
        robot.getConfig().LIntake.setPower(-intakePow);
        robot.getConfig().RIntake.setPower(intakePow);

        double liftPower = -gamepad2.left_stick_y;
        double armSlidePower = -gamepad2.right_stick_y;

        if(gamepad2.left_bumper && !gamepad2.right_bumper){
            liftPower *= 0.5;
        }
        if(gamepad2.right_bumper && !gamepad2.left_bumper){
            liftPower *= 0.5;
        }
        if(gamepad2.right_bumper && gamepad2.left_bumper){
            liftPower *= 0.25;
        }

        robot.getConfig().slideArm.setPower(armSlidePower);
        robot.getConfig().liftMotor.setPower(liftPower);

        if(robot.getController2().returnToggleX()){
            robot.getConfig().armServo.setPosition(robot.getConfig().ARMSERVO_OUT);
        } else {
            robot.getConfig().armServo.setPosition(robot.getConfig().ARMSERVO_IN);
        }

        if(gamepad2.dpad_down && !hasStartedCap){
            robot.getConfig().capServo.setPosition(robot.getConfig().CAPSERVO_OUT);
            hasStartedCap = true;
            capTime = System.currentTimeMillis();
        }
        if(hasStartedCap && System.currentTimeMillis()-capTime > 500){
            robot.getConfig().capServo.setPosition(robot.getConfig().CAPSERVO_IN);
            hasStartedCap = false;
        }

        // Toggleable
        if(robot.getController1().returnToggleX()){
            robot.getConfig().leftFound.setPosition(0.5f);
            robot.getConfig().rightFound.setPosition(1f);
        } else {
            robot.getConfig().leftFound.setPosition(1f);
            robot.getConfig().rightFound.setPosition(0.5f);
        }
    }

    // Tank drive controls
    private void tankDrive(){
        double leftStickY = robot.getController1().left_stick_y();
        double rightStickY = robot.getController1().right_stick_y();

        robot.getConfig().FLMotor.setPower(leftStickY);
        robot.getConfig().FRMotor.setPower(rightStickY);
        robot.getConfig().BLMotor.setPower(leftStickY);
        robot.getConfig().BRMotor.setPower(rightStickY);
    }

    // Normal holonomic drive
    private void holoDrive(){
        double leftStickX = gamepad1.right_stick_x;
        double leftStickY = -gamepad1.left_stick_y;
        double rightStickX = gamepad1.left_stick_x;
        double rightStickY = gamepad1.right_stick_y;

        double r = Math.hypot(leftStickX, leftStickY);
        double robotAngle = Math.atan2(leftStickY, leftStickX) - (Math.PI / 4);
        double leftX = rightStickX;
        double turn = leftX;

        double v1 = r * Math.cos(robotAngle) + turn;
        double v2 = r * Math.sin(robotAngle) - turn;
        double v3 = r * Math.sin(robotAngle) + turn;
        double v4 = r * Math.cos(robotAngle) - turn;

        if(gamepad1.right_bumper || gamepad1.left_bumper){
            v1 = v1 * PRECISION_SPEED_MODIFIER;
            v2 = v2 * PRECISION_SPEED_MODIFIER;
            v3 = v3 * PRECISION_SPEED_MODIFIER;
            v4 = v4 * PRECISION_SPEED_MODIFIER;
        }
        robot.getConfig().FLMotor.setPower(v1);
        robot.getConfig().FRMotor.setPower(v2);
        robot.getConfig().BLMotor.setPower(v3);
        robot.getConfig().BRMotor.setPower(v4);
    }

}
