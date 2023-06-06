package com.pos.kuppiya.pointofsale.util.mappers;

import com.pos.kuppiya.pointofsale.dto.ItemDTO;
import com.pos.kuppiya.pointofsale.dto.request.ItemSaveRequestDTO;
import com.pos.kuppiya.pointofsale.entity.Item;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item requestDtoToEntity(ItemSaveRequestDTO itemSaveRequestDTO);
    List<ItemDTO> pageToPaginatedList(Page<Item> page);
}
