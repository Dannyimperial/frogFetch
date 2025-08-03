import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import javax.imageio.ImageIO;



//frogFetch version 0.1.0


public class frogFetch {
	
	

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		String filePath = ".tadpoles/welcome.txt";
		
		try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String pumpkin = scanner.nextLine();
                System.out.println(pumpkin);
            }

            scanner.close(); // Close the scanner to release resources
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found at " + filePath);
            e.printStackTrace(); // Print the stack trace for debugging
        }
		
		
		
		
		try {
            System.out.println("\033[38;2;150;200;0m   ----------------------------------------------------------------------------------------------------------------");
            System.out.println("   \033[38;2;250;175;0mPlease enter the name of the png image.  \033[38;2;250;75;50m(Example: \033[38;2;150;200;0m\"frog\" \033[38;2;250;75;50mnot \033[38;2;100;175;200m\"frog.png\"\033[38;2;250;75;50m) (image must be in the \"frogs\" folder.)");
            System.out.println("   ----------------------------------------------------------------------------------------------------------------");
			

			
			String pixelArt = scan.next();
            String ignoredRGB = "200200200"; // This color will be treated as transparent. Use this for backgrounds


			File imageFile = new File("frogs", pixelArt + ".png");
			scan.close();
			FileWriter writer = new FileWriter(pixelArt + ".txt");
			writer.write("\\033[38;2;0;0;0m");

			BufferedImage image = ImageIO.read(imageFile);

			int width = image.getWidth();
			int height = image.getHeight();
			int prevColor = 0;
			Path ASCIIFolder = Paths.get("toads/"+pixelArt+".txt");
			Path ASCIIFile = Paths.get(pixelArt+".txt");
			//------------------------------------------------------------------------------------------------------------------------------


			for (int y = 0; y < height; y++) {

				for (int x = 0; x < width; x++) {

					int rgb = image.getRGB(x, y);
					Color pixelColor = new Color(rgb);
					int red = pixelColor.getRed();
					int green = pixelColor.getGreen();
					int blue = pixelColor.getBlue();
					String pixel = ("" + red + "" + green + "" + blue);

					if (!pixel.equals(ignoredRGB)) {

						if (rgb != prevColor) {
							System.out.print("\033[38;2;" + red + ";" + green + ";" + blue + "m");
							writer.write("\\033[38;2;" + red + ";" + green + ";" + blue + "m");
						}
						System.out.print("@.");
						writer.write("@.");

					} else {
						System.out.print("  ");
						writer.write("  ");
					}
					prevColor = rgb;
				}
				System.out.println();
				writer.write("\n");

			}
			
			//------------------------------------------------------------------------------------------------------------------------------

			writer.close();
			
			System.out.println();
			System.out.println();
			System.out.println();

			int minus = getGap(pixelArt + ".txt");
			minus = minus - (width * 2) - 3;
			System.out.println("\033[38;2;150;100;175m------------------");
			System.out.println("\033[38;2;50;200;175m Gap size = -" + minus);
			System.out.println("\033[38;2;150;100;175m------------------");
			
			
			
			 try {
		            // Move the file
		            Files.move(ASCIIFile, ASCIIFolder, StandardCopyOption.REPLACE_EXISTING);
		            
		        } catch (IOException e) {
		            System.err.println("Destination folder not found: " + e.getMessage());
		        }

		} catch (IOException e) {
			System.err.println("Error. Image not found");
			System.out.println("Make sure image is in the 'frogs' folder.");
		}

	}
	
	//------------------------------------------------------------------------------------------------------------------------------

	public static int getGap(String filename) {

		String longestLine = null;
		int maxLength = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				if (currentLine.length() > maxLength) {
					maxLength = currentLine.length();
					longestLine = currentLine;
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading the file: " + e.getMessage());
			return 0; // Indicate an error occurred
		}
		int gap = longestLine.length();
		return gap;
	}
	//------------------------------------------------------------------------------------------------------------------------------


}
