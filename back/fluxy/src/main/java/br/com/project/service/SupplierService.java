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
            Supplier supplier = mapperUtils.map(requestDTO, Supplier.class);
            Integer idPessoa = supplierRepository.savePerson(supplier);
            supplierRepository.saveSupplier(idPessoa, supplier);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar fornecedor: " + e.getMessage());
        }
    }

    public List<Supplier> findAll() {
        try {
            return supplierRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar fornecedores: " + e.getMessage());
        }
    }

    public Optional<Supplier> findById(Integer id) {
        try {
            return supplierRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar fornecedor com ID " + id + ": " + e.getMessage());
        }
    }

    @Transactional
    public void update(Integer id, SupplierRequestDTO requestDTO) {
        try {
            Supplier supplier = mapperUtils.map(requestDTO, Supplier.class);
            supplierRepository.updatePerson(id, supplier);
            supplierRepository.updateSupplier(id, supplier);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar fornecedor com ID " + id + ": " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            supplierRepository.deleteSupplier(id);
            supplierRepository.deletePerson(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar fornecedor com ID " + id + ": " + e.getMessage());
        }
    }
}
