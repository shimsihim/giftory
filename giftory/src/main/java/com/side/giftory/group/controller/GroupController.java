package com.side.giftory.group.controller;

import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import com.side.giftory.group.dto.request.GroupExitRequest;
import com.side.giftory.group.dto.response.GroupResponse;
import com.side.giftory.group.jwt.InviteTokenProvider;
import com.side.giftory.group.service.GroupService;
import com.side.giftory.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;
    private final InviteTokenProvider inviteTokenProvider;

    @GetMapping("/group/get-my-group") // 내가 참여한 방 모두 보기
    public List<GroupResponse> getMyGroup(@AuthenticationPrincipal UserPrincipal user) {
        
        // user.id 를 통해서 participant 를 조회 (조인을 통해서 해당 groupId의 참여자 전부 불러오기)

        throw new RuntimeException("개발중");
    }

    @PostMapping("/group/addGroup") // 새 그룹 추가 (초대 토큰 반환)
    public String makeGroup(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        //groupService.addGroup(); => 그룹 id반환

        // 나 참여

        //입장 토큰 발급
        //입장 토큰 반환

        throw new RuntimeException("개발중");
    }
    
    @GetMapping("/participant/{participatetoken}") // 참여하기
    public boolean participate(@AuthenticationPrincipal UserPrincipal userPrincipal , @PathVariable String participatetoken) {
        //단순히 bool값만 전달하며 , 목록 조회 창으로 화면단은 이동
        Long groupId = inviteTokenProvider.parseAndValidate(participatetoken);
        // 해당 그룹에 참가 처리


        
        throw new RuntimeException("개발중");
    }

    @GetMapping("/participant/{groupId}") // 참여한 인원 목록 반환
    public List<UserInfoResponse> getParticipants(@AuthenticationPrincipal UserPrincipal userPrincipal , @PathVariable Long groupId) {

        // 참여 사용자 리스트 반환 // 응답 타입 수정 필요할 수 도 있음
        throw new RuntimeException("개발중");
    }

    @DeleteMapping("/group/exit") // 그룹 나가기
    public boolean exit(@AuthenticationPrincipal UserPrincipal userPrincipal , @RequestBody GroupExitRequest groupExitRequest) {
        //
        long groupId = groupExitRequest.getGrouptId();
        throw new RuntimeException("개발중");
    }

    //그룹 내 공유된 기프티콘 목록 조회
    

    //그룹 내 알림 조회 (기프티콘 공유 , 기프티콘 사용 , 기프티콘 만료  , 인원 참여)

    //그룹 내 특정 사용자가 공유한 기프티콘 조회













}
