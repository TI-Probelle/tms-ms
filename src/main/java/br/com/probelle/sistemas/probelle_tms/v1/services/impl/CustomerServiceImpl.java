package br.com.probelle.sistemas.probelle_tms.v1.services.impl;

import br.com.probelle.sistemas.probelle_tms.v1.dto.customer.CustomerRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.customer.CustomerResponseDTO;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Customer;
import br.com.probelle.sistemas.probelle_tms.v1.entity.Region;
import br.com.probelle.sistemas.probelle_tms.v1.mappers.CustomerMapper;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.CustomerRepository;
import br.com.probelle.sistemas.probelle_tms.v1.repositories.RegionRepository;
import br.com.probelle.sistemas.probelle_tms.v1.services.CustomerService;
import br.com.probelle.sistemas.probelle_tms.v1.services.ServiceBase;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.ConflictException;
import br.com.probelle.sistemas.probelle_tms.v1.services.exceptions.NotFoundException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl extends ServiceBase implements CustomerService {

  @Autowired private CustomerRepository customerRepository;

  @Autowired private RegionRepository regionRepository;

  @Autowired private CustomerMapper customerMapper;

  @Override
  @Transactional
  public CustomerResponseDTO create(CustomerRequestDTO request) {
    if (customerRepository.existsByCode(request.code())) {
      throw new ConflictException("Customer code already exists");
    }
    Customer entity = customerMapper.toEntity(request);
    entity.setDefaultRegion(resolveRegion(request.defaultRegionUuid()));
    if (entity.getActive() == null) {
      entity.setActive(true);
    }
    Customer saved = customerRepository.save(entity);
    return customerMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public CustomerResponseDTO update(UUID uuid, CustomerRequestDTO request) {
    Customer entity = requireByUuid(customerRepository, uuid, "Customer");
    if (request.code() != null && !request.code().equals(entity.getCode())) {
      if (customerRepository.existsByCode(request.code())) {
        throw new ConflictException("Customer code already exists");
      }
    }
    customerMapper.updateEntity(request, entity);
    entity.setDefaultRegion(resolveRegion(request.defaultRegionUuid()));
    if (entity.getActive() == null) {
      entity.setActive(true);
    }
    Customer saved = customerRepository.save(entity);
    return customerMapper.toResponse(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public CustomerResponseDTO getByUuid(UUID uuid) {
    Customer entity = requireByUuid(customerRepository, uuid, "Customer");
    return customerMapper.toResponse(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public CustomerResponseDTO getByCode(String code) {
    Customer entity =
        customerRepository
            .findByCode(code)
            .orElseThrow(() -> new NotFoundException("Customer not found"));
    return customerMapper.toResponse(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<CustomerResponseDTO> list(Pageable pageable) {
    return customerRepository.findAll(pageable).map(customerMapper::toResponse);
  }

  @Override
  @Transactional
  public void delete(UUID uuid) {
    Customer entity = requireByUuid(customerRepository, uuid, "Customer");
    customerRepository.delete(entity);
  }

  private Region resolveRegion(UUID uuid) {
    if (uuid == null) {
      return null;
    }
    return requireByUuid(regionRepository, uuid, "Region");
  }
}
