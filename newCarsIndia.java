package newCarsIndia;

import java.util.Scanner;

import javax.lang.model.element.Name;

import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStream;
import java.io.Writer;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

public class newCarsIndia {
	
	public void convertData() {

		String filepath = "C:\\Users\\manas\\eclipse-workspace\\newCarsIndiaProject\\NewIndianCarModels.csv";
		
		Model newCars = ModelFactory.createDefaultModel();

		String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns/";
		String rdfs = "http://www.w3.org/2000/01/rdf-schema/";
		String dbp = "https://dbpedia.org/property/";
		String dbr = "https://dbpedia.org/resource/"
		String dbo = "https://dbpedia.org/ontology/";

        //Schema Level resources
        Resource CarModel = newCars.createResource(dbr + "CarModel");
		Resource PriceRange = newCars.createResource(dbr + "PriceRange");
		Resource Range = newCars.createResource(dbr + "Range");
        Resource Rating = newCars.createResource(dbr + "Rating");
        Resource Style = newCars.createResource(dbr + "Style");
        Resource Transmission = newCars.createResource(dbr + "Transmission");
        Resource VehicleType = newCars.createResource(dbr + "VehicleType");

        // Object Properties
        Property rdfType = newCars.createProperty(rdf + "type");
        Property hasDifferent = newCars.createProperty(dbo + "hasDifferent");
        Property hasPerformance = newCars.createProperty(dbo + "hasPerformance");
        Property hasPriceOf = newCars.createProperty(dbo + "hasPriceOf");
        Property isRunningOn = newCars.createProperty(dbo + "isRunningOn");
        Property typesOf = newCars.createProperty(dbo + "typesOf");


        // Data Properties
        Property feature = newCars.createProperty(dbp + "feature");
        Property names = newCars.createProperty(dbo + "names");
        Property price = newCars.createProperty(dbo + "price");
        Property range = newCars.createProperty(dbo + "range");
        Property rating = newCars.createProperty(dbo + "rating");
        Property style = newCars.createProperty(dbo + "style");
        Property type = newCars.createProperty(dbo + "type");
		
        // Linking object proprties schema level
        CarModel.addProperty(hasDifferent, Style);
        CarModel.addProperty(hasPerformance, Rating);
        CarModel.addProperty(hasPriceOf, PriceRange);
        CarModel.addProperty(isRunningOn, VehicleType);
        CarModel.addProperty(typesOf, Transmission);

		try {
			Scanner sc = new Scanner(new File(filepath));
			sc.nextLine();
			
			while(sc.hasNext()){  
				String line = sc.nextLine();
				String[] arrStatement = line.split(",");
				
				Resource carModel = newCars.createResource(dbr + arrStatement[1].replace(" ", "_"));
				Resource priceRange = newCars.createResource(dbr + arrStatement[6].replace(" ", "_"));
				Resource range = newCars.createResource(dbr + arrStatement[3].replace(" ", "_"));
                Resource rating = newCars.createResource(dbr + arrStatement[7].replace(" ", "_"));
				Resource style = newCars.createResource(dbr + arrStatement[2].replace(" ", "_"));
				Resource transmission = newCars.createResource(dbr + arrStatement[4].replace(" ", "_"));
                Resource vehicleType = newCars.createResource(dbr + arrStatement[5].replace(" ", "_"));

            

//				Object properties
                carModel.addProperty(rdfType, CarModel);
				priceRange.addProperty(rdfType, PriceRange);
				range.addProperty(rdfType, Range);
				rating.addProperty(hasPerformance, Rating);
				style.addProperty(hasDifferent, Style);
				
				
//				Data properties
                // feature.addProperty(transmission, arrStatement[4]);
                // names.addProperty(carModel, arrStatement[1]);
                // price.addProperty(priceRange, arrStatement[6]);
                // range.addProperty(range, arrStatement[3]);
                // rating.addProperty(rating, arrStatement[7]);
                // style.addProperty(style, arrStatement[2]);
                // type.addProperty(vehicleType, arrStatement[5] );
				feature.addProperty(name, arrStatement[4]);
                names.addProperty(name, arrStatement[1]);
                price.addProperty(priceRange, arrStatement[6]);
                range.addProperty(range, arrStatement[3]);
                rating.addProperty(rating, arrStatement[7]);
                style.addProperty(name, arrStatement[2]);
                type.addProperty(name, arrStatement[5] );
			}   
			sc.close();
		}
		catch (IOException e)   
		{  
			e.printStackTrace();  
		}
		
		newCars.write(System.out,"TURTLE");
		 try { 
			 Writer wr=new FileWriter("C:\\Users\\manas\\eclipse-workspace\\newCarsIndiaProject\\newIndianCarModels.ttl");
			 newCars.write(wr,"TURTLE");
			 FileOutputStream wr1 = new FileOutputStream(new File("C:\\Users\\manas\\eclipse-workspace\\newCarsIndiaProject\\NewIndianCarModels.owl"));
			 newCars.write(wr1);
		 } 
		 catch (IOException e) { 
			 e.printStackTrace(); 
		 }
		 
		 int counter =0;
			StmtIterator iter = newCars.listStatements();
			while (iter.hasNext()) {
				Statement stmt = iter.next();
				Resource s = stmt.getSubject();
				Property p = stmt.getPredicate();
				RDFNode o = stmt.getObject();
				
				counter ++;

				System.out.print(counter+".  ");
				System.out.print(s.toString());
				System.out.print(" " + p.toString() + " ");
				if (o instanceof Resource) {
					System.out.print(o.toString());
				} else {
					
					System.out.print(" \"" + o.toString() + "\"");
				}

				System.out.println(" .");
			}
	}

}
