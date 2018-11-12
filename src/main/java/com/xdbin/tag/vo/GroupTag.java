package com.xdbin.tag.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupTag implements Serializable {

    @Id
    private Long tagId;

    private String tagName;

    private Long count;

}
