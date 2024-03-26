package cmpt276.project.group.models;

import jakarta.persistence.*;
@Entity
@Table(name = "clothes")
public class Clothing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;
    private String gender;
    private String ageRange;
    private int quantity;

    // Constructors, getters, setters

    public Clothing() {
    }

    public Clothing(String type, String gender,String ageRange, int quantity) {
        this.type =type;
        this.gender=gender;
        this.ageRange = ageRange;
        this.quantity = quantity;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){

        this.type=type;
    }
    public String getGender(){
        return gender;
    }
    public void setGender(String gender){

        this.gender=gender;
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



