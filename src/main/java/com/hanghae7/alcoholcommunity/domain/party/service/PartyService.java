package com.hanghae7.alcoholcommunity.domain.party.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanghae7.alcoholcommunity.domain.member.entity.Member;
import com.hanghae7.alcoholcommunity.domain.party.dto.ParticipateInfoDto;
import com.hanghae7.alcoholcommunity.domain.party.dto.PartyDetailResponseDto;
import com.hanghae7.alcoholcommunity.domain.party.dto.PartyRequestDto;
import com.hanghae7.alcoholcommunity.domain.party.dto.PartyResponseDto;
import com.hanghae7.alcoholcommunity.domain.party.entity.Party;
import com.hanghae7.alcoholcommunity.domain.party.entity.PartyParticipate;
import com.hanghae7.alcoholcommunity.domain.party.repository.PartyParticipateRepository;
import com.hanghae7.alcoholcommunity.domain.party.repository.PartyRepository;

import lombok.RequiredArgsConstructor;

/**
 * Please explain the class!!
 *
 * @fileName      : example
 * @author        : mycom
 * @since         : 2023-05-19
 */

@RequiredArgsConstructor
@Service
public class PartyService {

	private final PartyRepository partyRepository;
	private final PartyParticipateRepository partyParticipateRepository;

	// 모임 게시글 등록
	@Transactional
	public ResponseEntity<Void> creatParty(PartyRequestDto partyRequestDto, Member member) {

		Party party = partyRequestDto.toEntity(member);
		party.addCurrentCount();
		partyRepository.save(party);

		// 모임 참가인원 자기자신은 +1
		PartyParticipate partyParticipate = PartyParticipate.builder()
			.party(party)
			.member(member)
			.build();

		partyParticipateRepository.save(partyParticipate);

		return ResponseEntity.ok(null);
	}

	// 모임 전체조회



	// 모임 상세조회
	@Transactional
	public ResponseEntity<PartyDetailResponseDto> getParty(Long partyId, Member member) {

		// User  유효성 검사
		Party party = partyRepository.findById(partyId).orElseThrow(
			()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		List<PartyParticipate> participates = party.getPartyParticipates();
		List<ParticipateInfoDto> participateInfoDtoList = new ArrayList<>();

		for(PartyParticipate participate: participates) {
			participateInfoDtoList.add(new ParticipateInfoDto(participate.getMember().getMemberId()));
		}

		PartyResponseDto partyResponseDto = new PartyResponseDto(party);
		return ResponseEntity.ok(new PartyDetailResponseDto(participateInfoDtoList, partyResponseDto));
	}

	// 모임 게시글 수정





	// 모임 게시글 삭제
	@Transactional
	public ResponseEntity<Void> deleteParty(Long partyId, Member member) {

		Party party = partyRepository.findById(partyId).orElseThrow(
			() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		if (!party.getMember().getMemberName().equals(member.getMemberName())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			partyRepository.delete(party);
			return ResponseEntity.ok(null);
		}
	}


}
