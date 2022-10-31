package com.example.demo.DTOHAV;

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
public class OrderDTOCOM {
    @JsonProperty("resId")
    @NonNull
    @Id
    @Column(name ="resId")
    private int resId;

    @JsonProperty("ingreId")
    @NonNull
    @Id
    @Column(name ="ingreId")
    private int ingreId;

    @JsonProperty("drinkId")
    @NonNull
    @Id
    @Column(name ="drinkId")
    private int drinkId;

    @JsonProperty("quantity")
    @NonNull
    @Column(name ="quantity")
    private int quantity;

    @JsonProperty("type")
    @NonNull
    @Column(name ="type")
    private String type;

    @JsonProperty("date")
    @NonNull
    @Column(name ="date")
    private Date date;

}
