package ch06;

class Person extends Object{
    String name;
    int age;

    Person(String name, int age){
        super();
        this.name = name;
        this.age  = age;
    }

    void displayInfo(){
        System.out.println("Name: " + name);
        System.out.println("Age: "+ age);
    }
}


class Employee extends Person{

    String department;

    Employee(String name, int age, String department){
        super(name,age);
        this.department = department;
    }
    /*오버라이딩*/
    @Override
    void displayInfo(){
        super.displayInfo();
        System.out.println("Department: "+department);
    }
}

public class ConstructorEx4 {
    public static void main(String[] args) {
        Person p1 = new Person("강호동",23);
        p1.displayInfo();
        System.out.println("-----------------------------------------");
        Employee e1 = new Employee("홍길동",23,"개발자");
        e1.displayInfo();
    }
}
