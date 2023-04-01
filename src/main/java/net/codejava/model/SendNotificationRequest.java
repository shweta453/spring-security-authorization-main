package net.codejava.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SendNotificationRequest {

	private String templateCode;
	private List<NotificationParameter> parameters;

}
