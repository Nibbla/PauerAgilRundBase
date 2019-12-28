import javax.swing.*;
import java.util.ArrayList;

public class JointState{
    private static int mask5bit = (1<<5)-1;
    private static long mask60bit = (1l<<60)-1;
    private static long mask50bit = (1l<<50)-1;
    long l1;
    long l2;


    public int getValue(JointType jt){
        return jt.getValue(l1,l2);
    }
    public JointState(int... values) {
        int i;
        for (i = 0; i < values.length&&i<12; i++) {
            l1 <<= 5;
            l1 += values[i]&mask5bit;
        }
        for (; i < values.length&&i<24; i++) {
            l2 <<= 5;
            l2 += values[i]&mask5bit;
        }

    }
    public JointState(long l1, long l2){
        //System.out.println("msk60: " + Long.toBinaryString(mask60bit) );
        //System.out.println("msk50: " + Long.toBinaryString(mask50bit) );
       // System.out.println("val: " + Long.toBinaryString(l1) );
       // System.out.println("val: " + Long.toBinaryString(l2) );
        this.l1 = l1&mask60bit;
        this.l2 = l2&mask50bit;
       // System.out.println("val: " + Long.toBinaryString(this.l1) );
       // System.out.println("val: " + Long.toBinaryString(this.l2) );
    }

    public int hashCode(){
        return (int) l1;
    }

    public boolean equals(Object o){
        if (!(o instanceof JointState))return false;
        JointState j2 = (JointState)o;
        return this.l1 == j2.l1 && this.l2 == j2.l2;
    }


    public void setValue(JointType jt, int value) {
        ArrayList<Integer> before = this.getAllValues();
        jt.setValue(value,this);
        ArrayList<Integer> after = this.getAllValues();
        int counter = 0;
        for (int i = 0; i < before.size(); i++) {
            if (before.get(i)!=after.get(i))counter++;
        }
        int returnvalue = jt.getValue(l1,l2);
        if (returnvalue!=value){
            System.out.println(jt);
            System.out.println("error: " + value + " " + returnvalue);
            jt.setValue(value,this);
        }
        if (counter>1){
            System.out.println("error: " + value + " " + returnvalue);
        }
    }

    private ArrayList<Integer> getAllValues() {
        ArrayList<Integer> returnArray = new ArrayList<>();
        for (JointType jt : JointType.values()){
            returnArray.add(getValue(jt));
        }
        return returnArray;
    }

    public void printAllValues() {
        System.out.println("all values");
        for (JointType jt : JointType.values()){
            System.out.println(jt + ": " + this.getValue(jt));
        }
    }

    public void printAllValues(JLabel textLabel) {
        StringBuilder sb = new StringBuilder();
        sb.append("all values\n");
        for (JointType jt : JointType.values()){
            sb.append(jt + ": " + this.getValue(jt)+"\n");
        }
        String myString = sb.toString();
        textLabel.setText("<html>" + myString.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
    }

    public ArrayList<JointState> getNeighbouringStates(JointStructure js) {
        ArrayList<JointState> neighbouringStates = new ArrayList<>();
        JointType[] jtValues = JointType.values();
        for (int i = 0; i < jtValues.length; i=i+2) {
            int valX = getValue(jtValues[i]);
            int valY = getValue(jtValues[i]);
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx==0 && dy == 0)continue;
                    JointState copy = this.copy();
                    int newX = valX+dx;
                    int newY = valY+dy;
                    if (newX<0||newX>31||newY <0||newY>31)continue;
                    copy.setValue(jtValues[i],newX);
                    copy.setValue(jtValues[i+1],newY);
                    //System.out.println("copyVals: ");
                    //copy.printAllValues();
                    if (js.isLegal(copy)) {
                        //System.out.println("found legal neighbouring state");
                        neighbouringStates.add(copy);
                    }else {

                    }

                }
            }


        }

        return neighbouringStates;
    }

    private JointState copy() {
        return new JointState(l1,l2);
    }
}
