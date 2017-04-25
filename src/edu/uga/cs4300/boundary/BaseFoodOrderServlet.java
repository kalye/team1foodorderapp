package edu.uga.cs4300.boundary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import edu.uga.cs4300.objectlayer.Cart;
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
	protected void sendEmailWithNewPassword(Cart cart) throws IOException {

		String from = "UGAClassof2017Team4@gmail.com";
		String password = "team42017";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		try{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(cart.getOrder().getShippingAddress().getEmail()));
			// Set Subject: header field
			message.setSubject("Password Change Notification!");
			String msgString ="Hi " + cart.getOrder().getShippingAddress().getFirstName() + ",\n\n\n"
					+ "Thank you for your recent order : \n"
					+ "order number: " + cart.getOrder().getOrderNumber() + "\n"
				    + "You placed order with us for the following items \n\n"
					+ cart.getOrder().getItemDescription() + "\n\n"
					+ "Sub Total: " + cart.getSubTotalPrice() + "\n"
					+ "Tax        " + cart.getTax().setScale(2, RoundingMode.CEILING).doubleValue() + "\n"
					+ "Total	  " + cart.getTotalPrice().setScale(2, RoundingMode.CEILING).doubleValue() + "\n\n\n"
				    + "Thanks, "
				    + "\n\n\n\n\n"
				    + "Teamonefoodorder\n";
			message.setText(msgString);
			// Send message
			Transport.send(message);
		}catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
