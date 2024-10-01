package com.obify.hy.ims.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String locationId;
	private String sqToken;
	private String pos;
	private List<String> roles;

	public JwtResponse(String accessToken, String id, String firstName, String lastName, String email, List<String> roles, String locationId, String sqToken, String pos) {
		this.token = accessToken;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roles = roles;
		this.locationId = locationId;
		this.sqToken = sqToken;
		this.pos = pos;
	}
}
