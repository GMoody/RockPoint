package cz.rockpoint.model;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public class CategoryEntity implements Serializable{

    @Setter
    private static int identifier = 0;

    @Getter @Setter
    private int id;

    @Getter @Setter(AccessLevel.PRIVATE)
    private String title;

    @Getter
    private static List<CategoryEntity> categories = Lists.newArrayList();

    public CategoryEntity(String title){
        this.id = ++identifier;
        this.title = title;
        categories.add(this);
    }
}
