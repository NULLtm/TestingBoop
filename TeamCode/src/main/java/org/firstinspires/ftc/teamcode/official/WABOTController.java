package org.firstinspires.ftc.teamcode.official;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

public class WABOTController {
    private Gamepad g1;
    private Gamepad g2;

    private boolean[] checkBools = {true, true, true, true, true, true, true, true};
    private int[] checkBools2 = {0, 0, 0, 0, 0, 0, 0, 0};
    private String[] buttonStrs = {"a", "b", "x", "y", "dpad_up", "dpad_down", "dpad_left", "dpad_right"};


    public WABOTController(Gamepad g1, Gamepad g2){
        this.g1 = g1;
        this.g2 = g2;
    }

    private int findIndex(String str){
        for(int i = 0; i < buttonStrs.length; i++){
            if(buttonStrs[i].equals(str)){
                return i;
            }
        }
        return 0;
    }

    public boolean returnToggleA(Gamepad g){
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
    public boolean returnToggleB(Gamepad g){
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
    public boolean returnToggleX(Gamepad g){
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
    public boolean returnToggleY(Gamepad g){
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
    public boolean returnToggleDPadDown(Gamepad g){
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
    public boolean returnToggleDPadUp(Gamepad g){
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
    public boolean returnToggleDPadLeft(Gamepad g){
        int index = findIndex("dpad_left");
        if(g.dpad_left && checkBools[index]){
            checkBools[index] = false;
            return g.dpad_left;
        } else if(!checkBools[index] && !g.dpad_left){
            checkBools[index] = true;
        }
        return false;
    }
    public boolean returnToggleDPadRight(Gamepad g){
        int index = findIndex("dpad_right");
        if(g.dpad_right && checkBools[index]){
            checkBools[index] = false;
            return g.dpad_right;
        } else if(!checkBools[index] && !g.dpad_right){
            checkBools[index] = true;
        }
        return false;
    }
}
