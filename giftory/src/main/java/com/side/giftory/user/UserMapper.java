package com.side.giftory.user;

import com.side.giftory.security.UserPrincipal;
import com.side.giftory.user.domain.User;
import com.side.giftory.user.dto.request.RegistUserDTO;
import com.side.giftory.user.dto.response.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // 스프링 빈으로 등록
public interface UserMapper {

    @Mapping(source = "name", target = "username") // dto의 name필드를  엔티티의 username으로 매핑
    @Mapping(target = "password", ignore = true) //  비밀번호는 서비스에서 따로 설정
    @Mapping(target = "role", ignore = true)     //  역할도 서비스에서 따로 설정
    User toEntity(RegistUserDTO dto);

    @Mapping(source = "roleType", target = "role")
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "profileUrl", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(UserPrincipal principal);

    @Mapping(source = "username", target = "name")
    @Mapping(source = "roleType", target = "role")
    UserDTO toDTO(UserPrincipal principal);

    @Mapping(source = "username", target = "name")
    UserDTO toDTO(User user);
}
