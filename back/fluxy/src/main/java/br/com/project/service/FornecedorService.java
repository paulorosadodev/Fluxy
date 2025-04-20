package br.com.project.service;

import br.com.project.dto.request.SupplierRequestDTO;
import br.com.project.dto.response.SupplierResponseDTO;
import br.com.project.model.Supplier;
import br.com.project.model.Phone;
import br.com.project.repository.FornecedorRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final TelefoneService telefoneService;
    private final MapperUtils mapperUtils;

    public FornecedorService(FornecedorRepository fornecedorRepository, TelefoneService telefoneService, MapperUtils mapperUtils) {
        this.fornecedorRepository = fornecedorRepository;
        this.telefoneService = telefoneService;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public void salvar(SupplierRequestDTO fornecedorRequestDTO) {
        Supplier fornecedor = mapperUtils.map(fornecedorRequestDTO, Supplier.class);
        fornecedorRepository.save(fornecedor);

        List<Phone> telefones = fornecedorRequestDTO.getTelefones().stream()
                .map(numero -> new Phone(numero, fornecedor.getIdFornecedor()))
                .collect(Collectors.toList());

        telefones.forEach(telefoneService::salvar);
    }

    @Transactional
    public void atualizar(Integer id, SupplierRequestDTO fornecedorRequestDTO) {
        Supplier fornecedor = mapperUtils.map(fornecedorRequestDTO, Supplier.class);
        fornecedor.setIdFornecedor(id);
        fornecedorRepository.update(fornecedor);

        telefoneService.deletarPorIdPessoa(id);

        List<Phone> telefones = fornecedorRequestDTO.getTelefones().stream()
                .map(numero -> new Phone(numero, id))
                .collect(Collectors.toList());

        telefones.forEach(telefoneService::salvar);
    }

    public SupplierResponseDTO buscarPorId(Integer id) {
        Supplier fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        return mapperUtils.map(fornecedor, SupplierResponseDTO.class);
    }

    public List<SupplierResponseDTO> listarTodos() {
        List<Supplier> fornecedores = fornecedorRepository.findAll();
        return mapperUtils.mapList(fornecedores, SupplierResponseDTO.class);
    }

    public void deletar(Integer id) {
        telefoneService.deletarPorIdPessoa(id);
        fornecedorRepository.deleteById(id);
    }
}
