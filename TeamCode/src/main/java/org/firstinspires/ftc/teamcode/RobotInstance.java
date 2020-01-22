package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.official.ImuController;
import org.firstinspires.ftc.teamcode.official.VuforiaController;
import org.firstinspires.ftc.teamcode.official.WABOTController;
import org.firstinspires.ftc.teamcode.official.WABOTHardware;
import org.firstinspires.ftc.teamcode.official.WABOTOmniDrive;

import java.lang.reflect.InvocationTargetException;

enum RobotState{
    INITIALIZING, RUNNING, ERROR, STOPPED, PREINIT;
}

public class RobotInstance {
    private final String NAME;
    private ImuController imuController = null;
    private VuforiaController vuforiaController = null;
    private WABOTHardware mapConfig = null;
    private WABOTController controller1 = null;
    private WABOTController controller2 = null;
    private RobotState state = RobotState.PREINIT;
    private Telemetry console = null;
    private WABOTOmniDrive drive;

    public RobotInstance(String robotName){
        NAME = robotName;
    }

    public WABOTHardware getConfig(){
        return mapConfig;
    }

    public WABOTController getController1(){
        return controller1;
    }
    public WABOTController getController2(){
        return controller2;
    }

    public RobotState getState(){
        return state;
    }

    public WABOTOmniDrive getDriveController(){
        return drive;
    }

    public VuforiaController getVuforiaController(){
        return vuforiaController;
    }

    public ImuController getImuController(){
        return imuController;
    }

    public void printToConsole(String message){
        if(console != null){
            console.addLine(NAME+": "+message);
            if(mapConfig.isAutonomous){
                console.update();
            }
        }
    }

    public void printToConsole(String message, Object value){
        if(console != null){
            console.addData(NAME+": "+message, value);
            if(mapConfig.isAutonomous){
                console.update();
            }
        }
    }

    public void update(){
        if(drive != null) {
            drive.updateDrive();
        }
    }

    public String getName(){
        return NAME;
    }

    public void setParameters(RobotParameters parameters){

        this.drive = new WABOTOmniDrive(parameters.map.get(DcMotor.class, "FLMotor"), parameters.map.get(DcMotor.class, "FRMotor"), parameters.map.get(DcMotor.class, "BLMotor"), parameters.map.get(DcMotor.class, "BRMotor"), this, parameters.map);
        this.mapConfig = new WABOTHardware(parameters.map, parameters.isAutonomous, this);
        this.controller1 = new WABOTController(parameters.gamepad1);
        this.controller2 = new WABOTController(parameters.gamepad2);
        this.console = parameters.console;

        if(parameters.useRevIMU){
            imuController = new ImuController(mapConfig.getInternalMap());
        }
        if(parameters.useVuforia){
            vuforiaController = new VuforiaController(parameters.vuforiaKey, parameters.isWebcam, mapConfig.getInternalMap(), parameters.showVuforiaDisplay, mapConfig);
        }

        printToConsole(NAME+" has initialized successfully!");
        state = RobotState.INITIALIZING;
    }
}
