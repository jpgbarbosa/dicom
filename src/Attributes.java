import java.io.Serializable;
import java.util.Map.Entry;
import java.util.Set;

import fr.apteryx.imageio.dicom.*;

public class Attributes implements Serializable 
{
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                                         ATTRIBUTES                                              *      
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	private static final long serialVersionUID = 1L;
	DataSet patientAttributes;
	DataSet studyAttributes;
	DataSet seriesAttributes;
	DataSet imageAttributes;

	public Attributes(DataSet regPatient, DataSet regStudy, DataSet regSeries,
			DataSet regImage) 
	{
		this.patientAttributes = regPatient;
		this.studyAttributes = regStudy;
		this.seriesAttributes = regSeries;
		this.imageAttributes = regImage;
		/* Sets patient's name. */
		this.patientAttributes.add( Tag.PatientsName, regPatient.findString(Tag.PatientsName));
	}

	public void changeFramesNo(int no) 
	{
		imageAttributes.add(Tag.NumberOfFrames, no);
	}

	public boolean existsData(String type) 
	{
		if (type.equals("Patient")) 
		{
			if (patientAttributes != null)
				return true;
			
		} else if (type.equals("Study")) 
		{
			if (studyAttributes != null)
				return true;
			
		} else if (type.equals("Series")) 
		{
			if (seriesAttributes != null)
				return true;

		} else if (type.equals("Image")) 
		{
			if (imageAttributes != null)
				return true;
		}
		
		return false;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                                   GETTERS & SETTERS                                             *      
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public DataSet getPatientAttributes() 
	{
		return patientAttributes;
	}

	public DataSet getStudyAttributes()
	{
		return studyAttributes;
	}

	public DataSet getSeriesAttributes()
	{
		return seriesAttributes;
	}
	
	public DataSet getImageAttributes()
	{
		return imageAttributes;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getImageAttributesToString()
	{
		String finalString = "Image Attributes:";
		
		Set set = imageAttributes.entrySet();
		String value;
		
		for(Object attr : set)
		{
			Entry<Integer, DataElement> srcImgSeq = (Entry<Integer, DataElement>) attr;
			value = srcImgSeq.getValue().toString();
			
			if(value.contains("SourceImageSequence"))
			{
				finalString+= "\n   -> SourceImageSequence:";
				
				value = value.replace('[', ' ');
				value = value.replace(']', ' ');
				value = value.replace('\n', ' ');
				value = value.replaceAll(" = ", " ");
				String [] c = value.split("  ");
				
				for(int i = 2; i<c.length;i++)
					finalString += "\n      -> "+c[i].replaceAll(" ", " = ");
				
			} else
				finalString += "\n   -> "+value;

		}
		
		return finalString;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getStudyAttributesToString()
	{
		String finalString = "Study Attributes:";
		
		Set set = studyAttributes.entrySet();
		String value;
		
		for(Object attr : set)
		{
			Entry<Integer, DataElement> srcImgSeq = (Entry<Integer, DataElement>) attr;
			value = srcImgSeq.getValue().toString();
			
			if(value.contains("SourceImageSequence"))
			{
				finalString+= "\n   -> SourceImageSequence:";
				
				value = value.replace('[', ' ');
				value = value.replace(']', ' ');
				value = value.replace('\n', ' ');
				value = value.replaceAll(" = ", " ");
				String [] c = value.split("  ");
				
				for(int i = 2; i<c.length;i++)
					finalString += "\n      -> "+c[i].replaceAll(" ", " = ");
				
			} else
				finalString += "\n   -> "+value;

		}
		
		return finalString;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getSeriesAttributesToString()
	{
		String finalString = "Series Attributes:";
		
		Set set = seriesAttributes.entrySet();
		String value;
		
		for(Object attr : set)
		{
			Entry<Integer, DataElement> srcImgSeq = (Entry<Integer, DataElement>) attr;
			value = srcImgSeq.getValue().toString();
			
			if(value.contains("SourceImageSequence"))
			{
				finalString+= "\n   -> SourceImageSequence:";
				
				value = value.replace('[', ' ');
				value = value.replace(']', ' ');
				value = value.replace('\n', ' ');
				value = value.replaceAll(" = ", " ");
				String [] c = value.split("  ");
				
				for(int i = 2; i<c.length;i++)
					finalString += "\n      -> "+c[i].replaceAll(" ", " = ");
				
			} else
				finalString += "\n   -> "+value;

		}
		
		return finalString;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getPatientAttributesToString()
	{
		String finalString = "Patients Attributes:";
		
		Set set = patientAttributes.entrySet();
		String value;
		
		for(Object attr : set)
		{
			Entry<Integer, DataElement> srcImgSeq = (Entry<Integer, DataElement>) attr;
			value = srcImgSeq.getValue().toString();
			
			if(value.contains("SourceImageSequence"))
			{
				finalString+= "\n   -> SourceImageSequence:";
				
				value = value.replace('[', ' ');
				value = value.replace(']', ' ');
				value = value.replace('\n', ' ');
				value = value.replaceAll(" = ", " ");
				String [] c = value.split("  ");
				
				for(int i = 2; i<c.length;i++)
					finalString += "\n      -> "+c[i].replaceAll(" ", " = ");
				
			} else
				finalString += "\n   -> "+value;

		}
		
		return finalString;
	}
	
	
	@Override
	public String toString()
	{
		String finalString = "";
		
		finalString += "Patient Attributes:\n\t->";
		finalString += patientAttributes;
		
		finalString += "\nStudy Attributes:\n\t->";
		finalString += studyAttributes;
		
		finalString += "\nSeries Attributes:\n\t->";
		finalString += seriesAttributes;
		
		if(imageAttributes != null)
		{
			finalString += "\nImage Attributes:\n\t->";
			finalString += imageAttributes;
		}

		return finalString;
	}
}
