package com.side.giftory.gifticon.domain;

public enum GifticonType {
    MULTIPLE_USE("금액권", "해당 금액내에서 여러번 구매 가능"), // 직접 구매한 기프티콘
    SINGLE_USE("1회권" , "금액 상관없이 1번 사용 가능한 타입");   // 일회성으로 받은 기프티콘

    private final String name;
    private final String description;
    private GifticonType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
}
