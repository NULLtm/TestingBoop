package org.firstinspires.ftc.teamcode.official;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a class that expands upon the 'functionality' of the Gamepad class. It mainly just adds toggleable features for buttons!
 */
public class WABOTController {
    private Gamepad g;

    private boolean[] checkBools = {true, true, true, true, true, true, true, true};
    private int[] checkBools2 = {0, 0, 0, 0, 0, 0, 0, 0};
    private String[] buttonStrs = {"a", "b", "x", "y", "dpad_up", "dpad_down", "dpad_left", "dpad_right"};


    public WABOTController(Gamepad g1){
        this.g = g1;
    }

    private int findIndex(String str){
        for(int i = 0; i < buttonStrs.length; i++){
            if(buttonStrs[i].equals(str)){
                return i;
            }
        }
        return 0;
    }

    public boolean a(){
        return g.a;
    }
    public boolean b(){
        return g.b;
    }
    public boolean x(){
        return g.x;
    }
    public boolean y(){
        return g.y;
    }
    public boolean dpad_up(){
        return g.dpad_up;
    }
    public boolean dpad_down(){
        return g.dpad_down;
    }
    public boolean dpad_right(){
        return g.dpad_right;
    }
    public boolean dpad_left(){
        return g.dpad_left;
    }
    public double left_stick_y(){
        return g.left_stick_y;
    }
    public double left_stick_x(){
        return g.left_stick_x;
    }
    public double right_stick_x(){
        return g.right_stick_x;
    }
    public double right_stick_y(){
        return g.right_stick_y;
    }
    public boolean left_bumper(){
        return g.left_bumper;
    }
    public boolean right_bumper(){
        return g.right_bumper;
    }
    public double left_trigger(){
        return g.left_trigger;
    }
    public double right_trigger(){
        return g.right_trigger;
    }

    public boolean returnToggleA(){
        int index = findIndex("a");
        if(checkBools2[index] == 0 && g.a){
            checkBools2[index] = 1;
        } else if(!g.a && checkBools2[index] == 1){
            if(checkBools[index]){
                checkBools[index] = false;
            } else {
                checkBools[index] = true;
            }
            checkBools2[index] = 0;
        }
        return checkBools[index];
    }
    public boolean returnToggleB(){
        int index = findIndex("b");
        if(checkBools2[index] == 0 && g.b){
            checkBools2[index] = 1;
        } else if(!g.b && checkBools2[index] == 1){
            if(checkBools[index]){
                checkBools[index] = false;
            } else {
                checkBools[index] = true;
            }
            checkBools2[index] = 0;
        }
        return checkBools[index];
    }
    public boolean returnToggleX(){
        int index = findIndex("x");
        if(checkBools2[index] == 0 && g.x){
            checkBools2[index] = 1;
        } else if(!g.x && checkBools2[index] == 1){
            if(checkBools[index]){
                checkBools[index] = false;
            } else {
                checkBools[index] = true;
            }
            checkBools2[index] = 0;
        }
        return checkBools[index];
    }
    public boolean returnToggleY(){
        int index = findIndex("y");
        if(checkBools2[index] == 0 && g.y){
            checkBools2[index] = 1;
        } else if(!g.y && checkBools2[index] == 1){
            if(checkBools[index]){
                checkBools[index] = false;
            } else {
                checkBools[index] = true;
            }
            checkBools2[index] = 0;
        }
        return checkBools[index];
    }
    public boolean returnToggleDPadDown(){
        int index = findIndex("dpad_down");
        if(checkBools2[index] == 0 && g.dpad_down){
            checkBools2[index] = 1;
        } else if(!g.dpad_down && checkBools2[index] == 1){
            if(checkBools[index]){
                checkBools[index] = false;
            } else {
                checkBools[index] = true;
            }
            checkBools2[index] = 0;
        }
        return checkBools[index];
    }
    public boolean returnToggleDPadUp(){
        int index = findIndex("dpad_up");
        if(checkBools2[index] == 0 && g.dpad_up){
            checkBools2[index] = 1;
        } else if(!g.dpad_up && checkBools2[index] == 1){
            if(checkBools[index]){
                checkBools[index] = false;
            } else {
                checkBools[index] = true;
            }
            checkBools2[index] = 0;
        }
        return checkBools[index];
    }
    public boolean returnToggleDPadLeft(){
        int index = findIndex("dpad_left");
        if(checkBools2[index] == 0 && g.dpad_left){
            checkBools2[index] = 1;
        } else if(!g.dpad_left && checkBools2[index] == 1){
            if(checkBools[index]){
                checkBools[index] = false;
            } else {
                checkBools[index] = true;
            }
            checkBools2[index] = 0;
        }
        return checkBools[index];
    }
    public boolean returnToggleDPadRight(){
        int index = findIndex("dpad_right");
        if(checkBools2[index] == 0 && g.dpad_right){
            checkBools2[index] = 1;
        } else if(!g.dpad_right && checkBools2[index] == 1){
            if(checkBools[index]){
                checkBools[index] = false;
            } else {
                checkBools[index] = true;
            }
            checkBools2[index] = 0;
        }
        return checkBools[index];
    }
}
