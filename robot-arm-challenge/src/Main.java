import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            RobotArm robotArm = new RobotArm(10, 10);
        } catch (Exception e) {
            System.out.println("You probably should start by implementing the constructor :)");

        }
        //testing certain methods
        RobotArm test = new RobotArm(2,2);
        Content a = new Content("asddfgs","asdasdas",'g',3,(double) 10,ContentType.BLOOD);
        Content b = new Content("asddfgs","asdasdas",'t',6,(double) 8,ContentType.BLOOD);
        Content c = new Content("asddfgs","asdasdas",'e',5,(double) 5,ContentType.BLOOD);
        Content d = new Content("asddfgs","asdasdas",'f',8,(double) 9,ContentType.BLOOD);
        List<Content> e = new ArrayList<>();
        e.add(a);
        e.add(b);
        e.add(c);
        e.add(d);
        test.fillLocationWithItems(e, FillingStrategy.COLUMN_WISE);
        Order order = test.fulfilOrderWithMinimalCostForVolumeAndType(20, ContentType.BLOOD);
        System.out.println(order.getPrice());
        System.out.println(test.grid[0][0].getVolume());
       // System.out.println(test.grid[0][0].getVolume());
        System.out.println("Congratulations, you run the application");

    }

}
