package br.com.project.service;

import br.com.project.dto.request.SupplierRequestDTO;
import br.com.project.dto.response.SupplierDeliveryCountDTO;
import br.com.project.model.Supplier;
import br.com.project.repository.PhoneRepository;
import br.com.project.repository.SupplierRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final MapperUtils mapperUtils;
    private final PhoneRepository phoneRepository;

    public SupplierService(SupplierRepository supplierRepository, MapperUtils mapperUtils, PhoneRepository phoneRepository) {
        this.supplierRepository = supplierRepository;
        this.mapperUtils = mapperUtils;
        this.phoneRepository = phoneRepository;
    }

    @Transactional
    public void save(SupplierRequestDTO requestDTO) {
        try {
            if (supplierRepository.existsByCnpj(requestDTO.cnpj())) {
                throw new IllegalArgumentException("CNPJ já cadastrado");
            }

            if (phoneRepository.existsByPhone(requestDTO.phone())) {
                throw new IllegalArgumentException("Telefone já cadastrado.");
            }

            Supplier supplier = new Supplier();
            supplier.setCnpj(requestDTO.cnpj());
            supplier.setName(requestDTO.name());
            supplier.setStreet(requestDTO.street());
            supplier.setNumber(requestDTO.number());
            supplier.setNeighborhood(requestDTO.neighborhood());
            supplier.setCity(requestDTO.city());
            supplier.setCep(requestDTO.cep());
            supplier.setPhone(requestDTO.phone());

            Integer idPessoa = supplierRepository.savePerson(supplier);
            supplierRepository.saveSupplier(idPessoa, supplier);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int countSuppliers() {
        return supplierRepository.countSuppliers();
    }

    public List<SupplierDeliveryCountDTO> getSuppliersByDeliveryCount() {
        return supplierRepository.findSuppliersByDeliveryCountDesc();
    }

    public List<SupplierDeliveryCountDTO> getSuppliersByDeliveryCountAsc() {
        return supplierRepository.findSuppliersByDeliveryCountAsc();
    }


    public List<Supplier> findAll() {
        try {
            return supplierRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<Supplier> findById(Integer id) {
        try {
            return supplierRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void update(Integer id, SupplierRequestDTO requestDTO) {
        try {
            if (supplierRepository.existsByCnpj(requestDTO.cnpj())){
                throw new IllegalArgumentException("CPNJ já cadastrado");
            }
            Supplier supplier = mapperUtils.map(requestDTO, Supplier.class);
            supplier.setId(id);
            supplier.setName(requestDTO.name());
            supplier.setCnpj(requestDTO.cnpj());
            supplier.setStreet(requestDTO.street());
            supplier.setNumber(requestDTO.number());
            supplier.setNeighborhood(requestDTO.neighborhood());
            supplier.setCity(requestDTO.city());
            supplier.setCep(requestDTO.cep());
            supplier.setPhone(requestDTO.phone()); // importante
            supplierRepository.updatePerson(id, supplier);
            supplierRepository.updateSupplier(id, supplier);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            supplierRepository.deleteSupplier(id);
            supplierRepository.deletePerson(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Integer buscarIdPorCnpj(String cnpj) {
        try {
            return supplierRepository.findSupplierIdByCnpj(cnpj);
        } catch (Exception e) {
            return null;
        }
    }
}