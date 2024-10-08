package pl;

import java.util.Scanner;

import bll.CsvBusinessLayer;
import dal.CsvDatabaseLayer;

public class CsvPresentationLayer {

    private CsvBusinessLayer businessLayer;

    public CsvPresentationLayer(CsvBusinessLayer businessLayer) {
        this.businessLayer = businessLayer;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the path to the CSV file: ");
        String filePath = scanner.nextLine();

        try {
            businessLayer.processCsvFile(filePath);
            System.out.println("CSV data imported successfully.");
        } catch (Exception e) {
            System.err.println("Error processing CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CsvDatabaseLayer dbLayer = new CsvDatabaseLayer();
        CsvBusinessLayer businessLayer = new CsvBusinessLayer(dbLayer);
        CsvPresentationLayer presentationLayer = new CsvPresentationLayer(businessLayer);
        presentationLayer.start();
    }
}
