package cmpt276.project.group.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    private String name;
    private String username;
    private String password;
    private String empCode;

    public Users(){
        
    }
   

    public Users(String name,String username, String password,String empCode) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.empCode = empCode;
     
    }
public String getName(){
        return name;
}
public void setName(String name){
        this.name=name;
}
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  public String getEmpCode() {
        return empCode;
    }

   public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
    
}
