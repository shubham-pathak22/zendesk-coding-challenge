package com.zendesk.mapper;

import com.zendesk.dto.UserDTO;
import com.zendesk.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserToUserDTOMapper {

    UserToUserDTOMapper INSTANCE = Mappers.getMapper(UserToUserDTOMapper.class);
    UserDTO userToUserDTO(User user);
}
