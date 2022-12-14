// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package myfirstmodule.actions;

import java.io.FileWriter;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;

public class AppendStringToFileBuffer extends CustomJavaAction<java.lang.Void>
{
	private java.lang.String StringToAppend;

	public AppendStringToFileBuffer(IContext context, java.lang.String StringToAppend)
	{
		super(context);
		this.StringToAppend = StringToAppend;
	}

	@java.lang.Override
	public java.lang.Void executeAction() throws Exception
	{
		// BEGIN USER CODE
		return appendStringToFileBuffer();
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "AppendStringToFileBuffer";
	}

	// BEGIN EXTRA CODE
	private Void appendStringToFileBuffer() throws Exception
	{
		Object contextObject = getContext().getData().get(BuildStringDocumentInFileBuffer.CONTEXT_WRITER);
		
		if ((contextObject == null) || (!(contextObject instanceof FileWriter)))
			throw new CoreException("AppendSTringToFileBuffer could not locate context writer. It should only be called from BuildStringDocumentFileBuilder");
		
		FileWriter writer = (FileWriter)contextObject;
		
		writer.write(StringToAppend);

		return null;
	}
	// END EXTRA CODE
}
