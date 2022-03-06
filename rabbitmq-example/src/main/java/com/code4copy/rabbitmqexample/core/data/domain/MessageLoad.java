package com.code4copy.rabbitmqexample.core.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageLoad implements Serializable {
    private String id;
    private String data;
}
