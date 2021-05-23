package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class FriendshipGraph {
    public List<Person> allpeople;  //储存所有的人对象
    public List<String> allname;    //储存所有已经存在的名字

    public FriendshipGraph() {
        allpeople = new ArrayList<Person>();
        allname = new ArrayList<String>();
    }

    public void addVertex(Person people) {
        String name = people.getName();
        if (allname.contains(name)) {
            System.out.println("此名已存在，重复");
        } else {
            allname.add(name);
            allpeople.add(people);
        }
    }

    public void addEdge(Person a, Person b) {
        a.addFriend(b);
    }

    public int getDistance(Person c1, Person c2) {
        if (c1 == c2) {
            return 0;
        }

        Map<Person, Integer> theway = new HashMap<>();
        Queue<Person> queue = new LinkedList<>();
        queue.offer(c1);
        theway.put(c1, 0);
        int distance;
        while (!queue.isEmpty()) {
            Person top = queue.poll();
            distance = theway.get(top);
            List<Person> friends = top.getFriend();
            for (Person m : friends) {
                if (!theway.containsKey(m)) {
                    theway.put(m, distance + 1);
                    queue.offer(m);
                    if (m == c2) {
                        return theway.get(c2);
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String args[]) {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println(graph.getDistance(rachel, ross)); //should print 1
        System.out.println(graph.getDistance(rachel, ben)); //should print 2
        System.out.println(graph.getDistance(rachel, rachel)); //should print 0
        System.out.println(graph.getDistance(rachel, kramer)); //should print -1
    }
}
