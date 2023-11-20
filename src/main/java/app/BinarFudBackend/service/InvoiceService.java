package app.BinarFudBackend.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface InvoiceService {

    byte[] generateInvoice() throws FileNotFoundException, JRException;

}
