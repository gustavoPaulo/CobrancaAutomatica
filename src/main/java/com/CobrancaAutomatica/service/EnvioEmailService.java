package com.CobrancaAutomatica.service;

import com.CobrancaAutomatica.service.EnvioEmailService;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.springframework.stereotype.Service;

@Service
public class EnvioEmailService {
	private String corpoEmail = "";

	private String userName = "portfoliogustavo@portifoliojavawebgustavo.com";

	private String senha = "Cielookimoto1*@";

	public void enviaRelatorioEmail(String enviarPara, byte[] pdf) {

		try {

			String emailUsuario = enviarPara;

			Properties properties = new Properties();
			properties.put("mail.smtp.ssl.trust", "*");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.host", "mail.portifoliojavawebgustavo.com");
			properties.put("mail.smtp.socketFactory.port", "465");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.starttls", "true");

			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, senha);
				}
			});
			
			
			Address[] toUser = InternetAddress.parse(emailUsuario + ",gustavojavaweb@gmail.com");
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName, "Softcase"));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Resultado - Pesquisa de hash.");
			
			StringBuilder stringBuilderCorpoEmail = new StringBuilder();
			
			stringBuilderCorpoEmail.append("<h2>Olá<p>");
			stringBuilderCorpoEmail.append("<h2>Conforme solicitado, enviamos um arquivo com a lista de hash após sua pesquisa.</h2><p>");
			stringBuilderCorpoEmail.append("<br/><h2 style=\"font-family:Trebuchet MS;\">Att, </h2><p>");
			stringBuilderCorpoEmail.append("<h2 style=\"font-family:Trebuchet MS;\">Softcase Soluções Tecnológicas.</h2>");
			
			MimeBodyPart corpoEmailPdf = new MimeBodyPart();
			
			corpoEmail = stringBuilderCorpoEmail.toString();
			corpoEmailPdf.setContent(corpoEmail, "text/html; charset=utf-8");
			
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(corpoEmailPdf);
			
			MimeBodyPart anexoEmail = new MimeBodyPart();
			
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(pdf, "application/pdf")));
			anexoEmail.setFileName("relatorio.pdf");
			
			multipart.addBodyPart((BodyPart) anexoEmail);
			
			message.setContent(multipart);

			
			Transport.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviaRelatorioEmailConectcar(String enviarParaConectcar, byte[] pdf) {
		
		try {
		
			String emailUsuario = enviarParaConectcar;
			
			Properties properties = new Properties();
			properties.put("mail.smtp.ssl.trust", "*");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.host", "mail.portifoliojavawebgustavo.com");
			properties.put("mail.smtp.socketFactory.port", "465");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.starttls", "true");
			
			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, senha);
				}
			});
			
			Address[] toUser = InternetAddress.parse(emailUsuario + ",gustavojavaweb@gmail.com");
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName, "Softcase"));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Resultado - Pesquisa de hash.");
			
			StringBuilder stringBuilderCorpoEmail = new StringBuilder();
			
			stringBuilderCorpoEmail.append("<h2>Olá<p>");
			stringBuilderCorpoEmail.append("<h2>Conforme solicitado, enviamos um arquivo com a lista de hash após sua pesquisa.</h2><p>");
			stringBuilderCorpoEmail.append("<br/><h2 style=\"font-family:Trebuchet MS;\">Att, </h2><p>");
			stringBuilderCorpoEmail.append("<h2 style=\"font-family:Trebuchet MS;\">Softcase Soluções Tecnológicas.</h2>");
			
			MimeBodyPart corpoEmailPdf = new MimeBodyPart();
			
			corpoEmail = stringBuilderCorpoEmail.toString();
			corpoEmailPdf.setContent(corpoEmail, "text/html; charset=utf-8");
			
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(corpoEmailPdf);
			
			MimeBodyPart anexoEmail = new MimeBodyPart();
			
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(pdf, "application/pdf")));
			anexoEmail.setFileName("relatorio.pdf");
			
			multipart.addBodyPart((BodyPart) anexoEmail);
			
			message.setContent(multipart);

			
			Transport.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
