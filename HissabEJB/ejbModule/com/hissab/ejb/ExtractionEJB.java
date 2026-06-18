package com.hissab.ejb;

import jakarta.ejb.Stateless;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.nio.file.Files;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

@Stateless
public class ExtractionEJB implements ExtractionLocal {

    @Override
    public String extraireExpression(String nomFichier, InputStream inputStream) {
        try {
            String lower = nomFichier.toLowerCase();

            if (lower.endsWith(".pdf")) {
                return extraireDepuisPdf(inputStream);
            }

            if (lower.endsWith(".txt")) {
                return extraireDepuisTxt(inputStream);
            }
            
            if (lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
                return extraireDepuisImage(inputStream);
            }

            throw new RuntimeException("Format non supporté pour le moment : " + nomFichier);

        } catch (Exception e) {
            throw new RuntimeException("Erreur extraction : " + e.getMessage(), e);
        }
    }

    private String extraireDepuisTxt(InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        String ligne;

        while ((ligne = reader.readLine()) != null) {
            sb.append(ligne).append(" ");
        }

        return nettoyerExpression(sb.toString());
    }

    private String extraireDepuisPdf(InputStream inputStream) throws Exception {
        PDDocument document = Loader.loadPDF(inputStream.readAllBytes());
        PDFTextStripper stripper = new PDFTextStripper();

        String texte = stripper.getText(document);
        document.close();

        return nettoyerExpression(texte);
    }
    
    private String extraireDepuisImage(InputStream inputStream) throws Exception {
        File imageTemp = File.createTempFile("hissab_ocr_", ".png");
        Files.copy(inputStream, imageTemp.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\tessdata");
        tesseract.setLanguage("fra");

        String texte = tesseract.doOCR(imageTemp);

        imageTemp.delete();

        return nettoyerExpression(texte);
    }


    private String nettoyerExpression(String texte) {
        return texte.replaceAll("[^0-9+\\-*/×÷()., ]", " ")
                    .replace(",", ".")
                    .replaceAll("\\s+", " ")
                    .trim();
    }
}
