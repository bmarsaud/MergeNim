package fr.thezopo.mergenim;

import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MergeNim {
    public static void main (String [] args) throws IOException, InterruptedException, URISyntaxException {
        Console console = System.console();
        if(console == null && !GraphicsEnvironment.isHeadless()){
            String filename = MergeNim.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);
            Runtime.getRuntime().exec(new String[]{"cmd","/c","start","cmd","/k","java -jar \"" + filename + "\""});
        }else{
            mergeNim(args);
        }
    }
	
	public static void mergeNim(String[] args) {
		System.out.println(" __  __                     _   _ _");
		System.out.println("|  \\/  |                   | \\ | (_)");
		System.out.println("| \\  / | ___ _ __ __ _  ___|  \\| |_ _ __ ___ ");
		System.out.println("| |\\/| |/ _ \\ '__/ _` |/ _ \\ . ` | | '_ ` _ \\");
		System.out.println("| |  | |  __/ | | (_| |  __/ |\\  | | | | | | |");
		System.out.println("|_|  |_|\\___|_|  \\__, |\\___|_| \\_|_|_| |_| |_|");
		System.out.println("                  __/ |");
		System.out.println("                 |___/");
		System.out.println("\nVersion 0.1 by Bastien MARSAUD");
		System.out.println("Merge same sized png files to one output file");
		System.out.println("Under (CC) BY NC ND Licence");
		
		File dir = new File(System.getProperty("user.dir"));
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		
		System.out.println("\nImages detected : ");
		for(File f : dir.listFiles()) {
			if(getFileExtension(f.getName()).equals("png") && !f.getName().matches("output.*")) {
				System.out.println("- " + f.getName());
				try {
					images.add((BufferedImage) ImageIO.read(f));
				} catch (IOException e) {
					System.err.println("\n> ERROR : Error loading image " + f.getName());
				}
			}
		}
		
		if(images.size() >= 2) {
			System.out.println("\n> Mergin started...");
			
			int width = images.get(0).getWidth();
			int height = images.get(0).getHeight();
			
			BufferedImage output = new BufferedImage(width * images.size(), height, BufferedImage.TYPE_INT_ARGB);
			Graphics g = output.getGraphics();
			for(int i = 0; i < images.size(); i++) {
				g.drawImage(images.get(i), i * width, 0, null);
			}
			
			File outputFile = new File("output.png");
			try {
				ImageIO.write(output, "PNG", outputFile);
				System.out.println("> Succes : mergin end, output saved to output.png");
			} catch (IOException e) {
				System.err.println("\n> ERROR : Error saving image, please check dir rights");
			}
		}
		else {
			System.err.println("\n> ERROR : You need at least two images to merge !");
		}
	}
	
	private static String getFileExtension(String filename) {
		try {
			return filename.substring(filename.lastIndexOf(".") + 1);
		} catch(Exception e) {
			return "";
		}
	}
}
