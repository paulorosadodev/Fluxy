package br.com.project.service;

import br.com.project.model.Phone;
import br.com.project.repository.TelefoneRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;
    private final MapperUtils mapperUtils;

    public TelefoneService(TelefoneRepository telefoneRepository, MapperUtils mapperUtils) {
        this.telefoneRepository = telefoneRepository;
        this.mapperUtils = mapperUtils;
    }

    public void salvar(Phone telefone) {
        telefoneRepository.save(telefone);
    }

    public void deletarPorIdPessoa(Integer idPessoa) {
        // Busca todos os telefones que pertencem a essa pessoa e deleta um por um
        List<Phone> telefones = telefoneRepository.findAll().stream()
                .filter(t -> t.getIdPhone().equals(idPessoa))
                .toList();

        telefones.forEach(t -> telefoneRepository.deleteByNumero(t.getNumber()));
    }
}
