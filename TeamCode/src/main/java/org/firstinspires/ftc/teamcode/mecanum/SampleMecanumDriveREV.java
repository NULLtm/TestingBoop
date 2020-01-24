package org.firstinspires.ftc.teamcode.mecanum;

import android.support.annotation.NonNull;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import com.qualcomm.robotcore.hardware.configuration.LynxConstants;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.teamcode.localizer.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.mecanum.SampleMecanumDriveBase;
import org.firstinspires.ftc.teamcode.util.AxesSigns;
import org.firstinspires.ftc.teamcode.util.BNO055IMUUtil;
import org.firstinspires.ftc.teamcode.util.LynxModuleUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.firstinspires.ftc.teamcode.DriveConstants.MOTOR_VELO_PID;
import static org.firstinspires.ftc.teamcode.DriveConstants.RUN_USING_ENCODER;
import static org.firstinspires.ftc.teamcode.DriveConstants.encoderTicksToInches;
import static org.firstinspires.ftc.teamcode.DriveConstants.getMotorVelocityF;

/*
 * Simple mecanum drive hardware implementation for REV hardware. If your hardware configuration
 * satisfies the requirements, SampleMecanumDriveREVOptimized is highly recommended.
 */
public class SampleMecanumDriveREV extends SampleMecanumDriveBase {
    private DcMotorEx leftFront, leftRear, rightRear, rightFront, BRMotorEncoder;
    private List<DcMotorEx> motors;
    private BNO055IMU imu;

    public SampleMecanumDriveREV(HardwareMap hardwareMap) {
        super();

        LynxModuleUtil.ensureMinimumFirmwareVersion(hardwareMap);

        // TODO: adjust the names of the following hardware devices to match your configuration
        //imu = hardwareMap.get(BNO055IMU.class, "imu");
        //BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        //parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        //imu.initialize(parameters);

        // TODO: if your hub is mounted vertically, remap the IMU axes so that the z-axis points
        // upward (normal to the floor) using a command like the following:
        //BNO055IMUUtil.remapAxes(imu, AxesOrder.XYZ, AxesSigns.NPN);

        leftFront = hardwareMap.get(DcMotorEx.class, "FLMotor");
        leftRear = hardwareMap.get(DcMotorEx.class, "BLMotor");
        rightRear = hardwareMap.get(DcMotorEx.class, "BRMotor");
        rightFront = hardwareMap.get(DcMotorEx.class, "FRMotor");
        BRMotorEncoder = hardwareMap.get(DcMotorEx.class, "LIntake");

        motors = Arrays.asList(leftFront, leftRear, rightRear, rightFront, BRMotorEncoder);

        for (DcMotorEx motor : motors) {
            if (RUN_USING_ENCODER && !motor.equals(BRMotorEncoder)) {
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        if (RUN_USING_ENCODER && MOTOR_VELO_PID != null) {
            setPIDCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, MOTOR_VELO_PID);
        }

        // TODO: reverse any motors using DcMotor.setDirection()

        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);

        // TODO: if desired, use setLocalizer() to change the localization method
        setLocalizer(new StandardTrackingWheelLocalizer(hardwareMap));
    }

    @Override
    public PIDCoefficients getPIDCoefficients(DcMotor.RunMode runMode) {
        PIDFCoefficients coefficients = leftFront.getPIDFCoefficients(runMode);
        return new PIDCoefficients(coefficients.p, coefficients.i, coefficients.d);
    }

    @Override
    public void setPIDCoefficients(DcMotor.RunMode runMode, PIDCoefficients coefficients) {
        for (DcMotorEx motor : motors) {
            if(!motor.equals(BRMotorEncoder)){
                motor.setPIDFCoefficients(runMode, new PIDFCoefficients(
                        coefficients.kP, coefficients.kI, coefficients.kD, getMotorVelocityF()
                ));
            }
        }
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        List<Double> wheelPositions = new ArrayList<>();
        for (DcMotorEx motor : motors) {
            if(!motor.equals(rightRear)){
                if(motor.equals(BRMotorEncoder)){
                    wheelPositions.set(2, encoderTicksToInches(motor.getCurrentPosition()*-1));
                } else {
                    wheelPositions.add(encoderTicksToInches(motor.getCurrentPosition()));
                }
            } else {
                wheelPositions.add(encoderTicksToInches(0));
            }
        }
        return wheelPositions;
    }

    @Override
    public List<Double> getWheelVelocities() {
        List<Double> wheelVelocities = new ArrayList<>();
        for (DcMotorEx motor : motors) {
            if(!motor.equals(rightRear)){
                if(motor.equals(BRMotorEncoder)){
                    wheelVelocities.set(2, encoderTicksToInches(motor.getVelocity()*-1));
                } else {
                    wheelVelocities.add(encoderTicksToInches(motor.getVelocity()));
                }
            } else {
                wheelVelocities.add(encoderTicksToInches(0));
            }
        }
        return wheelVelocities;
    }

    @Override
    public void setMotorPowers(double v, double v1, double v2, double v3) {
        leftFront.setPower(v);
        leftRear.setPower(v1);
        rightRear.setPower(v2);
        rightFront.setPower(v3);
    }

    @Override
    public double getRawExternalHeading() {
        return imu.getAngularOrientation().firstAngle;
    }
}
