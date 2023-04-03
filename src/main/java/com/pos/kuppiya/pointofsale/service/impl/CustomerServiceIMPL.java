package com.pos.kuppiya.pointofsale.service.impl;

import com.pos.kuppiya.pointofsale.dto.CustomerDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerSaveRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateByDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateQueryRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.CustomerUpdateRequestDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseFilterIdCustomerDTO;
import com.pos.kuppiya.pointofsale.dto.response.ResponseActiveCustomerDTO;
import com.pos.kuppiya.pointofsale.entity.Customer;
import com.pos.kuppiya.pointofsale.repository.CustomerRepo;
import com.pos.kuppiya.pointofsale.service.CustomerService;
import com.pos.kuppiya.pointofsale.util.mappers.CustomerMapper;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceIMPL implements CustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public String addCustomer(CustomerSaveRequestDTO customerSaveRequestDTO) {
        Customer customer = new Customer(customerSaveRequestDTO.getCustomerName(), customerSaveRequestDTO.getCustomerAddress(), customerSaveRequestDTO.getCustomerSalary(), customerSaveRequestDTO.getContactNumbers(), customerSaveRequestDTO.getNic(), true);
        if (!customerRepo.existsById(customer.getCustomerId())) {
            customerRepo.save(customer);
            return customer.getCustomerName() + " Saved";

        } else {
            System.out.println("Customer Id Already Exist");
            return "Customer Id Already Exist";
        }

    }

    @Override
    public String updateCustomer(CustomerUpdateRequestDTO customerUpdateRequestDTO) {
        if (customerRepo.existsById(customerUpdateRequestDTO.getCustomerId())) {
            Customer customer = customerRepo.getById(customerUpdateRequestDTO.getCustomerId());

            customer.setCustomerName(customerUpdateRequestDTO.getCustomerName());
            customer.setCustomerAddress(customerUpdateRequestDTO.getCustomerAddress());
            customer.setCustomerSalary(customerUpdateRequestDTO.getCustomerSalary());
            customer.setContactNumbers(customerUpdateRequestDTO.getContactNumbers());
            customer.setNic(customerUpdateRequestDTO.getNic());
            customer.setActiveState(customerUpdateRequestDTO.isActiveState());

            customerRepo.save(customer);

            System.out.println("Customer Come " + customer);
        } else {
            System.out.println("This Customer Not in the Database");
        }
        return null;
    }

    @Override
    public CustomerDTO getCustomerById(int id) {
        Optional<Customer> customer = customerRepo.findById(id);
        if (customer.isPresent()) {


//          CustomerDTO customerDTO = new CustomerDTO(
//              customer.get().getCustomerId(),
//              customer.get().getCustomerName(),
//              customer.get().getCustomerAddress(),
//              customer.get().getCustomerSalary(),
//              customer.get().getContactNumbers(),
//              customer.get().getNic(),
//              customer.get().isActiveState()
//            );
//
//          return customerDTO;

//          CustomerDTO customerDTO = modelMapper.map(customer.get(), CustomerDTO.class);
            CustomerDTO customerDTO = customerMapper.entityToDto(customer.get());
            return customerDTO ;

        } else {

            System.out.println("Customer Not in the Database");
            return null;
        }


    }

    @Override
    public List<CustomerDTO> getAllCustmmers() {
        List<Customer> getCustomers = customerRepo.findAll();
        //System.out.println(getCustomers.size());
//        List<CustomerDTO> customerDTOList = new ArrayList<>();

//        for (Customer c:getCustomers){
//            CustomerDTO customerDTO = new CustomerDTO(
//                    c.getCustomerId(),
//                    c.getCustomerName(),
//                    c.getCustomerAddress(),
//                    c.getCustomerSalary(),
//                    c.getContactNumbers(),
//                    c.getNic(),
//                    c.isActiveState()
//            );
//            customerDTOList.add(customerDTO);
//        }

//        List<CustomerDTO> customerDTOS = modelMapper.map(getCustomers, new TypeToken<List<CustomerDTO>>() {
//        }.getType());
        List<CustomerDTO> customerDTOS = customerMapper.entityListToDtoList(getCustomers);

        return customerDTOS;
    }

    @Override
    public boolean deleteCustomer(int id) throws NotFoundException {
        if (customerRepo.existsById(id)) {
            customerRepo.deleteById(id);
        } else {
            throw new NotFoundException("Not Found Customer For This Id");
        }
        return true;
    }

    @Override
    public List<CustomerDTO> getByName(String customerName) throws NotFoundException {
        List<Customer> customers = customerRepo.findAllByCustomerNameEquals(customerName);
        if (customers.size() != 0) {
            List<CustomerDTO> customerDTOS = modelMapper.map(customers, new TypeToken<List<CustomerDTO>>() {
            }.getType());
            return customerDTOS;
        } else {
            throw new NotFoundException("No Results");
        }

    }

    @Override
    public List<CustomerDTO> getAllCustomersByActiveState() throws NotFoundException {
       List<Customer> customers = customerRepo.findAllByActiveStateEquals(true);
       if (customers.size()!=0){
           List<CustomerDTO> customerDTOS = customerMapper.entityListToDtoList(customers);
           return customerDTOS;
       }else {
           throw new NotFoundException("No Active Customers Found");
       }
    }

    @Override
    public List<ResponseActiveCustomerDTO> getAllCustomersByActiveStateOnyName() throws NotFoundException {
        List<Customer> customers = customerRepo.findAllByActiveStateEquals(true);
        if (customers.size()!=0){
            List<ResponseActiveCustomerDTO> customerDTOS = customerMapper.entityListToDtolistOnlyName(customers);
            return customerDTOS;
        }else {
            throw new NotFoundException("No Active Customers Found");
        }

    }

    @Override
    public String updateCustomerByQuery(CustomerUpdateQueryRequestDTO customerUpdateQueryRequestDTO, int id) {
        if (customerRepo.existsById(id)){
            customerRepo.updateCustomerByQuery(customerUpdateQueryRequestDTO.getCustomerName(),customerUpdateQueryRequestDTO.getNic(),id);
            return "Updated "+id;
        }else {
            return "No Customer Found for this id " ;
        }

    }

    @Override
    public CustomerDTO getCustomerByNic(String nic) throws NotFoundException {
        Optional<Customer> customer = customerRepo.findByNic(nic);
        if(customer.isPresent()){
//            CustomerDTO customerDTO = customerMapper.entityToDto(customer.get());
            CustomerDTO customerDTO = modelMapper.map(customer.get(),CustomerDTO.class);
            return  customerDTO;
        }
        else {
            throw new com.pos.kuppiya.pointofsale.exception.NotFoundException("This customer not in the database");
        }
    }

    @Override
    public ResponseFilterIdCustomerDTO getCustomerFilterById(int id) {
        Optional<Customer> customer = customerRepo.findById(id);
        if(customer.isPresent()){
            ResponseFilterIdCustomerDTO responseFilterIdCustomerDTO = customerMapper.entityToResponseDTO(customer.get());
            return responseFilterIdCustomerDTO;
        }else {
            throw new com.pos.kuppiya.pointofsale.exception.NotFoundException("This customer not in the database");
        }
    }

    @Override
    public String updateCustomerByDto(CustomerUpdateByDTO customerUpdateByDTO, int id) {
        if (customerRepo.existsById(id)) {
            Customer customer = customerRepo.getById(id);

            customer.setCustomerName(customerUpdateByDTO.getCustomerName());
            customer.setCustomerSalary(customerUpdateByDTO.getCustomerSalary());
            customer.setNic(customerUpdateByDTO.getNic());

            customerRepo.save(customer);

        } else {
            System.out.println("This Customer Not in the Database");
        }
        return null;
    }

    @Override
    public CustomerDTO getCustomerrByIdIsActive(int id) {
        Optional<Customer> customer = customerRepo.findById(id);
        if(customer.isPresent()){
            if(customer.get().isActiveState()){
                CustomerDTO customerDTO = customerMapper.entityToDto(customer.get());
                return customerDTO;
            }else {
                return null;
            }
        }else {
            throw new com.pos.kuppiya.pointofsale.exception.NotFoundException("This customer not in the database");
        }
    }


}
