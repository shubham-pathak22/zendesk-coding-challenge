package com.zendesk.mapper;

import com.zendesk.dto.TicketDTO;
import com.zendesk.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketToTicketDTOMapper {

    TicketToTicketDTOMapper INSTANCE = Mappers.getMapper(TicketToTicketDTOMapper.class);
    TicketDTO ticketToTicketDTO(Ticket ticket);
}
