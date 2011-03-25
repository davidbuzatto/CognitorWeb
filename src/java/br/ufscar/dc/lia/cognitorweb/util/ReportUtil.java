/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lia.cognitorweb.util;

import java.io.*;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;
import net.sf.jasperreports.engine.export.*;

/**
 * Classe para geração de relatórios.
 *
 * @author David Buzatto
 */
public class ReportUtil {

    /*
     * Gera o relatório.
     */
    public void gerar( String nome,
            Collection dados,
            Map parametros,
            OutputStream outStream ) throws Exception {

        // obtém o relatório compilado
        InputStream stream = getClass().getResourceAsStream(
                "/relatorios/" + nome
                + ".jasper" );

        // preenche o relatório com as informações
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource( dados );

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                stream, parametros, ds );

        // exporta...
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter( JRExporterParameter.JASPER_PRINT,
                jasperPrint );
        exporter.setParameter( JRExporterParameter.OUTPUT_STREAM,
                outStream );

        exporter.exportReport();

    }

}
