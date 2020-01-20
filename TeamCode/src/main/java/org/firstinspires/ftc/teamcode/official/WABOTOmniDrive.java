package org.firstinspires.ftc.teamcode.official;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.DriveImplementation;
import org.firstinspires.ftc.teamcode.RobotInstance;

public class WABOTOmniDrive extends DriveImplementation {

    public WABOTOmniDrive(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor, RobotInstance robot){
        super(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, robot);
    }

    @Override
    public void updateDrive() {
        double leftStickX = robot.getController1().left_stick_x();
        double leftStickY = -robot.getController1().left_stick_y();
        double rightStickY = -robot.getController1().right_stick_y();
        double rightStickX = robot.getController1().right_stick_x();

        // Calculating angle between X and Y inputs on the stick
        double angle = Math.atan2(leftStickY, leftStickX);
        angle = Math.toDegrees(angle);
        angle = Math.abs(angle);
        double angleS = Math.atan2(rightStickY, rightStickX);
        angleS = Math.toDegrees(angleS);
        angleS = Math.abs(angleS);
        // Altering value for sake of the program
        if(leftStickY < 0){
            angle = 360 - angle;
        }
        if(rightStickY < 0){
            angleS = 360 - angleS;
        }

        // Power variables
        double v1 = 0, v2 = 0, v3 = 0, v4 = 0;

        // Represents what quadrant our stick is in
        int quadrant = 0;

        // Calculating current quadrant
        if(leftStickX == 0 && leftStickY == 0){
            quadrant = 0;
        } else if(angle >= 0 && angle <= 90){
            quadrant = 1;
        } else if(angle > 90 && angle <= 180){
            quadrant = 2;
        } else if(angle > 180 && angle <= 270){
            quadrant = 3;
        } else if(angle > 270 && angle <= 360) {
            quadrant = 4;
        }

        int quadrantS = 0;

        // Calculating current quadrant
        if(rightStickX == 0 && rightStickY == 0){
            quadrantS = 0;
        } else if(angleS >= 0 && angleS <= 90){
            quadrantS = 1;
        } else if(angleS > 90 && angleS <= 180){
            quadrantS = 2;
        } else if(angleS > 180 && angleS <= 270){
            quadrantS = 3;
        } else if(angleS > 270 && angleS <= 360) {
            quadrantS = 4;
        }

        // Getting our composite input used as a backbone value for movement
        // Short explanation: Always a net Y value, but uses a different percent from each direction based on Y value
        double sampleY = leftStickY;
        double sampleYS = rightStickY;

        double magnitude = Math.abs(sampleY) + Math.abs((1-Math.abs(sampleY))*leftStickX);
        double magnitudeS = Math.abs(sampleYS) + Math.abs((1-Math.abs(sampleYS))*rightStickX);

        // Based on the quadrant, change the underlying function each wheel depends on
        if(quadrant == 1){
            v1 = magnitude*((angle-45)/45);
            v3 = magnitude*((angle-45)/45);
            v2 = magnitude;
            v4 = magnitude;
        } else if(quadrant == 2){
            v1 = magnitude;
            v3 = magnitude;
            v2 = magnitude*((135-angle)/45);
            v4 = magnitude*((135-angle)/45);
        } else if(quadrant == 3){
            v1 = magnitude*((225-angle)/45);
            v3 = magnitude*((225-angle)/45);
            v2 = -1*magnitude;
            v4 = -1*magnitude;
        } else if(quadrant == 4){
            v1 = -1*magnitude;
            v3 = -1*magnitude;
            v2 = -1*magnitude*((315-angle)/45);
            v4 = -1*magnitude*((315-angle)/45);
        } else if(quadrant == 0){
            v1 = 0;
            v2 = 0;
            v3 = 0;
            v4 = 0;
        }

        /*

        if(magnitudeS != 0) {
            if (quadrantS == 1) {
                v1 = magnitudeS * ((angleS - 45) / 45);
                v3 = magnitudeS;
                v2 = magnitudeS;
                v4 = magnitudeS * ((angleS - 45) / 45);
            } else if (quadrantS == 2) {
                v1 = magnitudeS;
                v3 = magnitudeS * ((135 - angleS) / 45);
                v2 = magnitudeS * ((135 - angleS) / 45);
                v4 = magnitudeS;
            } else if (quadrantS == 3) {
                v1 = magnitudeS * ((225 - angleS) / 45);
                v3 = -1 * magnitudeS;
                v2 = -1 * magnitudeS;
                v4 = magnitudeS * ((255 - angleS) / 45);
            } else if (quadrantS == 4) {
                v1 = -1 * magnitudeS;
                v3 = -1 * magnitudeS * ((angleS - 315) / 45);
                v2 = -1 * magnitudeS * ((angleS - 315) / 45);
                v4 = -1 * magnitudeS;
            } else if (quadrantS == 0) {
                v1 = 0;
                v2 = 0;
                v3 = 0;
                v4 = 0;
            }
        }
        */

        // If not using omni-drive, switch to normal turn
        // OLD CODE, KEEP PLEASSSE
        if(rightStickX != 0){
            v1 = -1*rightStickX;
            v2 = rightStickX;
            v3 = rightStickX;
            v4 = -1*rightStickX;
        }

        // Precision controls based on bumpers pressed
        if(robot.getController1().left_bumper() && !robot.getController1().right_bumper()){
            v1 *= 0.5;
            v2 *= 0.5;
            v3 *= 0.5;
            v4 *= 0.5;
        }
        if(robot.getController1().right_bumper() && !robot.getController1().left_bumper()){
            v1 *= 0.5;
            v2 *= 0.5;
            v3 *= 0.5;
            v4 *= 0.5;
        }
        if(robot.getController1().right_bumper() && robot.getController1().left_bumper()){
            v1 *= 0.25;
            v2 *= 0.25;
            v3 *= 0.25;
            v4 *= 0.25;
        }

        FRMotor.setPower(v1);
        FLMotor.setPower(v2);
        BLMotor.setPower(v3);
        BRMotor.setPower(v4);
    }
}
