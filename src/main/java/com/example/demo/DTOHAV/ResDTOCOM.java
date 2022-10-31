package com.example.demo.DTOHAV;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Builder
@Table(name="Restraunt")
public class ResDTOCOM {
    @JsonProperty("id")
    @NonNull
    @Id
    @Column(name ="id")
    private int id;

    @JsonProperty("name")
    @NonNull
    @Column(name ="Name")
    private String name;

    @JsonProperty("addr")
    @NonNull
    @Column(name ="addr")
    private String addr;}

