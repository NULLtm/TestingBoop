package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mecanum.SampleMecanumDriveREV;

import java.util.ArrayList;
import java.util.List;

public abstract class DriveImplementation {
    public DcMotor FLMotor;
    public DcMotor FRMotor;
    public DcMotor BLMotor;
    public DcMotor BRMotor;
    public HardwareMap hMap;
    private SampleMecanumDriveREV driveClass;
    private Trajectory currentTrajectory;

    private DriveConstants constants = new DriveConstants();

    private DcMotorSimple.Direction[] preferredMotorDir = new DcMotorSimple.Direction[4];

    public DriveImplementation(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor, HardwareMap map){
        FLMotor = frontLeftMotor;
        FRMotor = frontRightMotor;
        BLMotor = backLeftMotor;
        BRMotor = backRightMotor;
        this.hMap = map;
        //driveClass = new SampleMecanumDriveREV(map);
    }

    /*public void setPreferredMotorDir(DcMotor motor, DcMotorSimple.Direction newMotorConfig){
        if(FLMotor.equals(motor)){
            FLMotor.setDirection(newMotorConfig);
            preferredMotorDir[0] = newMotorConfig;
        } else if(FRMotor == motor){
            FRMotor.setDirection(newMotorConfig);
            preferredMotorDir[1] = newMotorConfig;
        } else if(BLMotor == motor){
            BLMotor.setDirection(newMotorConfig);
            preferredMotorDir[2] = newMotorConfig;
        } else if(BRMotor == motor){
            BRMotor.setDirection(newMotorConfig);
            preferredMotorDir[3] = newMotorConfig;
        }
    }*/

    public TrajectoryBuilder getPathBuilder(){
        return driveClass.trajectoryBuilder();
    }

    public void followPath(){
        if(currentTrajectory != null) {
            driveClass.followTrajectorySync(currentTrajectory);
        }
    }

    public void setPath(Trajectory trajectory){
        currentTrajectory = trajectory;
    }

    public void setPreferredMotorMode(DcMotor motor, DcMotor.RunMode newMotorConfig){
        if(FLMotor == motor){
            FLMotor.setMode(newMotorConfig);
        } else if(FRMotor == motor){
            FRMotor.setMode(newMotorConfig);
        } else if(BLMotor == motor){
            BLMotor.setMode(newMotorConfig);
        } else if(BRMotor == motor){
            BRMotor.setMode(newMotorConfig);
        }
    }
    public void setPreferredMotorBrakeMode(DcMotor motor, DcMotor.ZeroPowerBehavior newMotorConfig){
        if(FLMotor == motor){
            FLMotor.setZeroPowerBehavior(newMotorConfig);
        } else if(FRMotor == motor){
            FRMotor.setZeroPowerBehavior(newMotorConfig);
        } else if(BLMotor == motor){
            BLMotor.setZeroPowerBehavior(newMotorConfig);
        } else if(BRMotor == motor){
            BRMotor.setZeroPowerBehavior(newMotorConfig);
        }
    }

    public abstract void updateDrive();

    public void stopMotors(){
        FLMotor.setPower(0);
        FRMotor.setPower(0);
        BLMotor.setPower(0);
        BRMotor.setPower(0);
    }

    public void motorDir(boolean forward){
        if(forward){
            BRMotor.setDirection(preferredMotorDir[3]);
            BLMotor.setDirection(preferredMotorDir[2]);
            FRMotor.setDirection(preferredMotorDir[1]);
            FLMotor.setDirection(preferredMotorDir[0]);
        } else {
            if(preferredMotorDir[0] == DcMotorSimple.Direction.FORWARD){
                FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            } else {
                FLMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            }
            if(preferredMotorDir[1] == DcMotorSimple.Direction.FORWARD){
                FRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            } else {
                FRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            }
            if(preferredMotorDir[2] == DcMotorSimple.Direction.FORWARD){
                BLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            } else {
                BLMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            }
            if(preferredMotorDir[3] == DcMotorSimple.Direction.FORWARD){
                BRMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            } else {
                BRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            }
        }
    }

    public void runToPos(double distanceCM, float power1, float power2, float power3, float power4){
        if(power1 < 0){
            FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            power1 *= -1;
        }
        if(power2 < 0){
            FRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            power2 *= -1;
        }
        if(power3 < 0){
            BLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            power3 *= -1;
        }
        if(power4 < 0){
            BRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            power4 *= -1;
        }
        double revs = distanceCM/constants.WHEEL_CIRCUMFERENCE;
        int ticksToRun = (int)(revs * 1680);
        runEncoder(true);
        FLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FLMotor.setTargetPosition(ticksToRun);
        FRMotor.setTargetPosition(ticksToRun);
        BLMotor.setTargetPosition(ticksToRun);
        BRMotor.setTargetPosition(ticksToRun);
        FLMotor.setPower(power1);
        FRMotor.setPower(power2);
        BLMotor.setPower(power3);
        BRMotor.setPower(power4);
        while (FLMotor.isBusy() && FRMotor.isBusy() && BLMotor.isBusy() && BRMotor.isBusy()){
            //This line was intentionally left blank
        }
        stopMotors();
        motorDir(true);
        runEncoder(false);
    }

    public void runToPos(double distanceCM, float power){

        if(power < 0){
            power *= -1;
            motorDir(false);
        }
        double revs = distanceCM/constants.WHEEL_CIRCUMFERENCE;
        int ticksToRun = (int)(revs * 1680);
        runEncoder(true);
        FLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BLMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BRMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FLMotor.setTargetPosition(ticksToRun);
        FRMotor.setTargetPosition(ticksToRun);
        BLMotor.setTargetPosition(ticksToRun);
        BRMotor.setTargetPosition(ticksToRun);
        FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        BLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        BRMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        linearDrive(power);
        while (FLMotor.isBusy() && FRMotor.isBusy() && BLMotor.isBusy() && BRMotor.isBusy()){
            //This line was intentionally left blank
        }
        stopMotors();
        runEncoder(false);
        motorDir(true);
    }

    public void runEncoder(boolean withEncoder){
        if(withEncoder) {
            FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }else{
            FLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            FRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            BLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            BRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

    }

    public void linearDrive(float power){
        FLMotor.setPower(power);
        FRMotor.setPower(power);
        BLMotor.setPower(power);
        BRMotor.setPower(power);
    }

    public void strafe (double distanceCM, float power){
        distanceCM *= 1.43*0.79;
        if(power < 0){
            power *= -1;
            runToPos(distanceCM, -power, power, power,-power);
        } else if (power > 0){
            runToPos(distanceCM, power, -power, -power,power);
        }
    }

    public void turn (int direction, double power){
        if(direction == -1){
            FLMotor.setPower(-power);
            BRMotor.setPower(power);
            FRMotor.setPower(power);
            BLMotor.setPower(-power);
        } else if (direction == 1){
            FLMotor.setPower(power);
            BRMotor.setPower(-power);
            FRMotor.setPower(-power);
            BLMotor.setPower(power);
        }
    }

    public void strafeLinear(int direction, double power){
        if(direction > 0){
            FRMotor.setPower(-power);
            FLMotor.setPower(power);
            BRMotor.setPower(power);
            BLMotor.setPower(-power);
        } else if(direction < 0){
            FRMotor.setPower(power);
            FLMotor.setPower(-power);
            BRMotor.setPower(-power);
            BLMotor.setPower(power);
        } else {
            FRMotor.setPower(0);
            FLMotor.setPower(0);
            BRMotor.setPower(0);
            BLMotor.setPower(0);
        }
    }


}
