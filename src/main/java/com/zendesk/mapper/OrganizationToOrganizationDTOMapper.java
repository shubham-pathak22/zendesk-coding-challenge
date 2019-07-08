package com.zendesk.mapper;

import com.zendesk.dto.OrganizationDTO;
import com.zendesk.model.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrganizationToOrganizationDTOMapper {

    OrganizationToOrganizationDTOMapper INSTANCE = Mappers.getMapper(OrganizationToOrganizationDTOMapper.class);
    OrganizationDTO organizationToOrganizationDTO(Organization organization);
}
