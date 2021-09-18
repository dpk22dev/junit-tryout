package org.aigiri.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Account {
    private String name;
    private String phone;
    private String address;
    private int age;

    public Account( String name, String phone, int age){
        this.name = name;
        this.phone = phone;
        this.age = age;
    }

    public void validateName(){
        if( this.getName() == null || this.getName().length() == 0 ){
            throw  new RuntimeException("Invalid name");
        }
    }

    public void validateAge(){
        if( this.getAge() < 0 || this.getAge() > 150 ){
            throw  new RuntimeException("Invalid person age");
        }
    }

    public void validatephone() {
        if (this.phone.length() == 0){
            throw new RuntimeException("Phone Number Cannot be null or empty");
        }

        if (this.phone.length() != 10) {
            throw new RuntimeException("Phone Number Should be 10 Digits Long");
        }
        if (!this.phone.matches("\\d+")) {
            throw new RuntimeException("Phone Number Contain only digits");
        }
        if (!this.phone.startsWith("0")) {
            throw new RuntimeException("Phone Number Should Start with 0");
        }
    }
}
