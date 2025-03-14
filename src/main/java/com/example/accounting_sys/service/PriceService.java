package com.example.accounting_sys.service;

import com.example.accounting_sys.dto.PriceListResponse;
import com.example.accounting_sys.exception.customException.PricePeriodNotFoundException;
import com.example.accounting_sys.model.entity.Product;
import com.example.accounting_sys.model.entity.ProductPricePeriod;
import com.example.accounting_sys.model.entity.Supplier;
import com.example.accounting_sys.repository.ProductPricePeriodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final ProductPricePeriodRepository productPricePeriodRepository;

    private final ProductService productService;
    private final SupplierService supplierService;


    public BigDecimal getPrice(Product product,Supplier supplier, LocalDate startDate, LocalDate endDate) {
        return productPricePeriodRepository.findByProductAndSupplierAndStartDateLessThanEqualAndEndDateGreaterThanEqual(product,supplier,startDate,endDate)
                .stream()
                .findFirst()
                .map(ProductPricePeriod::getPrice)
                .orElseThrow(() -> new PricePeriodNotFoundException("cant find a price for that product"));
    }

    public List<PriceListResponse> formatePriceList(Long id) {
        List<ProductPricePeriod> prices = getProductPrices(id);
        return prices.stream()
                .map(pricePeriod -> {
                    return new PriceListResponse(pricePeriod.getSupplier().getName(), pricePeriod.getStartDate(),pricePeriod.getEndDate(),pricePeriod.getPrice());
                }).collect(Collectors.toList());
    }

    @Transactional
    public List<ProductPricePeriod> getProductPrices(Long id) {
        Product product = productService.getById(id);
        return productPricePeriodRepository.findByProduct(product);
    }

    @Transactional
    public ProductPricePeriod setPrice(Long id, Long supplierId, BigDecimal newPrice) {
        Product product = productService.getById(id);
        Supplier supplier = supplierService.getById(supplierId);

        List<ProductPricePeriod> pricePeriods = getProductPrices(product.getId());

        LocalDate startDate = LocalDate.now();
        if(!(pricePeriods.isEmpty())) {
            startDate = pricePeriods.stream()
                    .map(ProductPricePeriod::getEndDate)
                    .max(LocalDate::compareTo)
                    .orElse(LocalDate.now())
                    .plusDays(1);
        }
        LocalDate endDate = startDate.plusMonths(3).minusDays(1);

        ProductPricePeriod period = ProductPricePeriod.builder()
                .price(newPrice)
                .endDate(endDate)
                .startDate(startDate)
                .product(product)
                .supplier(supplier)
                .build();
        return productPricePeriodRepository.save(period);

    }
}
