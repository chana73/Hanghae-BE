package com.hanghae7.alcoholcommunity.domain.party.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae7.alcoholcommunity.domain.party.entity.Party;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Please explain the class!!
 *
 * @fileName      : example
 * @author        : mycom
 * @since         : 2023-05-19
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PartyResponseDto {

	private Long partyId;
	private String title;
	private String content;
	private String hostImage;
	private String hostName;
	// @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm",timezone = "Asia/Seoul")
	// private LocalDateTime startDate;
	// @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm",timezone = "Asia/Seoul")
	// private LocalDateTime endDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm",timezone = "Asia/Seoul")
	private LocalDateTime partyDate;
	private Double latitude;
	private Double longitude;
	private Boolean processing;
	private int totalCount;
	private int currentCount;

	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public PartyResponseDto(Party party, String hostImage, String hostname) {
		this.partyId = party.getPartyId();
		this.title = party.getTitle();
		this.content = party.getContent();
		this.hostImage = hostImage;
		this.hostName = hostname;
		this.partyDate = party.getPartyDate();
		this.latitude = party.getLatitude();
		this.longitude = party.getLongitude();
		this.totalCount = party.getTotalCount();
		this.currentCount = party.getCurrentCount();
		this.createdAt = party.getCreatedAt();
		this.modifiedAt = party.getModifiedAt();
	}
}
