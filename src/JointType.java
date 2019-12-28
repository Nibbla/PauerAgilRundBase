public enum JointType {
    headX(0), headY(5), neckX(10), neckY(15), lElbowX(20), lElbowY(25),
    lFistX(30), lFistY(35), rElbowX(40), rElbowY(45), rFistX(50), rFistY(55),
    hipX(60), hipY(65), lKneeX(70), lKneeY(75), lFootX(80), lFootY(85),
    rKneeX(90), rKneeY(95), rFootX(100), rFootY(105);

    private final int index;
    private static long mask5bit = (1<<5)-1;
    private long inverseMask = 0;


    JointType(int index) {
        this.index = index;
        if (index<60){
            for (int i = 0; i < 60; i++) {

                inverseMask<<=1;
                if (i < index || i > index+4)inverseMask++;
                //System.out.println("msk: " + Long.toBinaryString(inverseMask));

            }
            inverseMask = Long.reverse(inverseMask)>>4;
        }else {
            for (int i = 0; i < 50; i++) {

                inverseMask<<=1;
                if (i < index-60 || i > index-60+4)inverseMask++;
            }
            inverseMask = Long.reverse(inverseMask)>>14;
        }

        System.out.println("index: " + index);
        System.out.println("msk: " + Long.toBinaryString(inverseMask));

    }

    public int getValue(long l1, long l2) {
        long val;
        if (index<60){
            val = l1>>index;
        }else {
            val = l2>>(index-60);
        }
        val &= mask5bit;
        return (int) val;
    }



    public void setValue(int valueSrc, JointState jointState) {
        long value = valueSrc;
        //System.out.println("val: " + Long.toBinaryString(value) + " index:" + index);
        if (index<60){
            long addition = value<<index;
           // System.out.println("add: " + Long.toBinaryString(addition));


           // System.out.println(Long.toBinaryString(jointState.l1));
            //System.out.println("msk: " + Long.toBinaryString(inverseMask));
            jointState.l1&=inverseMask;
           // System.out.println(Long.toBinaryString(jointState.l1));
            jointState.l1+= addition;

           // System.out.println(Long.toBinaryString(jointState.l1));
        }else {
            long addition = value<<(index-60);
           // System.out.println(Long.toBinaryString(addition));


           // System.out.println(Long.toBinaryString(jointState.l2));
            jointState.l2&=inverseMask;
           // System.out.println(Long.toBinaryString(jointState.l2));
            jointState.l2+= addition;
           // System.out.println(Long.toBinaryString(jointState.l2));
        }
    }
}
