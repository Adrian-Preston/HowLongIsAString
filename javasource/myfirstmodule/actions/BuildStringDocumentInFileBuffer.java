// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package myfirstmodule.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Map.Entry;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IDataType;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaObject;
import com.mendix.webui.CustomJavaAction;

public class BuildStringDocumentInFileBuffer extends CustomJavaAction<java.lang.Long>
{
	private IMendixObject __FileDocument;
	private system.proxies.FileDocument FileDocument;
	private java.lang.String DataCollectionMicroflow;
	private IMendixObject DataCollectionMicroflowParameter;

	public BuildStringDocumentInFileBuffer(IContext context, IMendixObject FileDocument, java.lang.String DataCollectionMicroflow, IMendixObject DataCollectionMicroflowParameter)
	{
		super(context);
		this.__FileDocument = FileDocument;
		this.DataCollectionMicroflow = DataCollectionMicroflow;
		this.DataCollectionMicroflowParameter = DataCollectionMicroflowParameter;
	}

	@java.lang.Override
	public java.lang.Long executeAction() throws Exception
	{
		this.FileDocument = this.__FileDocument == null ? null : system.proxies.FileDocument.initialize(getContext(), __FileDocument);

		// BEGIN USER CODE
		return buildStringDocumentInFileBuffer();
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "BuildStringDocumentInFileBuffer";
	}

	// BEGIN EXTRA CODE
	protected static String CONTEXT_WRITER = "AppendStringToFileBufferWriter";
	protected static String CONTEXT_FILE = "AppendStringToFileBufferFile";
	
	private Long buildStringDocumentInFileBuffer() throws Exception
	{		
		File file;
		FileWriter writer;
		Long returnValue = 0L;
		
		cleardown();
			
		try
		{
			file = File.createTempFile("FileBuffer", "Data");
			file.deleteOnExit();
			getContext().getData().put(CONTEXT_FILE, file);
			
			writer = new FileWriter(file, true);
			getContext().getData().put(CONTEXT_WRITER, writer);
		}
		catch (Exception e)
		{
			cleardown();
			throw new RuntimeException(e);
		}
		
		try
		{
			if (DataCollectionMicroflowParameter != null)
			{
				String argument = null;
				for (Entry<String, IDataType> entry : Core.getInputParameters(DataCollectionMicroflow).entrySet())
				{
					IDataType dataType = entry.getValue();
					
					if (dataType.isMendixObject())
					{
						if (dataType.getObjectType().equals(DataCollectionMicroflowParameter.getType()))
						{
							argument = entry.getKey();
							break;
						}
							
						IMetaObject metaObject = Core.getMetaObject(dataType.getObjectType());
						if (DataCollectionMicroflowParameter.getMetaObject().isSubClassOf(metaObject))
						{
							argument = entry.getKey();
							break;
						}
					}
				}
					
				if (argument == null)
				{
					throw new CoreException("No suitable argument could be found in microflow " + 
							DataCollectionMicroflow + " for " + DataCollectionMicroflowParameter.getType());
				}
				
				returnValue = Core.microflowCall(DataCollectionMicroflow).withParam(argument, DataCollectionMicroflowParameter).execute(getContext());
			}
			else
			{
				returnValue = Core.microflowCall(DataCollectionMicroflow).execute(getContext());
			}

			writer.close();
			FileInputStream inStream = new FileInputStream(file);
			Core.storeFileDocumentContent(getContext(), FileDocument.getMendixObject(), file.getName(), inStream);
			inStream.close();
			file.delete();
			getContext().getData().remove(CONTEXT_WRITER);
			getContext().getData().remove(CONTEXT_FILE);
		}
		catch (CoreException e)
		{
			cleardown();
			throw e;
		}

		return returnValue;
	}
	
	private void cleardown() throws RuntimeException
	{
		try
		{
			FileWriter writer = (FileWriter) getContext().getVariables().get(CONTEXT_WRITER);
			if (writer != null)
			{
				writer.close();
				getContext().getData().remove(CONTEXT_WRITER);
			}
			
			File file = (File) getContext().getVariables().get(CONTEXT_FILE);
			if (file != null)
			{
				file.delete();
				getContext().getData().remove(CONTEXT_FILE);
			}
		}
		catch (Exception e)
		{
			Core.getLogger("FileBuffer").warn("cleardown() failed", e);
		}
	}
	// END EXTRA CODE
}
