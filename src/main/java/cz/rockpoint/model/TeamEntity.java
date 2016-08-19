package cz.rockpoint.model;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public class TeamEntity implements Serializable {

    @Setter
    private static int identifier = 0;

    @Getter @Setter
    private int id;

    @Getter @Setter(AccessLevel.PRIVATE)
    private String title;

    @Getter @Setter(AccessLevel.PRIVATE)
    private CategoryEntity category;

    @Getter @Setter
    private List<PersonEntity> participants;

    @Getter
    private static List<TeamEntity> teams = Lists.newArrayList();

    public TeamEntity(String title, CategoryEntity category, List<PersonEntity> participants){
            this.id = ++identifier;
            this.title = title;
            this.category = category;
            this.participants = participants;
            teams.add(this);
    }
}
