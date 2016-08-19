package cz.rockpoint.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "race")
@EqualsAndHashCode(exclude = "race_id")
public class RaceEntity implements Serializable{

    @Id
    @NotNull
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int race_id;

    @NotNull
    @Size(min=4)
    @Getter @Setter
    private String race_title;

    @NotNull
    @Column
    @Getter @Setter
    private int race_url;

    @NotNull
    @Column
    @Getter @Setter
    private String race_date;

    @NotNull
    @Column
    @Getter @Setter
    private float distance_long;

    @NotNull
    @Column
    @Getter @Setter
    private float distance_half;

    @NotNull
    @Column
    @Getter @Setter
    private float distance_short;

    @NotNull
    @Column
    @Getter @Setter
    private float elevation_long;

    @NotNull
    @Column
    @Getter @Setter
    private float elevation_half;

    @NotNull
    @Column
    @Getter @Setter
    private float elevation_short;

    @NotNull
    @Column
    @Getter @Setter
    private float chp1_long;

    @NotNull
    @Column
    @Getter @Setter
    private float chp2_long;

    @NotNull
    @Column
    @Getter @Setter
    private float chp3_long;

    @NotNull
    @Column
    @Getter @Setter
    private float chp4_long;

    @NotNull
    @Column
    @Getter @Setter
    private float chp5_long;

    @NotNull
    @Column
    @Getter @Setter
    private float chp1_half;

    @NotNull
    @Column
    @Getter @Setter
    private float chp2_half;

    @NotNull
    @Column
    @Getter @Setter
    private float chp3_half;

    @NotNull
    @Column
    @Getter @Setter
    private float chp4_half;

    @NotNull
    @Column
    @Getter @Setter
    private float chp1_short;

    @NotNull
    @Column
    @Getter @Setter
    private float chp2_short;

    @NotNull
    @Column
    @Getter @Setter
    private float chp3_short;
}
