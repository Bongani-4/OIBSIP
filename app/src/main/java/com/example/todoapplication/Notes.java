package  com.example.todoapplication;


import java.io.Serializable;

// Notes.java
public class Notes  implements Serializable {
    private String Heading;
    private String note;



    public Notes()
    {}

    public Notes(String Heading, String note) {
        this.Heading = Heading;
        this.note = note;
    }

    // Getter methods
    public String getHeading() {
        return Heading;
    }

    public String getNote() {
        return note;
    }


}
