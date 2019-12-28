public class JointState{
    private static int mask5bit = (1<<5)-1;
    private static int mask60bit = (1<<60)-1;
    private static int mask50bit = (1<<50)-1;
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
        this.l1 = l1&mask60bit;
        this.l2 = l2&mask50bit;
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
        jt.setValue(value,this);
        int returnvalue = jt.getValue(l1,l2);
        if (returnvalue!=value){
            System.out.println(jt);
            System.out.println("error: " + value + " " + returnvalue);
            jt.setValue(value,this);
        }
    }

    public void printAllValues() {
        System.out.println("all values");
        for (JointType jt : JointType.values()){
            System.out.println(jt + ": " + this.getValue(jt));
        }
    }
}
