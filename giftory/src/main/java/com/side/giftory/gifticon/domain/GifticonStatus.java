package com.side.giftory.gifticon.domain;

public enum GifticonStatus {
    AVAILABLE("AVAILABLE" , "사용 가능"), // 사용 가능
    USED("USED" , "사용 완료"),      // 사용 완료
    EXPIRED("EXPIRED" , "기간 만료");    // 기간 만료

    private final String name;
    private final String description;

    private GifticonStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}