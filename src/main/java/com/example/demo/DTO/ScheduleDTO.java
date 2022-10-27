package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@Data
@Entity
@ToString(doNotUseGetters = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Builder
@Table(name="Schedule")
public class ScheduleDTO {
    @JsonProperty("id")
    @NonNull
    @Id
    @Column(name ="id")
    private int id;

    @JsonProperty("day")
    @NonNull
    @Id
    @Column(name ="day")
    private String day;

    @JsonProperty("open")
    @NonNull
    @Column(name ="open")
    private Time open;

    @JsonProperty("close")
    @NonNull
    @Column(name ="close")
    private Time close;
}
