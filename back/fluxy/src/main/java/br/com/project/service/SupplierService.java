package br.com.project.service;

import br.com.project.dto.request.SupplierRequestDTO;
import br.com.project.model.Supplier;
import br.com.project.repository.SupplierRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final MapperUtils mapperUtils;

    public SupplierService(SupplierRepository supplierRepository, MapperUtils mapperUtils) {
        this.supplierRepository = supplierRepository;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public void save(SupplierRequestDTO requestDTO) {
        try {
            if (supplierRepository.existsByCnpj(requestDTO.cnpj())) {
                throw new IllegalArgumentException("CNPJ já cadastrado");
            }

            // Mapeamento manual completo (substitui o uso de mapperUtils)
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
            Supplier supplier = mapperUtils.map(requestDTO, Supplier.class);
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
}