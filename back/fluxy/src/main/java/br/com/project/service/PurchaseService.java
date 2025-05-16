package br.com.project.service;

import br.com.project.dto.request.PurchaseRequestDTO;
import br.com.project.dto.response.PurchaseResponseDTO;
import br.com.project.model.Purchase;
import br.com.project.repository.EmployerRepository;
import br.com.project.repository.PurchaseRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final FuncionarioService funcionarioService;
    private final ClienteService clienteService;
    private final MapperUtils mapperUtils;

    public PurchaseService(PurchaseRepository purchaseRepository, FuncionarioService funcionarioService, ClienteService clienteService, MapperUtils mapperUtils) {
        this.purchaseRepository = purchaseRepository;
        this.funcionarioService = funcionarioService;
        this.clienteService = clienteService;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public PurchaseResponseDTO save(PurchaseRequestDTO requestDTO) {
        try {
            Purchase purchase = new Purchase();
            purchase.setDate(requestDTO.date());
            purchase.setTime(requestDTO.time());
            purchase.setInstallments(requestDTO.installments());
            purchase.setPaymentType(requestDTO.paymentType());
            purchase.setProductQuantity(requestDTO.productAmount());
            purchase.setProductId(requestDTO.productId());
            purchase.setClientId(clienteService.buscarIdPorMatricula(requestDTO.customerId()));
            if (requestDTO.customerId() == null || requestDTO.customerId().isBlank()) {
                throw new RuntimeException("A matrícula do cliente (fkClientId) está ausente.");
            }
            purchase.setOperationalEmployeeId(funcionarioService.buscarIdPorMatricula(requestDTO.employeeId()));

            Integer purchaseId = purchaseRepository.save(purchase);
            purchase.setNumber(purchaseId);

            return mapperUtils.map(purchase, PurchaseResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public PurchaseResponseDTO findByNumber(Integer number) {
        try {
            Purchase purchase = purchaseRepository.findByNumber(number)
                    .orElseThrow(() -> new RuntimeException("Compra não encontrada com número" + number));
            return mapperUtils.map(purchase, PurchaseResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<PurchaseResponseDTO> findAll() {
        try {
            List<Purchase> purchases = purchaseRepository.findAll();
            return mapperUtils.mapList(purchases, PurchaseResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void update(Integer number, PurchaseRequestDTO requestDTO) {
        try {
            Purchase purchase = purchaseRepository.findByNumber(number)
                    .orElseThrow(() -> new RuntimeException("Compra não encontrada"));

            purchase.setDate(requestDTO.date());
            purchase.setTime(requestDTO.time());
            purchase.setInstallments(requestDTO.installments());
            purchase.setPaymentType(requestDTO.paymentType());
            purchase.setProductQuantity(requestDTO.productAmount());
            purchase.setProductId(requestDTO.productId());
            purchase.setClientId(clienteService.buscarIdPorMatricula(requestDTO.customerId()));
            purchase.setOperationalEmployeeId(funcionarioService.buscarIdPorMatricula(requestDTO.employeeId()));
            purchase.setNumber(number);
            purchaseRepository.update(purchase);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Transactional
    public void deleteByNumber(Integer number) {
        try {
            purchaseRepository.deleteByNumber(number);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
