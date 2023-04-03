package com.pos.kuppiya.pointofsale.service;

import com.pos.kuppiya.pointofsale.dto.CustomerDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerSaveRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateByDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateQueryRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateRequestDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseFilterIdCustomerDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseActiveCustomerDTO;
import javassist.NotFoundException;

import java.util.List;

public interface CustomerService {
    String addCustomer(CustomerSaveRequestDTO customerSaveRequestDTO);
    String updateCustomer(CustomerUpdateRequestDTO customerUpdateRequestDTO);
    CustomerDTO getCustomerById(int id);
    List<CustomerDTO> getAllCustmmers();

    boolean deleteCustomer(int id) throws NotFoundException;

    List<CustomerDTO> getByName(String customerName) throws NotFoundException;

    List<CustomerDTO> getAllCustomersByActiveState() throws NotFoundException;

    List<ResponseActiveCustomerDTO> getAllCustomersByActiveStateOnyName() throws NotFoundException;

    String updateCustomerByQuery(CustomerUpdateQueryRequestDTO customerUpdateQueryRequestDTO, int id);

    CustomerDTO getCustomerByNic(String nic) throws NotFoundException;

    ResponseFilterIdCustomerDTO getCustomerFilterById(int id);

    String updateCustomerByDto(CustomerUpdateByDTO customerUpdateByDTO, int id);

    CustomerDTO getCustomerrByIdIsActive(int id);
}
