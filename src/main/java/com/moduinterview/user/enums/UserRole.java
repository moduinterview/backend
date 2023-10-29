package com.moduinterview.user.enums;

public enum UserRole {
  ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER"), ROLE_ENTERPRISE("ROLE_ENTERPRISE");

  final String stringRoles;

  UserRole(String stringRoles) {
    this.stringRoles = stringRoles;
  }

  public String getStringRoles() {
    return stringRoles;
  }


}
