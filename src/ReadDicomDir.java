import java.awt.image.*;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;
import javax.imageio.*;
import javax.imageio.stream.*;
import fr.apteryx.imageio.dicom.*;
import fr.apteryx.imageio.dicom.FileSet.*;

public class ReadDicomDir 
{
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                                         ATTRIBUTES                                              *      
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private Vector <String> examImagesPaths;
    private Vector <Attributes> examsAttributes;
	private Vector <Float> frameTime;
	private Vector <String> directoriesRecordTypes;  
    
    public ReadDicomDir()
    {
        Plugin.setLicenseKey("NM73KIZUPKHLFLAQM5L0V9U"); 
        ImageIO.scanForPlugins();       
    }
    
    public void readDirectory(String path) throws Exception
    {
    	/* First of all, we clean the components. */
        examImagesPaths = new Vector<String>();
        examsAttributes = new Vector<Attributes>();
        frameTime = new Vector<Float>(); 
        directoriesRecordTypes = new Vector<String>();
    	
    	File f = new File(path + "DICOMDIR");
    	FileSet fileSet = new FileSet(f,null);
		
		/* Directory hierarchy:
		 * 
		 *       DIRECTORY
		 *           |
		 *           |
		 *         STUDY ---> Other Studies
		 *           |
		 * 	         |	
		 * 		   SERIES ---> Other series
		 *           |
		 *           |
		 *         IMAGES
		 */
		
    	/* Get Patient Directory */
    	Directory patient = fileSet.getRootDirectory();
    	
    	for(int i = 0; i < patient.getNumRecords(); i++)
    	{
    		/* Add attributes from patient */
    		DataSet patientAttributes = patient.getRecord(i).getAttributes();
    		
    		/* Get Study Directory */
    		Directory study = patient.getRecord(i).getLowerLevelDirectory();
    		
    		for(int j = 0; j < study.getNumRecords() ; j++)
    		{
    			/* Add attributes from study */
    			DataSet studyAttributes = study.getRecord(j).getAttributes();
    			
    			/* Get Series Directory */
    			Directory series = study.getRecord(j).getLowerLevelDirectory();
    			
    			for(int k = 0; k < series.getNumRecords(); k++)
    			{
    				/* Add attributes from series */
    				DataSet seriesAttributes = series.getRecord(k).getAttributes();
    				
    				/* Get Images Directory */
    				Directory images = series.getRecord(k).getLowerLevelDirectory();
    				
    	    		for(int l=0; l < images.getNumRecords(); l++)
    	    		{
    	    			/* Add attributes from images */
    	    			Record imageRecord = images.getRecord(l);
    	    			DataSet imageAttributes = imageRecord.getAttributes();

    	    			//Add exam to vector
    	    			examsAttributes.add(new Attributes(patientAttributes,studyAttributes,seriesAttributes,imageAttributes));
    	    			frameTime.add((Float) imageRecord.getAttribute(Tag.FrameTime));
    	    			examImagesPaths.add(path + (imageRecord.getFile()).getPath());
    	    			directoriesRecordTypes.add((String)imageRecord.getAttribute(Tag.DirectoryRecordType));
    	    		}
    			}
    		}
    	}
    }
    
    
    public BufferedImage[] readImages(String fileName) throws Exception
    {
    	ImageIO.scanForPlugins();
    	Plugin.setLicenseKey("NM73KIZUPKHLFLAQM5L0V9U"); 
          
        
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("dicom");
        DicomReader reader = (DicomReader)readers.next();
        reader.addIIOReadWarningListener(new WarningListener());
        reader.setInput(new FileImageInputStream(new File(fileName)));

        if (reader.getNumImages(true) < 1) 
        {
		  	System.err.println("No pixel data.");
		  	return null;
		}
        
        BufferedImage[] result = new BufferedImage[reader.getNumImages(true)];
        DicomMetadata dmd = reader.getDicomMetadata();
        
        for (int i = 0; i < result.length; i++)
        	result[i] = dmd.applyGrayscaleTransformations(reader.read(i), 0);
        
        return result;
    }
    
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                                   GETTERS & SETTERS                                             *      
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public String getExamImagesPath(int i) 
    {
		return examImagesPaths.get(i);
	}

	public Vector<Attributes> getExamsAttributes()
	{
		return examsAttributes;
	}

	public Float getFrameTime(int i) 
	{
		return frameTime.get(i);
	}

	public String getDirectoriesRecordType(int i)
	{
		return directoriesRecordTypes.get(i);
	}
    
}
