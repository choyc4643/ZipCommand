package Zip;


import java.io.*;
import java.util.zip.*;
import org.apache.commons.cli.*;

public class Zip {

	private String[] argument;
	static String input;	//-b
	static String output;	// -o
	static String include;	//-i
	static String ignore;	//-ic
	boolean help;	//-h

   public static void main(String[] args) {
      try {
  		setInput(args[1]);
  		setOutput(args[3]);
  		
  		if(args.length>=5) {
	  		if(args[4].equals("-i")) {
	  			setInclude(args[5]);
	  		}else if(args[4].equals("-ic")) {
	  			setIgnore(args[5]);
	  		}
  		}
  		
         creatZipFile();
         System.out.println(output);
         System.out.println(ignore);
         System.out.println(include);
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   public void run() {
		//String path = args[0];
		Options options = createOptions();

			if(parseOptions(options, argument)){
				if (help){
					printHelp(options);
					return;
				}else {					
					
				}
			}
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			
		
		
		
	}
   
	public void setArg(String[] args) {
		argument = args;
	}

   public static void creatZipFile() throws Exception {
     
	   
       File f = new File(input);
     
       String path = input;

       String files[] = f.list(); // f object 에 있는 파일목록
       
       // Create a buffer for reading the files
       byte[] buf = new byte[1024];
       
       try {

      

           // Create the ZIP file
           String outFilename = output;
           ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));
       
           
           if(include == null && ignore == null) {
        	   for (int i=0; i<files.length; i++) {
            	   
        	       
                   FileInputStream in = new FileInputStream( path + "/" + files[i]);
           
                   // Add ZIP entry to output stream.
                   out.putNextEntry(new ZipEntry(files[i])); // Zip 파일에 경로를 정하여 저장할수 있다.
           
                   // Transfer bytes from the file to the ZIP file
                   int len;
                   while ((len = in.read(buf)) > 0) {

                       out.write(buf, 0, len);
                   }
           
                   // Complete the entry
                   out.closeEntry();
                   in.close();
               }
    	   }else if(include != null) {
    		   for (int i=0; i<files.length; i++) {
            	   if(files[i].contains(include)) {
	    		       
	                   FileInputStream in = new FileInputStream( path + "/" + files[i]);
	           
	                   // Add ZIP entry to output stream.
	                   out.putNextEntry(new ZipEntry(files[i])); // Zip 파일에 경로를 정하여 저장할수 있다.
	           
	                   // Transfer bytes from the file to the ZIP file
	                   int len;
	                   while ((len = in.read(buf)) > 0) {
	
	                       out.write(buf, 0, len);
	                   }
	           
	                   // Complete the entry
	                   out.closeEntry();
	                   in.close();
            	   }
               }
    	   } else if(ignore != null) {
    		   for (int i=0; i<files.length; i++) {
            	   if(files[i].contains(ignore) == false) {
	    		       
	                   FileInputStream in = new FileInputStream( path + "/" + files[i]);
	           
	                   // Add ZIP entry to output stream.
	                   out.putNextEntry(new ZipEntry(files[i])); // Zip 파일에 경로를 정하여 저장할수 있다.
	           
	                   // Transfer bytes from the file to the ZIP file
	                   int len;
	                   while ((len = in.read(buf)) > 0) {
	
	                       out.write(buf, 0, len);
	                   }
	           
	                   // Complete the entry
	                   out.closeEntry();
	                   in.close();
            	   }
               }
    		   
    	   }
       
           // Complete the ZIP file
           out.close();
       }catch(Exception e) {

           throw e;
       }

   }
   
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			input = cmd.getOptionValue("b");
			output = cmd.getOptionValue("o");
			include = cmd.getOptionValue("i");
			ignore = cmd.getOptionValue("ic");
			help = cmd.hasOption("h");


		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}      
	
	private Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("b").longOpt("input")
				.desc("Set an input file path. You must write this option to implement this program.")
				.hasArg()
				.argName("input path")
				.required()
				.build());

		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an first output file path. You must write this option to implement this program.")
				.hasArg()
				.argName("output path")
				.required()
				.build());
		
		options.addOption(Option.builder("i").longOpt("include")
				.desc("Set an necessery word in file name. You can not use this option with -ic option.")
				.hasArg()
				.argName("include word")
				.required()
				.build());
		
		options.addOption(Option.builder("ic").longOpt("ignore")
				.desc("Set an ignore word in file name. You can not use this option with -i option.")
				.hasArg()
				.argName("ignore word")
				.required()
				.build());


		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Help")
				.build());

		return options;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "Zip command";
		String footer ="";
		formatter.printHelp("Zipcommand", header, options, footer, true);
	}
	public static void setInput(String a) {
		input = a;
	}
	
	public static void setOutput(String a) {
		output = a;
	}
	public static void setInclude(String a) {
		include = a;
	}
	public static void setIgnore(String a) {
		ignore = a;
	}


}