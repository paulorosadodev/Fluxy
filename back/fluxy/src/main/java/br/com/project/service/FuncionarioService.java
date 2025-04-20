package br.com.project.service;

import br.com.project.dto.request.FuncionarioRequestDTO;
import br.com.project.dto.response.FuncionarioResponseDTO;
import br.com.project.model.Funcionario;
import br.com.project.repository.FuncionarioRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final MapperUtils mapperUtils;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, MapperUtils mapperUtils) {
        this.funcionarioRepository = funcionarioRepository;
        this.mapperUtils = mapperUtils;
    }

    public void salvar(FuncionarioRequestDTO funcionarioRequestDTO) {
        Funcionario funcionario = mapperUtils.map(funcionarioRequestDTO, Funcionario.class);
        funcionarioRepository.save(funcionario);
    }

    public FuncionarioResponseDTO buscarPorId(Integer id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        return mapperUtils.map(funcionario, FuncionarioResponseDTO.class);
    }

    public List<FuncionarioResponseDTO> listarTodos() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return mapperUtils.mapList(funcionarios, FuncionarioResponseDTO.class);
    }

    public void atualizar(Integer id, FuncionarioRequestDTO funcionarioRequestDTO) {
        Funcionario funcionario = mapperUtils.map(funcionarioRequestDTO, Funcionario.class);
        funcionario.setIdFuncionario(id);
        funcionarioRepository.update(funcionario);
    }

    public void deletar(Integer id) {
        funcionarioRepository.deleteById(id);
    }
}
