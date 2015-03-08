/*
 *
 * Supplies:  2x HiTec servo motor (HS-422); Advanced servo 8 motor board (1061); 
 * 8/8/8 Interface Kit; power supply; on/off switch; computer
 *
 * Connections: 
 *  1061 board <-> USB cable <-> computer
 *  8/8/8 interface <-> USB cable <-> computer
 *  power supply -> 1061 board
 *  switch <-> input pin  <-> wire <-> 0 (input) on 8/8/8 interface board 
 *  switch <-> ground pin  <-> wire <-> G (ground) on 8/8/8 interface board
 *  HS-422 servo motor <-> 3-wire <-> analog input port 0, 1061 board
 *  HS-422 servo motor <-> 3-wire <-> analog input on port 3, 1061 board
 */

//PIN 0 IS BOX OPENER
//PIN 3 IS ARM EXTENDER

import com.phidgets.*;
import com.phidgets.event.*;

public class UselessBox {
  public static final void main(String args[]) throws Exception {
    final int O_UP = 175;
    final int O_DOWN = 220;
    final int A_UP = 30;
    final int A_DOWN = 90;
    //represents motor controller
    AdvancedServoPhidget servo;
    servo = new AdvancedServoPhidget();
    //attach motor controller to computer
    servo.openAny();
    servo.waitForAttachment();
    //connect box-opening servo (pin 0) and arm-extending servo (pin 3)
    servo.setEngaged(0, false);
    servo.setEngaged(3, false);
    servo.setSpeedRampingOn(0, false);
    servo.setSpeedRampingOn(3, false);
    //CHECK WHAT THESE DO
    servo.setPosition(0, O_DOWN);
    servo.setPosition(3, A_DOWN);
    servo.setEngaged(0, true);
    servo.setEngaged(3, true);

    Thread.sleep(500);
                
    servo.setSpeedRampingOn(0, true);
    servo.setSpeedRampingOn(3, true);
    
    servo.setAcceleration(0,10000);
    servo.setAcceleration(3,10000);
    servo.setVelocityLimit(0, 100);
    servo.setVelocityLimit(3, 100);
    int pushCounter = 0;
    
    
    boolean yes = true;
    //represents interface kit for returning switch's position
    InterfaceKitPhidget ik = new InterfaceKitPhidget();
    //add change listener to tell if switch is on or off
    ik.addInputChangeListener(new InputChangeListener() {
      public void inputChanged(InputChangeEvent oe) {}
    });
    //open pins on interface kit and wait for attachment to computer 
    ik.openAny();
    ik.waitForAttachment();
    Thread.sleep(500);
    //turn on pin 0 on interface kit
    ik.setOutputState(0, true);
    //keep code running so that whenever switch is turned on, code will run
    while (yes) {
      //do nothing until switch is turned on
      while (ik.getInputState(0) == false) {
      }
      //execute different behaviors depending on how many times the switch has been turned on
      System.out.println("Running behavior " + (pushCounter+1));
      switch (pushCounter) {
        
        //same for first, second, fifth, eighth, eleventh times switch is turned on; just turns off switch
        case(10):
        case(7):
        case(4):
        case(1):
        case(0):
          servo.setVelocityLimit(0,200);
          servo.setVelocityLimit(3,200);
          //open box
          servo.setPosition(0, O_UP);
          Thread.sleep(200);
          //extend arm
          servo.setPosition(3, A_UP);
          Thread.sleep(250);
          //retract arm
          servo.setPosition(3, A_DOWN);
          Thread.sleep(200);
          //close box
          servo.setPosition(0,O_DOWN);
          Thread.sleep(200);
          //increment push counter, since switch was pushed
          pushCounter++; 
          break;
        //on third push, slowly open box and turn off switch
        case(2):
          //set servo speed lower
          servo.setVelocityLimit(0,70);
          servo.setVelocityLimit(3,70);
          //open box
          servo.setPosition(0,O_UP);
          Thread.sleep(800);
          //extend arm
          servo.setVelocityLimit(3,200);
          servo.setPosition(3,A_UP);
          servo.setVelocityLimit(3,30);
          Thread.sleep(1500);
          //retract arm
          servo.setPosition(3,A_DOWN);
          Thread.sleep(1500);
          //close box
          servo.setPosition(0,O_DOWN);
          Thread.sleep(1000);
          pushCounter++;
          break;
        //on fourth push, open and close box rapidly, then turn switch off
        case(3):
          servo.setVelocityLimit(0,200);
          servo.setVelocityLimit(3,200);
          Thread.sleep(200);
          //open and close box quickly 4 times
          for (int i = 0; i<4; i++) {
            //open box a bit
            servo.setPosition(0,200);
            Thread.sleep(190);
            //close box
            servo.setPosition(0,O_DOWN);
            Thread.sleep(200);
          }
          Thread.sleep(1000);
          servo.setVelocityLimit(0, 100);
          servo.setVelocityLimit(3,100);
          //open box
          servo.setPosition(0,O_UP);
          Thread.sleep(500);
          //extend arm
          servo.setPosition(3,A_UP);
          Thread.sleep(700);
          //retract arm
          servo.setPosition(3,A_DOWN);
          Thread.sleep(500);
          //close box
          servo.setPosition(0,O_DOWN);
          Thread.sleep(1000);
          pushCounter++;
          break;
        //on sixth push, wait, then slowly open box and turn off switch
        case(5):
          //do nothing
          Thread.sleep(4000);
          //slow down servos
          servo.setVelocityLimit(0,50);
          servo.setVelocityLimit(3,30);
          //open box
          servo.setPosition(0,O_UP);
          Thread.sleep(2000);
          //extend arm
          servo.setPosition(3,A_UP);
          Thread.sleep(3000);
          servo.setVelocityLimit(0,200);
          servo.setVelocityLimit(3,200);
          //retract arm
          servo.setPosition(3,A_DOWN);
          Thread.sleep(300);
          //close box
          servo.setPosition(0,O_DOWN);
          Thread.sleep(300);
          pushCounter++;
          break;     
        //on seventh push, open box, hold switch off
        case(6):
          servo.setVelocityLimit(0,100);
          servo.setVelocityLimit(3,100);
          //open box
          servo.setPosition(0,O_UP);
          Thread.sleep(700);
          //extend arm
          servo.setPosition(3,A_UP);
          //wait w/ arm extended
          Thread.sleep(5000);
          //retract arm
          servo.setPosition(3,A_DOWN);
          Thread.sleep(500);
          //close box
          servo.setPosition(0,O_DOWN);
          Thread.sleep(1000);
          pushCounter++;
          break;
        //on ninth push, open box, repeatedly push switch off
        case(8):
          servo.setVelocityLimit(0,100);
          servo.setVelocityLimit(3,100);
          //open box
          servo.setPosition(0,O_UP);
          Thread.sleep(1000);
          //extend arm
          servo.setPosition(3,A_UP);
          //wait for a bit with arm extended
          Thread.sleep(2000);
          //push switch in off position 4 times
          for (int i = 0; i<4; i++)  {
            //set high servo speed
            servo.setVelocityLimit(3,200);
            //retract arm a bit
            servo.setPosition(3,80);
            Thread.sleep(200);
            //extend arm
            servo.setPosition(3,A_UP);
            Thread.sleep(200);
          }
          servo.setVelocityLimit(3,100);
          //wait for a bit
          Thread.sleep(1500);
          //retract arm
          servo.setPosition(3,A_DOWN);
          Thread.sleep(500);
          //close box
          servo.setPosition(0,O_DOWN);
          Thread.sleep(1000);
          pushCounter++;
          break;
        //on tenth push, open box, wait, then close box, then quickly push switch off
        case(9):
          //slow down servos
          servo.setVelocityLimit(0,50);
          servo.setVelocityLimit(3,50);
          //open box
          servo.setPosition(0, O_UP);
          //wait
          Thread.sleep(8000);
          //close box
          servo.setPosition(0,O_DOWN);
          //wait
          Thread.sleep(3000);
          pushCounter++;
          break;
          //will run regular turn-switch-off behavior after above code
        //on twelfth push, go out normally but stop, go back a little bit, then press slowly
        case(11):
          servo.setVelocityLimit(0,100);
          servo.setVelocityLimit(3,100);
          //open box
          servo.setPosition(0,O_UP);
          Thread.sleep(700);
          //extend arm most of the way
          servo.setPosition(3, 60);
          Thread.sleep(2000);
          servo.setVelocityLimit(3,50);
          //pull arm back a little bit
          servo.setPosition(3,70);
          //wait
          Thread.sleep(2000);
          //slow servo way down
          servo.setVelocityLimit(3,20);
          //fully extend arm
          servo.setPosition(3,A_UP);
          Thread.sleep(1500);
          //retract arm
          servo.setPosition(3,A_DOWN);
          Thread.sleep(2200);
          //close box
          servo.setPosition(0,O_DOWN);
          Thread.sleep(1000);
          //this is the last programmed behavior, so reset pushCounter
          pushCounter = 0;
          break;
      }
    }
  }
}