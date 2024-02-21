package com.eviden.meetingroom.security.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {

	private String username;
    private String password;
}
