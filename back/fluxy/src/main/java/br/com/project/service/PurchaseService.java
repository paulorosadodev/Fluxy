package br.com.project.service;

import br.com.project.dto.request.PurchaseRequestDTO;
import br.com.project.dto.response.PaymentTypeCountResponseDTO;
import br.com.project.dto.response.PurchaseCountByMonthAndYearResponseDTO;
import br.com.project.dto.response.PurchaseResponseDTO;
import br.com.project.model.Purchase;
import br.com.project.repository.PurchaseRepository;
import br.com.project.util.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final FuncionarioService funcionarioService;
    private final ClienteService clienteService;
    private final ProductService productService;

    private final MapperUtils mapperUtils;

    public PurchaseService(PurchaseRepository purchaseRepository,
                           FuncionarioService funcionarioService,
                           ClienteService clienteService,
                           ProductService productService,
                           MapperUtils mapperUtils) {
        this.purchaseRepository = purchaseRepository;
        this.funcionarioService = funcionarioService;
        this.clienteService = clienteService;
        this.productService = productService;
        this.mapperUtils = mapperUtils;
    }

    @Transactional
    public PurchaseResponseDTO save(PurchaseRequestDTO requestDTO) {
        try {
            Purchase purchase = new Purchase();
            purchase.setDate(LocalDate.now());
            purchase.setTime(LocalTime.now());
            purchase.setInstallments(requestDTO.installments());
            purchase.setPaymentType(requestDTO.paymentType());
            purchase.setProductAmount(requestDTO.productAmount());
            purchase.setProductId(requestDTO.productId());

            Integer clientId = clienteService.buscarIdPorMatricula(requestDTO.customerId());
            if (clientId == null) {
                    throw new RuntimeException("Cliente não encontrado com CPF " + requestDTO.customerId());
            }

            purchase.setClientId(clientId);
            if (requestDTO.customerId() == null || requestDTO.customerId().isBlank()) {
                throw new RuntimeException("A matrícula do cliente está ausente.");
            }
            purchase.setEmployeeId(funcionarioService.buscarIdPorMatricula(requestDTO.employeeId()));

            Integer purchaseId = purchaseRepository.save(purchase);
            purchase.setNumber(purchaseId);

            productService.decreaseStock(requestDTO.productId(), requestDTO.productAmount());

            return mapperUtils.map(purchase, PurchaseResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public int countAllPurchases() {
        return purchaseRepository.countAllPurchases();
    }

    public PurchaseCountByMonthAndYearResponseDTO getPurchaseCountByMonthAndYear(int month, int year) {
        return purchaseRepository.countPurchasesByMonthAndYear(month, year);
    }

    public List<PaymentTypeCountResponseDTO> getPaymentTypeCounts() {
        return purchaseRepository.countPurchasesByPaymentType();
    }

    public List<Purchase> getAllPurchasesOrderedByCostDesc() {
        return purchaseRepository.findAllOrderedByCostDesc();
    }

    public List<Purchase> getAllPurchasesOrderedByCostAsc() {
        return purchaseRepository.findAllOrderedByCostAsc();
    }

    public Double getTotalPurchaseCostByMonthAndYear(int month, int year) {
        try {
            return purchaseRepository.sumPurchaseCostsByMonthAndYear(month, year);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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

            Integer produtoAntigoId = purchase.getProductId();
            int quantidadeAntiga = purchase.getProductAmount();

            Integer produtoNovoId = requestDTO.productId();
            int quantidadeNova = requestDTO.productAmount();

            if (!produtoAntigoId.equals(produtoNovoId)) {
                productService.increaseStock(produtoAntigoId, quantidadeAntiga);
                productService.decreaseStock(produtoNovoId, quantidadeNova);
            } else {
                int diferenca = quantidadeNova - quantidadeAntiga;
                if (diferenca > 0) {
                    productService.decreaseStock(produtoNovoId, diferenca);
                } else if (diferenca < 0) {
                    productService.increaseStock(produtoNovoId, -diferenca);
                }
            }

            purchase.setDate(LocalDate.now());
            purchase.setTime(LocalTime.now());
            purchase.setInstallments(requestDTO.installments());
            purchase.setPaymentType(requestDTO.paymentType());
            purchase.setProductAmount(quantidadeNova);
            purchase.setProductId(produtoNovoId);

            Integer customerId = clienteService.buscarIdPorMatricula(requestDTO.customerId());
            if (customerId == null) {
                throw new RuntimeException("Cliente não encontrado com matrícula " + requestDTO.customerId());
            }
            purchase.setClientId(customerId);

            Integer employeeId = funcionarioService.buscarIdPorMatricula(requestDTO.employeeId());
            if (employeeId == null) {
                throw new RuntimeException("Funcionário não encontrado com matrícula " + requestDTO.employeeId());
            }
            purchase.setEmployeeId(employeeId);

            purchase.setNumber(number);
            purchaseRepository.update(purchase);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteByNumber(Integer number) {
        try {
            Purchase purchase = purchaseRepository.findByNumber(number)
                    .orElseThrow(() -> new RuntimeException("Compra não encontrada"));

            productService.increaseStock(purchase.getProductId(), purchase.getProductAmount());
            purchaseRepository.deleteByNumber(number);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
