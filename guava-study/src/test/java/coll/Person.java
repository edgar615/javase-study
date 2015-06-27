package coll;

import com.google.common.collect.ComparisonChain;

/**
 * Created by edgar on 15-6-27.
 */
public class Person implements Comparable<Person> {
    private String firstname;
    private String lastname;
    private int age;
    private String sex;

    public Person(String firstname, String lastname, int age, String sex) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.sex = sex;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    @Override
    public int compareTo(Person o) {
        return ComparisonChain.start()
        .compare(this.firstname,o.getFirstname()).
                compare(this.lastname,o.getLastname()).
                compare(this.age,o.getAge()).
                compare(this.sex,o.getSex()).result();
    }
}
