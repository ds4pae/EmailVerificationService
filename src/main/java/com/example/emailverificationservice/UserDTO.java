package com.example.emailverificationservice;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String password;

    public @NotEmpty String getName() {
        return name;
    }

    public void setName(@NotEmpty String name) {
        this.name = name;
    }

    public @NotEmpty String getNickname() {
        return nickname;
    }

    public void setNickname(@NotEmpty String nickname) {
        this.nickname = nickname;
    }

    public @NotEmpty String getPhone() {
        return phone;
    }

    public void setPhone(@NotEmpty String phone) {
        this.phone = phone;
    }

    public @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }

    public User toEntity() {
        User user = new User();
        user.setName(this.name);
        user.setNickname(this.nickname);
        user.setPhone(this.phone);
        user.setPassword(this.password);
        return user;
    }
}
