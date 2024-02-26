package com.MJTest.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter@ToString
public class ArticleForm {
    private Long bno;
    private String title;
    private String content;
}
