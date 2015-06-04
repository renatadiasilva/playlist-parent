package dei.uc.pt.aor;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Part;

@Named
@RequestScoped
public class UploadFile implements Serializable {

	private static final long serialVersionUID = -5537671907313363474L;

	private Part file;

	private String path;

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean upload(Long id) {
		String Localpath = generatePath(id);

		try {
			if(validExtension(Localpath)) {
				file.write(Localpath);
				path = generateServerPath(id);
				return true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The file is not a MP3 File!"));
				return false;
			}
		} catch (IOException ioe) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error in the file upload!"));
			return false;
		}

	}

	private boolean validExtension(String path) {
		String extension = path.substring(path.length()-3);

		if(extension.equals("mp3"))
			return true;

		return false;
	}

	private String generatePath(Long id) {
		return System.getProperty("jboss.home.dir")+"\\Proj4UploadedFiles\\id"
					+id+"-"+getFilename(file);
	}
	
	private String generateServerPath(Long id) {
		return "\\music\\id"+id+"-"+getFilename(file);
	}

	private static String getFilename(Part part) {  
		for (String cd : part.getHeader("content-disposition").split(";")) {  
			if (cd.trim().startsWith("filename")) {  
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");  
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.  
			}
		}
		return null;  
	}

}
