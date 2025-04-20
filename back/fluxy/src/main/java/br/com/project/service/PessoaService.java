package br.com.project.service;

import br.com.project.dto.request.PersonRequestDTO;
import br.com.project.dto.response.PersonResponseDTO;
import br.com.project.model.Pessoa;
import br.com.project.model.Telefone;
import br.com.project.repository.PessoaRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final TelefoneService telefoneService;
    private final MapperUtils mapperUtils;

    public PessoaService(PessoaRepository pessoaRepository, TelefoneService telefoneService, MapperUtils mapperUtils) {
        this.pessoaRepository = pessoaRepository;
        this.telefoneService = telefoneService;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public void salvar(PersonRequestDTO pessoaRequestDTO) {
        Pessoa pessoa = mapperUtils.map(pessoaRequestDTO, Pessoa.class);
        pessoaRepository.save(pessoa);

        // Salva todos os telefones vinculados
        List<Telefone> telefones = pessoaRequestDTO.getTelefones().stream()
                .map(numero -> new Telefone(numero, pessoa.getIdPessoa()))
                .collect(Collectors.toList());

        telefones.forEach(telefoneService::salvar);
    }

    @Transactional
    public void atualizar(Integer id, PersonRequestDTO pessoaRequestDTO) {
        Pessoa pessoa = mapperUtils.map(pessoaRequestDTO, Pessoa.class);
        pessoa.setIdPessoa(id);
        pessoaRepository.update(pessoa);

        // Primeiro apaga os telefones antigos
        telefoneService.deletarPorIdPessoa(id);

        // Depois salva os novos telefones
        List<Telefone> telefones = pessoaRequestDTO.getTelefones().stream()
                .map(numero -> new Telefone(numero, id))
                .collect(Collectors.toList());

        telefones.forEach(telefoneService::salvar);
    }

    public PersonResponseDTO buscarPorId(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        return mapperUtils.map(pessoa, PersonResponseDTO.class);
    }

    public List<PersonResponseDTO> listarTodas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return mapperUtils.mapList(pessoas, PersonResponseDTO.class);
    }

    public void deletar(Integer id) {
        telefoneService.deletarPorIdPessoa(id);
        pessoaRepository.deleteById(id);
    }
}
