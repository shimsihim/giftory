package com.side.giftory.notification.domain;

public enum NotificationType {
    EXPIRATION_DUE("EXPIRATION_DUE" , "유효기간 임박"), // 유효기간 임박
    EXPIRATION("EXPIRATION" , "유효기간 만료"), // 유효기간 임박
    NEW_GIFTICON("NEW_GIFTICON" , "새로운 기프티콘 공유"),   // 새 기프티콘 공유
    DELETE_GIFICON("DELETE_GIFICON" , "기프티콘 삭제"),
    GIFTICON_USED_ALL("GIFTICON_USED_ALL" , "기프티콘 사용 완료"),
    GIFTICON_USED("GIFTICON_USED" , "기프티콘 사용");   // 기프티콘 사용됨
    

    private final String name;
    private final String description;

    private NotificationType(String name, String description) {
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