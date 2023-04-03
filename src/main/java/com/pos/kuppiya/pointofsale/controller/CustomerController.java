package com.pos.kuppiya.pointofsale.controller;

import com.pos.kuppiya.pointofsale.dto.CustomerDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerSaveRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateByDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateQueryRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateRequestDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseFilterIdCustomerDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseActiveCustomerDTO;
import com.pos.kuppiya.pointofsale.service.CustomerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/save")
    public String saveCustomer(@RequestBody CustomerSaveRequestDTO customerSaveRequestDTO) {
        System.out.println("Come " + customerSaveRequestDTO.getCustomerName());
        String id = customerService.addCustomer(customerSaveRequestDTO);
        return id;
    }

    @PutMapping(path = "/update")
    public String updateCustomer(@RequestBody CustomerUpdateRequestDTO customerUpdateRequestDTO) {
        String updated = customerService.updateCustomer(customerUpdateRequestDTO);

        return "updated";
    }

    @GetMapping(
            path = "/get-by-id",
            params = "id"
    )
    public CustomerDTO getCustomerById(@RequestParam(value = "id") int id) {
        CustomerDTO customerDTO = customerService.getCustomerById(id);
        return customerDTO;
    }

    @GetMapping(path =
            {"/get-all-customers"}
    )
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> allCustomers = customerService.getAllCustmmers();
        return allCustomers;
    }
    @DeleteMapping(
            path = {"/delete-customer/{id}"}
    )
    public String deleteCustomer(@PathVariable(value = "id") int id) throws NotFoundException {
        boolean deletedCustomer = customerService.deleteCustomer(id);
        return "deleted ";
    }
    @GetMapping(
            path = {"/get-by-name"},
            params = {"name"}
    )
    public List<CustomerDTO> getCustomerByName(@RequestParam(value = "name") String customerName) throws NotFoundException {
        List<CustomerDTO> getCustomers = customerService.getByName(customerName);
        return getCustomers;
    }
    @GetMapping(
            path = {"/get-by-active-state"}
    )
    public List<CustomerDTO> getCustomerByActiveState() throws NotFoundException {
        List<CustomerDTO> getCustomer = customerService.getAllCustomersByActiveState();
        return getCustomer;
    }
    @GetMapping(
            path = {"/get-by-active-state-only-name"}
    )
    public List<ResponseActiveCustomerDTO> getCustomerByActiveStateOnlyName() throws NotFoundException {
        List<ResponseActiveCustomerDTO> getCustomer = customerService.getAllCustomersByActiveStateOnyName();
        return getCustomer;
    }
    @PutMapping(path = "/update/{id}")
    public String updateCustomerByQuery(
            @RequestBody CustomerUpdateQueryRequestDTO customerUpdateQueryRequestDTO,
            @PathVariable(value = "id") int id
            ) {
        String updated = customerService.updateCustomerByQuery(customerUpdateQueryRequestDTO,id);

        return "updated "+id;
    }
    @GetMapping(
            path = {"/get-by-nic"},
            params = {"nic"}
    )
    public CustomerDTO getCustomerByNic(@RequestParam(value = "nic" ) String nic) throws NotFoundException {
        CustomerDTO customerDTO = customerService.getCustomerByNic(nic);
        return customerDTO;
    }
    @GetMapping(
            path = {"/get-filter-by-id"},
            params = {"id"}
    )
    public ResponseFilterIdCustomerDTO getCustomerFilterById(@RequestParam(value = "id" ) int id) {
        ResponseFilterIdCustomerDTO responseFilterIdCustomerDTO = customerService.getCustomerFilterById(id);
        return responseFilterIdCustomerDTO;
    }
    @PutMapping(path = "/update-by-request/{id}")
    public String updateCustomerByDto(@RequestBody CustomerUpdateByDTO customerUpdateByDTO,
                                      @PathVariable(value = "id") int id
                                      ) {
        String updated = customerService.updateCustomerByDto(customerUpdateByDTO,id);

        return "update success "+id;
    }
    @GetMapping(
            path = {"/get-r-by-id-is-active"},
            params = {"id"}
    )
    public CustomerDTO getCustomerByIdIsActive(@RequestParam(value = "id" ) int id) {
        CustomerDTO customerDTO = customerService.getCustomerrByIdIsActive(id);
        return customerDTO;
    }

}
