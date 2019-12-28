import java.util.ArrayList;
import java.util.LinkedList;


public class JointStructure {


    private ArrayList<Joint> joints;

    public ArrayList<Joint> getJoints() {
        return joints;
    }

    public JointStructure(){
        joints = new ArrayList<>();
        Joint head = new Joint(4,JointType.headX);
        Joint neck = new Joint(2,JointType.neckX);
        Joint lElbow = new Joint(1,JointType.lElbowX);
        Joint rElbow = new Joint(1,JointType.rElbowX);
        Joint lFist = new Joint(1,JointType.lFistX);
        Joint rFist = new Joint(1,JointType.rFistX);
        Joint hip = new Joint(2,JointType.hipX);
        Joint lKnee = new Joint(1,JointType.lKneeX);
        Joint rKnee = new Joint(1,JointType.rKneeX);
        Joint lFoot = new Joint(1,JointType.lFootX);
        Joint rFoot = new Joint(1,JointType.rFootX);
        Joint.connect(head,neck,2,2);
        Joint.connect(lElbow,neck,2,2);
        Joint.connect(rElbow,neck,2,2);
        Joint.connect(lElbow,lFist,4,5);
        Joint.connect(rElbow,rFist,4,5);
        Joint.connect(neck,hip,4,6);
        Joint.connect(lKnee,hip,2,2);
        Joint.connect(rKnee,hip,4,5);
        Joint.connect(lFoot,lKnee,4,5);
        Joint.connect(rFoot,rKnee,4,5);
        joints.add(head);
        joints.add(neck);
        joints.add(lElbow);
                joints.add(lFist);
                joints.add(rElbow);
                joints.add(rFist);

                joints.add(hip);
                joints.add(lKnee);
                joints.add(lFoot);

        joints.add(rKnee);
        joints.add(rFoot);

    }
    public boolean isLegal(JointState js){
        boolean[] visited = new boolean[JointType.values().length];
        //breadth first
        LinkedList<Joint> queue = new LinkedList<>();
        Joint startJoint = joints.get(0);
        queue.push(startJoint);
        visited[startJoint.getJointTypeX().ordinal()]=true;
        while (!queue.isEmpty()){
            Joint current = queue.poll();
            for (Edge edges : current.getEdges()){
                Joint neighbour = edges.getOpposite(current);
                if (visited[neighbour.getJointTypeX().ordinal()])continue;
                queue.push(neighbour);
                visited[neighbour.getJointTypeX().ordinal()] = true;
                if (!edges.isLegal(js))return false;
            }
        }
        return true;
    }
    public JointState getLegalState(int headX, int headY){
        JointState js = new JointState();
        boolean[] visited = new boolean[JointType.values().length];
        //breadth first
        LinkedList<Joint> queue = new LinkedList<>();
        Joint startJoint = joints.get(0);
        js.setValue(startJoint.getJointTypeX(),headX);
        js.setValue(startJoint.getJointTypeY(),headY);
        js.printAllValues();
        queue.push(startJoint);
        visited[startJoint.getJointTypeX().ordinal()]=true;
        while (!queue.isEmpty()){
            Joint current = queue.poll();
            for (Edge edges : current.getEdges()){
                Joint neighbour = edges.getOpposite(current);
                if (visited[neighbour.getJointTypeX().ordinal()])continue;
                visited[neighbour.getJointTypeX().ordinal()] = true;
                js.printAllValues();
                if (neighbour.getJointTypeX() == JointType.rFistX){
                    System.out.println("");
                }
                edges.setNeighbourToArbitaryLegalValue(current,neighbour,js);
                System.out.println(current.getJointTypeX() + " " + js.getValue(current.getJointTypeX()) + " " + js.getValue(current.getJointTypeY()) + " "+
                         neighbour.getJointTypeX() + " " + js.getValue(neighbour.getJointTypeX())+" " +  js.getValue(neighbour.getJointTypeY()));
                js.printAllValues();
                queue.push(neighbour);

            }
        }
        return js;
    }
}
