//import java.util.concurrent.ExecutionException;
//
//////
//////class ageFinder{
//////    int age;
//////    String name;
//////    void age(){
//////        System.out.print(this.age+this.name);
//////    }
//////    void detailsFind(int age){
//////        System.out.print(" Your age is "+age);
//////    }
//////    void detailsFind(String name,int age){
//////        System.out.print("Your name is "+name+" and age is "+age);
//////    }
//////}
//////method overriding
////class Animal{
////    void sound(){
////        System.out.print(" Animal making sound ");
////    }
////}
////class Dog extends Animal{
////    void sound(){
////        System.out.println(" Dog is barking ");
////    }
////
////}
////class chatGpt extends Animal{
////    void sound(){
////        System.out.println(" Chatgpt is horning hahaha ");
////    }
////}
////
////    class Employee{
////        String name;
////        private int age;
////        public void setAge(int age){
////            this.age=age;
////            System.out.print("Employee age is "+this.age);
////
////        }
////        public int getAge(){
////            return age;
////        }
////}
//    class BankAccount{
//        private int balance;
////        private void setBalance(int balance){
////            if(balance<0){
////                System.out.println("invalid balance");
////            }else{
////                this.balance=balance;
////
////            }
////        }
//         BankAccount(int balance){
//             if(balance>=0){
//             this.balance=balance;
//             }else{
//                 throw new IllegalArgumentException("Error message");
//             }
//         }
//            public void deposit(int amount){
//                if(amount>0){
//                    this.balance=balance+amount;
//                }
//                else{
//                    System.out.println("Invalid amount");
//                }
//            }
//           public void debit(int amount){
//               if (amount<0) {
//                   System.out.println("invalid amount");
//               }
//
//               else if(amount<=balance){
//                this.balance=balance-amount;
//
//            }  else{
//                System.out.println("Insufficient balance");
//            }
//           }
//       public int getBalance(){
//            return balance;
//        }
//
//}
//public class Qaoopspractice{
//    public static void main(String[] args){
////        ageFinder age=new ageFinder();
////        age.name="rizwan";
////        age.age=15;
//////        age.age();
////        age.detailsFind(27);
////        Animal kutta =new Dog();
////        kutta.sound();
////        Animal mera_Chattu=new chatGpt();
////        mera_Chattu.sound();
////        Employee riz=new Employee();
////        riz.setAge(90);
////        riz.getAge();
//        try{
//            BankAccount sbi=new BankAccount(2300);
//            System.out.println(sbi.getBalance());
//            sbi.deposit(300);
//            sbi.debit(1000);
//            sbi.deposit(-100);
//            sbi.debit(-900);
//            System.out.println(sbi.getBalance());
//            BankAccount canera=new BankAccount(-900);
//            System.out.println(canera.getBalance());
//        }catch (IllegalArgumentException e){
//            System.out.println(e.getMessage());
//        }
//        finally {
//            System.out.println("finally block executed successfully");
//        }
//
//    }
//}
//
//
//
abstract class Animal{
    abstract void sound();
    void eat(){
        System.out.println("Eating food");
    }
    Animal(){
        System.out.println("Animal constructor called");
    }
}
class Dog extends Animal{
    void sound(){
        System.out.println("Barking");
    }
    @Override
    void eat(){
        System.out.println("Dog eating");
    }
    Dog(){
        System.out.println("Dog is eating hurre");
    }
}
public class Qaoopspractice{
    public static void main(String[] args){
        Animal bh=new Dog();
        bh.eat();
        bh.sound();

    }

}
