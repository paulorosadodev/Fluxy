package br.com.project.service;

import br.com.project.dto.request.EmployeeRequestDTO;
import br.com.project.dto.response.EmployeeResponseDTO;
import br.com.project.model.Employer;
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

    public void salvar(EmployeeRequestDTO funcionarioRequestDTO) {
        Employer funcionario = mapperUtils.map(funcionarioRequestDTO, Employer.class);
        funcionarioRepository.save(funcionario);
    }

    public EmployeeResponseDTO buscarPorId(Integer id) {
        Employer funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        return mapperUtils.map(funcionario, EmployeeResponseDTO.class);
    }

    public List<EmployeeResponseDTO> listarTodos() {
        List<Employer> funcionarios = funcionarioRepository.findAll();
        return mapperUtils.mapList(funcionarios, EmployeeResponseDTO.class);
    }

    public void atualizar(Integer id, EmployeeRequestDTO funcionarioRequestDTO) {
        Employer funcionario = mapperUtils.map(funcionarioRequestDTO, Employer.class);
        funcionario.setIdFuncionario(id);
        funcionarioRepository.update(funcionario);
    }

    public void deletar(Integer id) {
        funcionarioRepository.deleteById(id);
    }
}
