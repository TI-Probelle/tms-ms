package br.com.probelle.sistemas.probelle_tms.v1.services;

import br.com.probelle.sistemas.probelle_tms.v1.dto.customer.CustomerRequestDTO;
import br.com.probelle.sistemas.probelle_tms.v1.dto.customer.CustomerResponseDTO;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
  CustomerResponseDTO create(CustomerRequestDTO request);

  CustomerResponseDTO update(UUID uuid, CustomerRequestDTO request);

  CustomerResponseDTO getByUuid(UUID uuid);

  CustomerResponseDTO getByCode(String code);

  Page<CustomerResponseDTO> list(Pageable pageable);

  void delete(UUID uuid);
}
