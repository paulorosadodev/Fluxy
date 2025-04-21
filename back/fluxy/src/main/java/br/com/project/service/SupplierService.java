package br.com.project.service;

import br.com.project.dto.request.SupplierRequestDTO;
import br.com.project.model.Supplier;
import br.com.project.repository.SupplierRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;

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

    public void save(SupplierRequestDTO requestDTO) {
        Supplier supplier = mapperUtils.map(requestDTO, Supplier.class);

        Integer idPessoa = supplierRepository.savePerson(supplier);
        supplierRepository.saveSupplier(idPessoa, supplier);
    }

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> findById(Integer id) {
        return supplierRepository.findById(id);
    }

    public void update(Integer id, SupplierRequestDTO requestDTO) {
        Supplier supplier = mapperUtils.map(requestDTO, Supplier.class);

        supplierRepository.updatePerson(id, supplier); // Atualiza pessoa
        supplierRepository.updateSupplier(id, supplier); // Atualiza fornecedor
    }

    public void delete(Integer id) {
        supplierRepository.deleteSupplier(id); // Exclui fornecedor
        supplierRepository.deletePerson(id);   // Exclui pessoa
    }
}
