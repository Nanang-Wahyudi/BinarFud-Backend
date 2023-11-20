package app.BinarFudBackend.service.impl;

import app.BinarFudBackend.model.response.InvoiceResponse;
import app.BinarFudBackend.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.*;

@Transactional(readOnly = true)
@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {


    @Override
    public byte[] generateInvoice() throws FileNotFoundException, JRException {
        log.info("Generating invoice for {Nanang Wahyudi}");
        List<InvoiceResponse> orderDetails = Collections.singletonList(
                InvoiceResponse.builder()
                        .productName("Nasi Goreng")
                        .price("15000")
                        .quantity("2")
                        .build()
        );

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("userName", "Nanang Wahyudi");
        parameterMap.put("finalPrice", "Rp. 15.000");
        parameterMap.put("orderDetail", orderDetails);
        JasperPrint invoice = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(ResourceUtils.getFile("classpath:invoice.jrxml").getAbsolutePath()),
                parameterMap,
                new JRBeanCollectionDataSource(orderDetails)
        );

        return JasperExportManager.exportReportToPdf(invoice);
    }

}
