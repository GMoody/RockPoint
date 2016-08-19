package cz.rockpoint.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalTime;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
public class CompetitionEntity implements Serializable {

    @Setter
    private static int identifier = 0;

    @Getter
    private int id;

    @Getter @Setter
    private int place;

    @Getter @Setter
    private TeamEntity team;

    @Nullable
    @Setter @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "mm:ss:SS")
    private LocalTime finish;

    @Setter @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "mm:ss:SS")
    private List<LocalTime> checkpoints;

    @Getter @Setter
    private int type;

    @Getter
    private static List<CompetitionEntity> competitions = Lists.newArrayList();

    public void setId(){
        this.id = ++identifier;
        competitions.add(this);
    }
}
