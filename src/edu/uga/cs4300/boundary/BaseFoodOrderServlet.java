package edu.uga.cs4300.boundary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import edu.uga.cs4300.objectlayer.CustomizableItem;
import edu.uga.cs4300.objectlayer.Side;
import edu.uga.cs4300.objectlayer.Topping;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class BaseFoodOrderServlet extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2175841634672075403L;
	Configuration cfg = null;
	private String templateDir = "/WEB-INF/templates";

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public BaseFoodOrderServlet(){
		super();
	}
	public void init() {
		cfg = new Configuration(Configuration.VERSION_2_3_25);
		cfg.setServletContextForTemplateLoading(getServletContext(), templateDir);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
	}
	public Configuration getCfg() {
		return cfg;
	}
	public String getTemplateDir() {
		return templateDir;
	}
	public void renderTemplate(HttpServletRequest request, HttpServletResponse response, String templateName,
			SimpleHash root) {
		try {
			Template template = null;
			template = cfg.getTemplate(templateName);
			response.setContentType("text/html");
			Writer out = response.getWriter();
			template.process(root, out);
			template.setShowErrorTips(true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}
	public boolean saveImage(HttpServletRequest request, HttpServletResponse response,
			SimpleHash root, String urlAsName) throws IllegalStateException, IOException, ServletException {
		boolean saved = false;
		    // Create path components to save the file
		    final String path = System.getProperty("user.home") + File.separator + "team1foodorderapp";
		    Path dirPath = Paths.get(path);
		    if(dirPath == null || !Files.exists(dirPath, LinkOption.values())){
		    	Files.createDirectory(dirPath);
		    }
		    final Part filePart = request.getPart("file");

		    OutputStream out = null;
		    InputStream filecontent = null;

		    try {
		        out = new FileOutputStream(new File(path + File.separator
		                + urlAsName));
		        filecontent = filePart.getInputStream();

		        int read = 0;
		        final byte[] bytes = new byte[1024];

		        while ((read = filecontent.read(bytes)) != -1) {
		            out.write(bytes, 0, read);
		        }
		        saved = true;
		    } catch (FileNotFoundException fne) {
		    	root.put("error", false);
		    	root.put("message", "File not found. Exception: " + fne.getMessage());

		    } finally {
		        if (out != null) {
		            out.close();
		        }
		        if (filecontent != null) {
		            filecontent.close();
		        }
		    }
		return saved;
	}
	public boolean imageExists(String fileName){
		boolean exists = false;
		final String path = System.getProperty("user.dir");
		if (new File(path + File.separator + fileName).exists()) {
			exists = true;
		}
		return exists;
	}
	public String getFileName(final Part part, String payload) {
	    final String partHeader = part.getHeader("content-disposition");
	    for (String content : partHeader.split(";")) {
	        if (content.trim().startsWith(payload)) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	public boolean hasNoCache(HttpServletRequest request){
			String nocache = request.getParameter("nocache");
			if(StringUtils.isNumeric(nocache)){
				long nocacheLong = Long.parseLong(nocache);
				if(nocacheLong > 0){
					return true;
				}
			}
			return false;
	}
	public void updateSelectedSides(List<Side> sides, List<Side> sides2) {
		if (CollectionUtils.isNotEmpty(sides) && CollectionUtils.isNotEmpty(sides2)) {
			for (Side side : sides) {
				for (Side side2 : sides2) {
					if (side.getId() == side2.getId()) {
						side.setSelected(true);
					}
				}
			}
		}
	}

	public void updateSelectedToppings(List<Topping> toppings, List<Topping> toppings2) {
		if (CollectionUtils.isNotEmpty(toppings) && CollectionUtils.isNotEmpty(toppings2)) {
			for (Topping topping : toppings) {
				for (Topping topping2 : toppings2) {
					if (topping.getId() == topping2.getId()) {
						topping.setSelected(true);
					}
				}
			}
		}
	}

	public void updateSelectedCustomizableItems(List<CustomizableItem> customizableItems,
			List<CustomizableItem> customizableItems2) {
		if (CollectionUtils.isNotEmpty(customizableItems) && CollectionUtils.isNotEmpty(customizableItems2)) {
			for (CustomizableItem customizableItem : customizableItems) {
				for (CustomizableItem customizableItem2 : customizableItems2) {
					if (customizableItem.getId() == customizableItem2.getId()) {
						customizableItem.setSelected(true);
					}
				}
			}
		}
	}
}
