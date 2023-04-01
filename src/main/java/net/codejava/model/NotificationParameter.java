package net.codejava.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class NotificationParameter {

	private String key;
	private String value;

}
