package com.pos.kuppiya.pointofsale.controller;

import com.pos.kuppiya.pointofsale.dto.CustomerDTO;
import com.pos.kuppiya.pointofsale.dto.ItemDTO;
import com.pos.kuppiya.pointofsale.dto.paginated.PaginatedResponseItemDTO;
import com.pos.kuppiya.pointofsale.dto.request.ItemSaveRequestDTO;
import com.pos.kuppiya.pointofsale.service.ItemService;
import com.pos.kuppiya.pointofsale.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;

@RestController
@RequestMapping("api/v1/item")
@CrossOrigin
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> saveItem(@RequestBody ItemSaveRequestDTO itemSaveRequestDTO){
        String id = itemService.addItem(itemSaveRequestDTO);

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(201,id + " item successfully saved",id),
                HttpStatus.CREATED
        );
    }

    @GetMapping(path =
            {"/get-all-items"}
    )
    public ResponseEntity<StandardResponse> getAllItems() {
        List<ItemDTO> allItems = itemService.getAllItems();

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, " success",allItems),
                HttpStatus.OK
        );
    }
    @GetMapping(
            path = {"/get-all-items-by-state"},
            params = {"state"}
    )
    public ResponseEntity<StandardResponse> getAllItemsByState(@RequestParam(value = "state") String state){
        if(state.equalsIgnoreCase("active") | state.equalsIgnoreCase("inactive")){

//            boolean states = false;
//           if (state.equalsIgnoreCase("active")){
//               states = true;
//           }

           boolean states = state.equalsIgnoreCase("active")?true:false;

           List<ItemDTO> allItems = itemService.getAllItemsByState(states);

           return new ResponseEntity<StandardResponse>(
                   new StandardResponse(200, "Success",allItems),
                   HttpStatus.OK
           );

        }else {
            List<ItemDTO> allItems = itemService.getAllItems();

            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200,"success", allItems),
                    HttpStatus.OK
            );
        }
    }
    @GetMapping(
            path = {"/count-all-items-"}
    )
    public ResponseEntity<StandardResponse> getAllItemCount(){
        int itemCount = itemService.getAllItemCount();

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, "Success", itemCount),
                HttpStatus.OK
        );
    }
    @GetMapping(
            path = {"/get-all-items-paginated"},
            params = {"page", "size"}
    )
    public ResponseEntity<StandardResponse> getAllItemsPaginated(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") @Max(50) int size
    ){
        PaginatedResponseItemDTO paginatedResponseItemDTO = itemService.getAllItemsPaginated(page, size);

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, "Success", paginatedResponseItemDTO),
                HttpStatus.OK
        );
    }
    @GetMapping(
            path = {"/get-all-active-items-paginated"},
            params = {"page", "size","activeState"}
    )
    public ResponseEntity<StandardResponse> getAllActiveItemsPaginated(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") @Max(50) int size,
            @RequestParam(value = "activeState") boolean activeState
    ){
        PaginatedResponseItemDTO paginatedResponseItemDTO = itemService.getAllActiveItemsPaginated(page, size,activeState);

        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, "Success", paginatedResponseItemDTO),
                HttpStatus.OK
        );
    }

}
