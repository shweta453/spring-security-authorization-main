package net.codejava.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "users_roles")
public class UserRoles {
    private int userId;
    private int roleId;

}
