package com.pro.stagelink.entity;
public enum MemberState {
   ACTIVE("0"),
   BLOCKED("1"),
   DELETED("2");
   private final String code;
   MemberState(String code) {
       this.code = code;
   }
   public String getCode() {
       return code;
   }
}

