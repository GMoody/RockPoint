package cz.rockpoint.model;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public class PersonEntity implements Serializable {

    @Setter
    private static int identifier = 0;

    @Getter @Setter(AccessLevel.PRIVATE)
    private int id;

    @Getter @Setter(AccessLevel.PRIVATE)
    private int no;

    @Getter @Setter(AccessLevel.PRIVATE)
    private String fname;

    @Getter @Setter(AccessLevel.PRIVATE)
    private String lname;

    @Getter
    private static List<PersonEntity> persons = Lists.newArrayList();

    public PersonEntity(int no, String fname, String lname){
        if(!fname.isEmpty()){
            this.id = ++identifier;
            this.no = no;
            this.fname = fname;
            this.lname = lname;
            persons.add(this);
        }
    }
}
