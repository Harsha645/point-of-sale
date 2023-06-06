package com.pos.kuppiya.pointofsale.service.impl;

import com.pos.kuppiya.pointofsale.dto.ItemDTO;
import com.pos.kuppiya.pointofsale.dto.paginated.PaginatedResponseItemDTO;
import com.pos.kuppiya.pointofsale.dto.request.ItemSaveRequestDTO;
import com.pos.kuppiya.pointofsale.entity.Item;
import com.pos.kuppiya.pointofsale.exception.EntryDuplicationException;
import com.pos.kuppiya.pointofsale.repository.ItemRepo;
import com.pos.kuppiya.pointofsale.service.ItemService;
import com.pos.kuppiya.pointofsale.util.mappers.ItemMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceIMPL implements ItemService {
    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public String addItem(ItemSaveRequestDTO itemSaveRequestDTO) {
//        Item item = itemMapper.requestDtoToEntity(itemSaveRequestDTO);
        Item item = modelMapper.map(itemSaveRequestDTO, Item.class);
        item.setActiveState(true);

        if (!itemRepo.existsById(item.getItemId())) {
            itemRepo.save(item);
            return itemRepo.save(item).getItemName();
        } else {
            throw new EntryDuplicationException("Already in the database");
        }

    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<Item> getItems = itemRepo.findAll();
        List<ItemDTO> itemDTOList = new ArrayList<>();

        List<ItemDTO> itemDTOS = modelMapper.map(getItems, new TypeToken<List<ItemDTO>>() {
                }.getType()
        );
        return itemDTOS;

    }

    @Override
    public List<ItemDTO> getAllItemsByState(boolean states) {
        List<Item> getItems = itemRepo.findAllByActiveState(states);

        List<ItemDTO> itemDTOS = modelMapper.map(
                getItems, new TypeToken<List<ItemDTO>>() {
                }.getType()
        );

        return itemDTOS;
    }

    @Override
    public int getAllItemCount() {
        int count = itemRepo.countAllByActiveStateEquals(true);

        return count;
    }

    @Override
    public PaginatedResponseItemDTO getAllItemsPaginated(int page, int size) {
        Page<Item> getAllByPaginated = itemRepo.findAll(PageRequest.of(page, size));
        System.out.println(getAllByPaginated);

        return new PaginatedResponseItemDTO(
                itemMapper.pageToPaginatedList(getAllByPaginated),
                itemRepo.count()
        );
    }

    @Override
    public PaginatedResponseItemDTO getAllActiveItemsPaginated(int page, int size, boolean activeState) {
        Page<Item> getAllActiveItemsByPaginated = itemRepo.findAllByActiveStateEquals(activeState,PageRequest.of(page, size));

        return new PaginatedResponseItemDTO(
                itemMapper.pageToPaginatedList(getAllActiveItemsByPaginated),
                itemRepo.countAllByActiveStateEquals(activeState)
        );
    }
}
