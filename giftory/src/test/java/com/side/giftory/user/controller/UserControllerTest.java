package com.side.giftory.user.controller;

import com.side.giftory.security.RoleType;
import com.side.giftory.security.UserPrincipal;
import com.side.giftory.security.oauth2.SocialType;
import com.side.giftory.user.domain.User;
import com.side.giftory.user.dto.request.RegistUserDTO;
import com.side.giftory.user.dto.response.UserDTO;
import com.side.giftory.user.repository.UserRepository;
import com.side.giftory.user.repository.UserSocialRepository;
import com.side.giftory.user.service.UserService;
import com.side.giftory.user.service.UserSocialService;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSocialService userSocialService;
    @Autowired
    private UserSocialRepository userSocialRepository;
    @Autowired
    private EntityManager entityManager;


    private final String testId = "testId20251011";
    private final String testname = "testnametest";
    private final String testpassword = "testpassword20251011!";
    private final String testmail = "testId20251011@test.com";
    private final String testPhoneNo = "01011111111";
    private final SocialType testSocialType = SocialType.KAKAO;
    private final String testSocialId = "01011111111";
    private final RoleType testGuestRole = RoleType.ROLE_GUEST;
    private final RoleType testUserRole = RoleType.ROLE_USER;
    private final Map<String,Object> testAttribute = new HashMap<>();

    @Test
    void 자체회원가입시_새롭게_db_insert(){

        assertThat(userRepository.findByLoginId(testId).isPresent()).isFalse();
        //given
        RegistUserDTO request = RegistUserDTO.builder()
                .name(testname)
                .loginId(testId)
                .password(testpassword)
                .email(testmail)
                .phoneNo(testPhoneNo)
                .build();

        //when
        UserDTO userDTO = userService.signup(null , request);


        //then
        testTestIdExist();
    }




    @Test
    void 소셜회원가입시_새롭게_db_update확인(){


        //사전 검증
        assertThat(userRepository.findByLoginId(testId).isPresent()).isFalse();
        assertThat(userSocialRepository.findBySocialTypeAndSocialIdWithUser(testSocialType , testSocialId).isPresent()).isFalse();


        //given
        UserPrincipal testUserPrincipal = new UserPrincipal("" , "" ,"" , "" , testPhoneNo , testSocialType,testSocialId,testGuestRole,testAttribute );
        User user = userService.registerSocial(testUserPrincipal);
        testUserPrincipal.setId(user.getId());


        RegistUserDTO request = RegistUserDTO.builder()
                .name(testname)
                .loginId(testId)
                .password(testpassword)
                .email(testmail)
                .phoneNo(testPhoneNo)
                .build();

        entityManager.clear();

        //when
        UserDTO userDTO = userService.signup(testUserPrincipal , request);


        //then
        testTestIdExist();
    }

    void testTestIdExist(){
        User foundUser = userRepository.findByLoginId(testId)
                .orElseThrow(() -> new AssertionError("회원가입 후 사용자가 DB에서 조회되지 않음")); // 조회 실패 시 명확한 에러 발생

        // 2. 조회된 엔티티 필드 검증
        assertThat(foundUser.getEmail()).isEqualTo(testmail);
        assertThat(foundUser.getUsername()).isEqualTo(testname);
        assertThat(foundUser.getRole()).isEqualTo(testUserRole);
    }

}