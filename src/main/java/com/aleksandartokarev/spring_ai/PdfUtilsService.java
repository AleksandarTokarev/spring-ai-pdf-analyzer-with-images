package com.aleksandartokarev.spring_ai;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class PdfUtilsService {
    public List<byte[]> convertPdf(String documentName) {
        try {
            List<byte[]> images = new ArrayList<>();
            PDDocument pdDocument = Loader.loadPDF(new File("documents/" + documentName));
            PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
            for (int x = 0; x < pdDocument.getNumberOfPages(); x++) {
                BufferedImage bImage = pdfRenderer.renderImageWithDPI(x, 300, ImageType.RGB);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", baos);
                images.add(baos.toByteArray());
            }
            pdDocument.close();
            return images;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<byte[]> convertPdfWithUploadedFile(File file) {
        try {
            List<byte[]> images = new ArrayList<>();
            PDDocument pdDocument = Loader.loadPDF(file);
            PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
            for (int x = 0; x < pdDocument.getNumberOfPages(); x++) {
                BufferedImage bImage = pdfRenderer.renderImageWithDPI(x, 300, ImageType.RGB);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", baos);
                images.add(baos.toByteArray());
            }
            pdDocument.close();
            return images;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
