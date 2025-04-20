package br.com.project.service;

import br.com.project.dto.request.SupplierRequestDTO;
import br.com.project.dto.response.SupplierResponseDTO;
import br.com.project.model.Fornecedor;
import br.com.project.model.Telefone;
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
        Fornecedor fornecedor = mapperUtils.map(fornecedorRequestDTO, Fornecedor.class);
        fornecedorRepository.save(fornecedor);

        List<Telefone> telefones = fornecedorRequestDTO.getTelefones().stream()
                .map(numero -> new Telefone(numero, fornecedor.getIdFornecedor()))
                .collect(Collectors.toList());

        telefones.forEach(telefoneService::salvar);
    }

    @Transactional
    public void atualizar(Integer id, SupplierRequestDTO fornecedorRequestDTO) {
        Fornecedor fornecedor = mapperUtils.map(fornecedorRequestDTO, Fornecedor.class);
        fornecedor.setIdFornecedor(id);
        fornecedorRepository.update(fornecedor);

        telefoneService.deletarPorIdPessoa(id);

        List<Telefone> telefones = fornecedorRequestDTO.getTelefones().stream()
                .map(numero -> new Telefone(numero, id))
                .collect(Collectors.toList());

        telefones.forEach(telefoneService::salvar);
    }

    public SupplierResponseDTO buscarPorId(Integer id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        return mapperUtils.map(fornecedor, SupplierResponseDTO.class);
    }

    public List<SupplierResponseDTO> listarTodos() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        return mapperUtils.mapList(fornecedores, SupplierResponseDTO.class);
    }

    public void deletar(Integer id) {
        telefoneService.deletarPorIdPessoa(id);
        fornecedorRepository.deleteById(id);
    }
}
