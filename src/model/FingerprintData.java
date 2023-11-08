package model;

import com.griaule.grfingerjava.FingerprintImage;
import com.griaule.grfingerjava.Template;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class FingerprintData {
    /** Locally hold captured fingerprint data */
    private ByteArrayInputStream fingerprintData;
    /** Holds the fingerprint length */
    private int fingerprintDataLength;
    /** Image of the last fingerprint captured */
    private FingerprintImage fingerprint;
    /** Template of the last fingerprint captured */
    public Template template;
    private BufferedImage bufferedImage;
    public FingerprintData() {
    }

    public ByteArrayInputStream getFingerprintData() {
        return fingerprintData;
    }

    public void setFingerprintData(ByteArrayInputStream fingerprintData) {
        this.fingerprintData = fingerprintData;
    }

    public int getFingerprintDataLength() {
        return fingerprintDataLength;
    }

    public void setFingerprintDataLength(int fingerprintDataLength) {
        this.fingerprintDataLength = fingerprintDataLength;
    }

    public FingerprintImage getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(FingerprintImage fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
        this.fingerprintDataLength = template.getData().length;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public Image getImage(){
        return SwingFXUtils.toFXImage(this.bufferedImage, null);
    }
}
