package P3;

import java.util.List;
import java.util.ArrayList;

public class Person {
    private String name;
    private List<Person> friend;   //本人的朋友
    private static ArrayList<String> allPerson = new ArrayList<String>(); //定义全局名字表，储存本人所有朋友的名字

    public Person(String name) {
        if (!allPerson.contains(name)) {
            this.name = name;
            this.friend = new ArrayList<>();
            allPerson.add(name);
        }
    }

    public void addFriend(Person person) {  //增加本人的新朋友
        this.friend.add(person);
    }

    public String getName() {
        return this.name;
    }

    public List<Person> getFriend() {
        return this.friend;
    }
}
