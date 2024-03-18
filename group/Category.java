package cmpt276.project.group.models;

import jakarta.persistence.*;
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String ageRange;
    private int quantity;

    // Constructors, getters, setters

    public Category() {
    }

    public Category(String name, String ageRange, int quantity) {
        this.name = name;
        this.ageRange = ageRange;
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){

        this.name=name;
    }
    public String getAgeRange(){return ageRange;}
    public void setColour(String ageRange) {
        this.ageRange = ageRange;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}



