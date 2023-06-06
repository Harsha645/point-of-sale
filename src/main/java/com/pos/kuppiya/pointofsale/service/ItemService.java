package com.pos.kuppiya.pointofsale.service;

import com.pos.kuppiya.pointofsale.dto.ItemDTO;
import com.pos.kuppiya.pointofsale.dto.paginated.PaginatedResponseItemDTO;
import com.pos.kuppiya.pointofsale.dto.request.ItemSaveRequestDTO;

import java.util.List;

public interface ItemService {
    String addItem(ItemSaveRequestDTO itemSaveRequestDTO);

    List<ItemDTO> getAllItems();

    List<ItemDTO> getAllItemsByState(boolean states);

    int getAllItemCount();

    PaginatedResponseItemDTO getAllItemsPaginated(int page, int size);

    PaginatedResponseItemDTO getAllActiveItemsPaginated(int page, int size, boolean activeState);
}
